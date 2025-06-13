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
import temurasa.models.Order;
import temurasa.models.OrderItem;
import temurasa.controllers.MenuController;
import temurasa.controllers.OrderController;
import temurasa.database.MenuDAO;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class PosPanel extends VBox {
    private String currentUser;
    private TableView<Menu> menuTable;
    private TableView<OrderItem> orderTable;
    private Label totalLabel;
    private TextField quantityField;
    private TextField customerNameField;
    private Label customerLabel;
    private Button addToOrderButton;
    private Button removeFromOrderButton;
    private Button processOrderButton;
    private Button clearOrderButton;
    private Button setCustomerButton;

    private ObservableList<Menu> menuItems;
    private ObservableList<OrderItem> orderItems;
    private double totalAmount = 0.0;
    private String currentCustomerName = "";

    private NumberFormat currencyFormat;

    // Controller
    private final MenuController menuController = new MenuController();
    private final OrderController orderController = new OrderController();

    public PosPanel(String username) {
        this.currentUser = username;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        this.setPadding(new Insets(20));
        this.setSpacing(10);

        HBox customerSection = createCustomerSection();
        HBox contentBox = new HBox(20);

        VBox menuSection = createMenuSection();
        menuSection.setPrefWidth(500);

        VBox orderSection = createOrderSection();
        orderSection.setPrefWidth(500);

        contentBox.getChildren().addAll(menuSection, orderSection);
        this.getChildren().addAll(customerSection, contentBox);

        loadMenuItems();
    }

    private HBox createCustomerSection() {
        HBox customerSection = new HBox(15);
        customerSection.setPadding(new Insets(10));
        customerSection.setAlignment(Pos.CENTER_LEFT);
        customerSection.setStyle(
                "-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 1; -fx-border-radius: 5;");

        Label nameLabel = new Label("Nama Customer:");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        customerNameField = new TextField();
        customerNameField.setPromptText("Masukkan nama customer...");
        customerNameField.setPrefWidth(200);
        customerNameField.setOnAction(e -> setCustomerName());

        setCustomerButton = new Button("Input Customer");
        setCustomerButton.setStyle("-fx-background-color: #8B4844; -fx-text-fill: white; -fx-font-weight: bold;");
        setCustomerButton.setOnAction(e -> setCustomerName());

        customerLabel = new Label("Tidak ada customer");
        customerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        customerLabel.setTextFill(Color.web("#28a745"));

        Separator separator = new Separator();
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);

        customerSection.getChildren().addAll(
                nameLabel, customerNameField, setCustomerButton,
                separator, new Label("Customer Saat Ini:"), customerLabel);

        return customerSection;
    }

    private VBox createMenuSection() {
        VBox menuSection = new VBox(10);

        Label menuTitle = new Label("Menu Items");
        menuTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        menuTitle.setTextFill(Color.web("#2C3E50"));

        menuTable = new TableView<>();
        menuTable.setPrefHeight(400);

        TableColumn<Menu, String> nameCol = new TableColumn<>("Nama");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        nameCol.setPrefWidth(200);

        TableColumn<Menu, String> categoryCol = new TableColumn<>("Kategori");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        categoryCol.setPrefWidth(120);

        TableColumn<Menu, Double> priceCol = new TableColumn<>("Harga");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("harga"));
        priceCol.setPrefWidth(100);
        priceCol.setCellFactory(col -> new TableCell<Menu, Double>() {
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

        HBox addControls = new HBox(10);
        addControls.setAlignment(Pos.CENTER_LEFT);

        Label qtyLabel = new Label("Kuantitas:");
        quantityField = new TextField("1");
        quantityField.setPrefWidth(80);

        addToOrderButton = new Button("Tambahkan Orderan");
        addToOrderButton
                .setStyle("-fx-background-color: #8B4844; -fx-text-fill: white; -fx-font-weight: bold;");
        addToOrderButton.setOnAction(e -> addToOrder());

        addControls.getChildren().addAll(qtyLabel, quantityField, addToOrderButton);

        menuSection.getChildren().addAll(menuTitle, menuTable, addControls);
        return menuSection;
    }

    private VBox createOrderSection() {
        VBox orderSection = new VBox(10);

        Label orderTitle = new Label("Orderan Saat Ini");
        orderTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        orderTitle.setTextFill(Color.web("#2C3E50"));

        orderTable = new TableView<>();
        orderTable.setPrefHeight(300);
        orderItems = FXCollections.observableArrayList();
        orderTable.setItems(orderItems);

        orderTable.setRowFactory(tv -> {
            TableRow<OrderItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    removeFromOrder();
                }
            });
            return row;
        });

        TableColumn<OrderItem, String> itemNameCol = new TableColumn<>("Item");
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("namaItem"));
        itemNameCol.setPrefWidth(200);

        TableColumn<OrderItem, Integer> qtyCol = new TableColumn<>("Kuantitas");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        qtyCol.setPrefWidth(60);

        TableColumn<OrderItem, Double> unitPriceCol = new TableColumn<>("Harga Per-Unit");
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("hargaPerUnit"));
        unitPriceCol.setPrefWidth(100);
        unitPriceCol.setCellFactory(col -> new TableCell<OrderItem, Double>() {
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText("");
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });

        TableColumn<OrderItem, Double> subtotalCol = new TableColumn<>("Subtotal");
        subtotalCol.setCellValueFactory(cellData -> {
            OrderItem item = cellData.getValue();
            return new javafx.beans.property.SimpleObjectProperty<>(item.getQuantity() * item.getHargaPerUnit());
        });
        subtotalCol.setPrefWidth(100);
        subtotalCol.setCellFactory(col -> new TableCell<OrderItem, Double>() {
            protected void updateItem(Double subtotal, boolean empty) {
                super.updateItem(subtotal, empty);
                if (empty || subtotal == null) {
                    setText("");
                } else {
                    setText(currencyFormat.format(subtotal));
                }
            }
        });

        orderTable.getColumns().addAll(itemNameCol, qtyCol, unitPriceCol, subtotalCol);

        HBox totalBox = new HBox();
        totalBox.setAlignment(Pos.CENTER_RIGHT);
        totalLabel = new Label("Total: Rp 0");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        totalLabel.setTextFill(Color.web("#2C3E50"));
        totalBox.getChildren().add(totalLabel);

        HBox orderButtons = new HBox(10);
        orderButtons.setAlignment(Pos.CENTER);

        removeFromOrderButton = new Button("Hapus Item");
        removeFromOrderButton.setStyle("-fx-background-color: #D4B996; -fx-text-fill: black; -fx-font-weight: bold;");
        removeFromOrderButton.setOnAction(e -> removeFromOrder());

        Button decreaseQtyButton = new Button("Kurangkan Item");
        decreaseQtyButton.setStyle("-fx-background-color: #D4B996; -fx-text-fill: black; -fx-font-weight: bold;");
        decreaseQtyButton.setOnAction(e -> decreaseItemQuantity());

        clearOrderButton = new Button("Hapus Order");
        clearOrderButton.setStyle("-fx-background-color: #D4B996; -fx-text-fill: black; -fx-font-weight: bold;");
        clearOrderButton.setOnAction(e -> clearOrder());

        processOrderButton = new Button("Proses Order");
        processOrderButton.setStyle(
                "-fx-background-color: #D4B996; -fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 14;");
        processOrderButton.setOnAction(e -> processOrder());

        orderButtons.getChildren().addAll(removeFromOrderButton, decreaseQtyButton, clearOrderButton,
                processOrderButton);

        orderSection.getChildren().addAll(orderTitle, orderTable, totalBox, orderButtons);
        return orderSection;
    }

    private void setCustomerName() {
        String customerName = customerNameField.getText().trim();
        if (customerName.isEmpty()) {
            showAlert("Silahkan masukkan nama customer.");
            return;
        }

        currentCustomerName = customerName;
        customerLabel.setText(currentCustomerName);
        customerNameField.clear();

        showAlert("Customer: " + currentCustomerName, Alert.AlertType.INFORMATION);
    }

    private void loadMenuItems() {
        menuItems = FXCollections.observableArrayList();
        List<Menu> items = menuController.getAll();
        menuItems.addAll(items);
        if (menuTable != null) {
            menuTable.setItems(menuItems);
        }
    }

    private void addToOrder() {
        Menu selectedItem = menuTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Silahkan pilih item menu.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) {
                showAlert("Harus lebih dari 0.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid quantity.");
            return;
        }

        if (quantity > selectedItem.getStok()) {
            showAlert("Stok tidak cukup.");
            return;
        }

        // Check if item already in order
        OrderItem existingItem = orderItems.stream()
                .filter(item -> item.getNamaItem().equals(selectedItem.getNama()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantity;
            if (newQuantity > selectedItem.getStok()) {
                showAlert("Stok tidak cukup untuk total quantity.");
                return;
            }
            existingItem.setQuantity(newQuantity);
        } else {
            OrderItem orderItem = new OrderItem(
                    selectedItem.getNama(),
                    quantity,
                    selectedItem.getHarga());
            orderItems.add(orderItem);
        }

        updateTotal();
        orderTable.refresh();
        quantityField.setText("1");
    }

    private void removeFromOrder() {
        OrderItem selectedItem = orderTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Pilih item yang ingin dihapus.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Remove Item");
        confirmation.setHeaderText("Remove item from order?");
        confirmation.setContentText("Item: " + selectedItem.getNamaItem() +
                "\nQuantity: " + selectedItem.getQuantity());

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                orderItems.remove(selectedItem);
                updateTotal();
                orderTable.refresh();
            }
        });
    }

    private void decreaseItemQuantity() {
        OrderItem selectedItem = orderTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Pilih item yang ingin dikurangi.");
            return;
        }

        if (selectedItem.getQuantity() > 1) {
            selectedItem.setQuantity(selectedItem.getQuantity() - 1);
            updateTotal();
            orderTable.refresh();
        } else {
            orderItems.remove(selectedItem);
            updateTotal();
            orderTable.refresh();
        }
    }

    private void clearOrder() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Clear Order");
        confirmation.setHeaderText("Clear all items from order?");
        confirmation.setContentText("This action cannot be undone.");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                orderItems.clear();
                updateTotal();
            }
        });
    }

    private void processOrder() {
        if (currentCustomerName.isEmpty()) {
            showAlert("Silahkan isi nama customer sebelum proses order.");
            customerNameField.requestFocus();
            return;
        }

        if (orderItems.isEmpty()) {
            showAlert("Tidak ada item di order.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Process Order");
        confirmation.setHeaderText("Proses order ini?");

        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Customer: ").append(currentCustomerName).append("\n");
        orderDetails.append("Items: ").append(orderItems.size()).append("\n");
        orderDetails.append("Total: ").append(currencyFormat.format(totalAmount)).append("\n\n");
        orderDetails.append("Order Details:\n");

        for (OrderItem item : orderItems) {
            orderDetails.append("- ").append(item.getNamaItem())
                    .append(" (").append(item.getQuantity()).append("x) = ")
                    .append(currencyFormat.format(item.getQuantity() * item.getHargaPerUnit())).append("\n");
        }

        confirmation.setContentText(orderDetails.toString());
        confirmation.getDialogPane().setPrefWidth(400);
        confirmation.getDialogPane().setPrefHeight(300);

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Buat objek Order
                Order order = new Order(
                        0, // id akan diisi oleh database
                        currentCustomerName,
                        currentUser,
                        LocalDateTime.now(),
                        totalAmount);
                int orderId = orderController.tambahOrderReturnId(order);
                if (orderId > 0) {
                    boolean success = orderController.tambahItemKeOrder(orderId, orderItems);
                    if (success) {
                        MenuDAO menuDao = new MenuDAO();
                        for (OrderItem item : orderItems) {
                            menuDao.kurangiStok(item.getNamaItem(), item.getQuantity());
                        }

                        showAlert("Order berhasil diproses!\nCustomer: " + currentCustomerName,
                                Alert.AlertType.INFORMATION);
                        clearOrderAfterProcess();
                        refreshData();
                    } else {
                        showAlert("Gagal menyimpan order items. Silakan coba lagi.");
                    }
                } else {
                    showAlert("Gagal memproses order. Silakan coba lagi.");
                }
            }
        });
    }

    private void clearOrderAfterProcess() {
        orderItems.clear();
        updateTotal();
        currentCustomerName = "";
        customerLabel.setText("Tidak ada customer");
        customerNameField.requestFocus();
    }

    private void updateTotal() {
        totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getHargaPerUnit())
                .sum();
        totalLabel.setText("Total: " + currencyFormat.format(totalAmount));
    }

    private void showAlert(String message) {
        showAlert(message, Alert.AlertType.WARNING);
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("POS System");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void refreshData() {
        loadMenuItems();
    }
}
