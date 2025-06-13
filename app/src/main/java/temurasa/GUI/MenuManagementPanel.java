package temurasa.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import temurasa.models.Menu;
import temurasa.database.MenuDAO;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MenuManagementPanel extends VBox {
    private TableView<Menu> menuTable;
    private TextField nameField;
    private ComboBox<String> categoryComboBox;
    private TextField priceField;
    private TextField stockField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button clearFieldsButton;

    private ObservableList<Menu> menuItems;
    private NumberFormat currencyFormat;
    private Menu selectedMenuItem;

    private MenuDAO menuDao = new MenuDAO();

    public MenuManagementPanel() {
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        this.menuItems = FXCollections.observableArrayList();
        setSpacing(20);
        setPadding(new Insets(20));
        createContent();
        loadMenuItems();
    }

    private void createContent() {
        HBox contentBox = new HBox(20);
        contentBox.setPadding(new Insets(20));

        // Left side - Menu Table
        VBox tableSection = createTableSection();
        tableSection.setPrefWidth(600);

        // Right side - Form
        VBox formSection = createFormSection();
        formSection.setPrefWidth(400);

        contentBox.getChildren().addAll(tableSection, formSection);
        this.getChildren().add(contentBox);
    }

    private VBox createTableSection() {
        VBox tableSection = new VBox(10);

        // Table title
        Label tableTitle = new Label("Menu Items");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tableTitle.setTextFill(Color.web("#2C3E50"));

        menuTable = new TableView<>();
        menuTable.setPrefHeight(500);
        menuTable.setItems(menuItems);

        TableColumn<Menu, String> nameCol = new TableColumn<>("Nama");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        nameCol.setPrefWidth(180);

        TableColumn<Menu, String> categoryCol = new TableColumn<>("Kategori");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        categoryCol.setPrefWidth(120);

        TableColumn<Menu, Double> priceCol = new TableColumn<>("Harga");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("harga"));
        priceCol.setPrefWidth(100);
        priceCol.setCellFactory(col -> new TableCell<Menu, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText("");
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });

        TableColumn<Menu, Integer> stockCol = new TableColumn<>("Stok");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stok"));
        stockCol.setPrefWidth(80);

        menuTable.getColumns().addAll(nameCol, categoryCol, priceCol, stockCol);

        // Table selection listener
        menuTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedMenuItem = newSelection;
                populateForm(newSelection);
            }
        });

        tableSection.getChildren().addAll(tableTitle, menuTable);
        return tableSection;
    }

    private VBox createFormSection() {
        VBox formSection = new VBox(15);
        formSection.setPadding(new Insets(20));
        formSection.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        // Form title
        Label formTitle = new Label("Menu Item Details");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        formTitle.setTextFill(Color.web("#2C3E50"));

        // Form fields
        VBox fieldsBox = new VBox(10);

        // Name field
        Label nameLabel = new Label("Nama:");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        nameField = new TextField();
        nameField.setPromptText("Masukkan nama menu");

        // Category field
        Label categoryLabel = new Label("Kategori:");
        categoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Makanan", "Minuman");
        categoryComboBox.setPromptText("Pilih kategori");
        categoryComboBox.setPrefWidth(Double.MAX_VALUE);

        // Price field
        Label priceLabel = new Label("Harga:");
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        priceField = new TextField();
        priceField.setPromptText("Masukkan harga");

        // Stock field
        Label stockLabel = new Label("Stok:");
        stockLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        stockField = new TextField();
        stockField.setPromptText("Masukkan jumlah stok");

        fieldsBox.getChildren().addAll(
                nameLabel, nameField,
                categoryLabel, categoryComboBox,
                priceLabel, priceField,
                stockLabel, stockField);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        addButton = new Button("Tambah");
        buttonBox.setAlignment(Pos.CENTER);

        addButton = new Button("Tambah");
        addButton.setStyle(
                "-fx-background-color: #D4B996; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 8 16;");
        addButton.setOnAction(e -> addMenuItem());

        updateButton = new Button("Update");
        updateButton.setStyle(
                "-fx-background-color: #D4B996; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 8 16;");
        updateButton.setOnAction(e -> updateMenuItem());

        deleteButton = new Button("Hapus");
        deleteButton.setStyle(
                "-fx-background-color: #D4B996; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 8 16;");
        deleteButton.setOnAction(e -> deleteMenuItem());

        clearFieldsButton = new Button("Clear");
        clearFieldsButton.setStyle(
                "-fx-background-color: #D4B996; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 8 16;");
        clearFieldsButton.setOnAction(e -> clearFields());

        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, clearFieldsButton);

        formSection.getChildren().addAll(formTitle, fieldsBox, buttonBox);
        return formSection;
    }

    private void loadMenuItems() {
        try {
            List<Menu> items = menuDao.getAllMenu();
            menuItems.clear();
            menuItems.addAll(items);
            if (menuTable != null) {
                menuTable.setItems(menuItems);
            }
        } catch (Exception e) {
            showAlert("Gagal memuat data menu: " + e.getMessage());
        }
    }

    private void populateForm(Menu item) {
        nameField.setText(item.getNama());
        categoryComboBox.setValue(item.getKategori());
        priceField.setText(String.valueOf(item.getHarga()));
        stockField.setText(String.valueOf(item.getStok()));
    }

    private void clearFields() {
        nameField.clear();
        categoryComboBox.setValue(null);
        priceField.clear();
        stockField.clear();
        selectedMenuItem = null;
        menuTable.getSelectionModel().clearSelection();
    }

    private void addMenuItem() {
        if (!validateFields()) return;

        try {
            String name = nameField.getText().trim();
            String category = categoryComboBox.getValue();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            Menu menu = new Menu(0, name, price, category, stock, null);
            boolean success = menuDao.insertMenu(menu);

            if (success) {
                showAlert("Menu berhasil ditambahkan!", Alert.AlertType.INFORMATION);
                refreshData();
                clearFields();
            } else {
                showAlert("Gagal menambah menu. Cek koneksi database.");
            }
        } catch (NumberFormatException e) {
            showAlert("Harga dan stok harus berupa angka.");
        }
    }

    private void updateMenuItem() {
        if (selectedMenuItem == null) {
            showAlert("Pilih item yang ingin diupdate.");
            return;
        }
        if (!validateFields()) return;

        try {
            String name = nameField.getText().trim();
            String category = categoryComboBox.getValue();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            // Buat objek Menu baru dengan ID dari selectedMenuItem
            Menu menuBaru = new Menu(selectedMenuItem.getId(), name, price, category, stock, selectedMenuItem.getCreatedAt());
            
            // Panggil updateMenu dengan hanya satu parameter (objek Menu)
            boolean success = menuDao.updateMenu(menuBaru);

            if (success) {
                showAlert("Menu berhasil diupdate!", Alert.AlertType.INFORMATION);
                refreshData();
                clearFields();
            } else {
                showAlert("Gagal update menu.");
            }
        } catch (NumberFormatException e) {
            showAlert("Harga dan stok harus berupa angka.");
        }
    }

    private void deleteMenuItem() {
        if (selectedMenuItem == null) {
            showAlert("Pilih item yang ingin dihapus.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Hapus Menu");
        confirmation.setHeaderText("Hapus menu?");
        confirmation.setContentText("Yakin ingin menghapus '" + selectedMenuItem.getNama() + "'?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = menuDao.deleteMenu(selectedMenuItem.getId());
                if (success) {
                    showAlert("Menu berhasil dihapus!", Alert.AlertType.INFORMATION);
                    refreshData();
                    clearFields();
                } else {
                    showAlert("Gagal menghapus menu.");
                }
            }
        });
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty()) {
            showAlert("Nama menu harus diisi.");
            return false;
        }
        if (categoryComboBox.getValue() == null) {
            showAlert("Pilih kategori.");
            return false;
        }
        try {
            double price = Double.parseDouble(priceField.getText());
            if (price <= 0) {
                showAlert("Harga harus lebih dari 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Harga harus berupa angka.");
            return false;
        }
        try {
            int stock = Integer.parseInt(stockField.getText());
            if (stock < 0) {
                showAlert("Stok tidak boleh negatif.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Stok harus berupa angka.");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        showAlert(message, Alert.AlertType.WARNING);
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Menu Management");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void refreshData() {
        loadMenuItems();
        menuTable.refresh();
    }
}
