package temurasa.GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainWindow {
    private String username;
    private Stage stage;
    private BorderPane mainLayout;
    private Label welcomeLabel;

    private Button posButton;
    private Button menuButton;
    private Button salesButton;
    private Button logoutButton;

    private PosPanel posPanel;
    private MenuManagementPanel menuPanel;
    private SalesReport salesPanel;

    public MainWindow(String username) {
        this.username = username;
        createUI();
    }

    private void createUI() {
        stage = new Stage();
        stage.setTitle("Welcome to Temurasa POS");
        mainLayout = new BorderPane();

        createTopBar();
        createSidebar();

        posPanel = new PosPanel(username);
        menuPanel = new MenuManagementPanel();
        salesPanel = new SalesReportPanel();

        showPOSPanel();

        Scene scene = new Scene(mainLayout, 900, 600);
        stage.setScene(scene);

        // Handle window close
        stage.setOnCloseRequest(e -> System.exit(0));
    }

    private void createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setSpacing(20);
        topBar.setStyle("-fx-background-color: linear-gradient(to bottom, #8B4844 0%, #6B2C28 50%, #4A1F1C 100%);");

        // Title
        Label titleLabel = new Label("TemuRasa POS System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Welcome label
        welcomeLabel = new Label("Welcome, " + username + "!");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        welcomeLabel.setTextFill(Color.WHITE);

        // Logout button
        logoutButton = new Button("Logout");
        logoutButton.setStyle(
                "-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 5;");
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(
                "-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 5;"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(
                "-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 5;"));
        logoutButton.setOnAction(e -> handleLogout());

        topBar.getChildren().addAll(titleLabel, spacer, welcomeLabel, logoutButton);
        mainLayout.setTop(topBar);
    }

    private void createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #6B2C28 0%, #4A1F1C 100%);");

        // Navigation title
        Label navTitle = new Label("Navigation");
        navTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        navTitle.setTextFill(Color.WHITE);

        // Navigation buttons
        posButton = createNavButton("🛒 Point of Sale", true);
        menuButton = createNavButton("🍽️ Manajemen Menu", false);
        salesButton = createNavButton("📋 Laporan Penjualan", false);

        // Button actions
        posButton.setOnAction(e -> {
            showPOSPanel();
            updateActiveButton(posButton);
        });

        menuButton.setOnAction(e -> {
            showMenuPanel();
            updateActiveButton(menuButton);
        });

        salesButton.setOnAction(e -> {
            showSalesPanel();
            updateActiveButton(salesButton);
        });

        // Spacer
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // System info
        VBox systemInfo = new VBox(5);
        Label systemLabel = new Label("System Info");
        systemLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        systemLabel.setTextFill(Color.LIGHTGRAY);

        Label versionLabel = new Label("Version 1.0");
        versionLabel.setFont(Font.font("Arial", 10));
        versionLabel.setTextFill(Color.LIGHTGRAY);

        systemInfo.getChildren().addAll(systemLabel, versionLabel);

        sidebar.getChildren().addAll(
                navTitle,
                new Separator(),
                posButton, menuButton, salesButton,
                spacer,
                systemInfo);

        mainLayout.setLeft(sidebar);
    }

    private Button createNavButton(String text, boolean active) {
        Button button = new Button(text);
        button.setPrefWidth(180);
        button.setPrefHeight(45);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        if (active) {
            button.setStyle(
                    "-fx-background-color: #D4B996; -fx-text-fill: #4A1F1C; -fx-alignment: center-left; -fx-padding: 10; -fx-background-radius: 5;");
        } else {
            button.setStyle(
                    "-fx-background-color: transparent; -fx-text-fill: #BDC3C7; -fx-alignment: center-left; -fx-padding: 10; -fx-background-radius: 5;");
        }

        button.setOnMouseEntered(e -> {
            if (!button.getStyle().contains("#D4B996")) {
                button.setStyle(
                        "-fx-background-color: #34495E; -fx-text-fill: white; -fx-alignment: center-left; -fx-padding: 10; -fx-background-radius: 5;");
            }
        });

        button.setOnMouseExited(e -> {
            if (!button.getStyle().contains("#D4B996")) {
                button.setStyle(
                        "-fx-background-color: transparent; -fx-text-fill: #BDC3C7; -fx-alignment: center-left; -fx-padding: 10; -fx-background-radius: 5;");
            }
        });

        return button;
    }

    private void updateActiveButton(Button activeButton) {
        // Reset all buttons
        posButton.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #BDC3C7; -fx-alignment: center-left; -fx-padding: 10; -fx-background-radius: 5;");
        menuButton.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #BDC3C7; -fx-alignment: center-left; -fx-padding: 10; -fx-background-radius: 5;");
        salesButton.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #BDC3C7; -fx-alignment: center-left; -fx-padding: 10; -fx-background-radius: 5;");

        // Set active button
        activeButton.setStyle(
                "-fx-background-color: #D4B996; -fx-text-fill: #4A1F1C; -fx-alignment: center-left; -fx-padding: 10; -fx-background-radius: 5;");
    }

    private void showPOSPanel() {
        mainLayout.setCenter(posPanel);
        posPanel.refreshData();
    }

    private void showMenuPanel() {
        mainLayout.setCenter(menuPanel);
        menuPanel.refreshData();
    }

    private void showSalesPanel() {
        mainLayout.setCenter(salesPanel.getPanel());
    }

    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("You will be returned to the login screen.");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color:  #6B2C28;" +
                        "-fx-text-fill: white;");

        // Styling untuk content
        dialogPane.lookup(".content").setStyle("-fx-text-fill: white;");

        // Styling untuk tombol
        dialogPane.getButtonTypes().forEach(buttonType -> {
            Button button = (Button) dialogPane.lookupButton(buttonType);
            button.setStyle(
                    "-fx-background-color: #D4B996;" +
                            "-fx-text-fill: black;" +
                            "-fx-background-radius: 5px;");
        });

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                LoginWindow loginWindow = new LoginWindow();
                loginWindow.show();
                stage.close();
            }
        });
    }

    public void show() {
        stage.show();
    }
}