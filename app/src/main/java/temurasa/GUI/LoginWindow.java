package temurasa.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import temurasa.abstracts.ShowScene;
import temurasa.database.AdminDao;
import temurasa.util.PasswordUtils;

public class LoginWindow implements ShowScene {
    private Stage stage;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;
    private boolean isLoginMode = true;
    private Button actionButton;
    private Button toggleButton;

    public LoginWindow() {
        createUI();
    }

    @Override
    public void createUI() {
        stage = new Stage();
        stage.setTitle("TemuRasa - Login System");

        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(40));
        mainContainer
                .setStyle("-fx-background-color: linear-gradient(to bottom, #8B4844 0%, #6B2C28 50%, #4A1F1C 100%);");

        VBox loginCard = new VBox(25);
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setPadding(new Insets(45));
        loginCard.setMaxWidth(420);
        loginCard.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.95); " +
                        "-fx-background-radius: 20; " +
                        "-fx-border-radius: 20; " +
                        "-fx-border-color: #D4B996; " +
                        "-fx-border-width: 2; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 8);");

        Label titleLabel = new Label("TEMU RASA");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        titleLabel.setTextFill(Color.web("#8B4844"));
        titleLabel.setStyle("-fx-letter-spacing: 2px;");

        Label subtitleLabel = new Label("Point of Sale System");
        subtitleLabel.setFont(Font.font("Georgia", FontWeight.NORMAL, 16));
        subtitleLabel.setTextFill(Color.web("#6B2C28"));
        subtitleLabel.setStyle("-fx-letter-spacing: 1px;");

        Region decorativeLine = new Region();
        decorativeLine.setPrefHeight(2);
        decorativeLine.setPrefWidth(200);
        decorativeLine.setStyle("-fx-background-color: linear-gradient(to right, transparent, #D4B996, transparent);");

        VBox formContainer = new VBox(20);
        formContainer.setAlignment(Pos.CENTER);

        VBox usernameContainer = new VBox(8);
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        usernameLabel.setTextFill(Color.web("#4A1F1C"));
        usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefHeight(45);
        usernameField.setStyle(
                "-fx-font-size: 14; " +
                        "-fx-padding: 12; " +
                        "-fx-background-color: #FEFEFE; " +
                        "-fx-border-color: #D4B996; " +
                        "-fx-border-width: 1.5; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8;");
        usernameContainer.getChildren().addAll(usernameLabel, usernameField);

        VBox passwordContainer = new VBox(8);
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        passwordLabel.setTextFill(Color.web("#4A1F1C"));
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefHeight(45);
        passwordField.setStyle(
                "-fx-font-size: 14; " +
                        "-fx-padding: 12; " +
                        "-fx-background-color: #FEFEFE; " +
                        "-fx-border-color: #D4B996; " +
                        "-fx-border-width: 1.5; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8;");
        passwordContainer.getChildren().addAll(passwordLabel, passwordField);

        formContainer.getChildren().addAll(usernameContainer, passwordContainer);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);

        actionButton = new Button("LOGIN");
        actionButton.setPrefWidth(130);
        actionButton.setPrefHeight(50);
        actionButton.setStyle(
                "-fx-background-color: #8B4844; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 15; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-radius: 10; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 2);");

        toggleButton = new Button("REGISTER");
        toggleButton.setPrefWidth(130);
        toggleButton.setPrefHeight(50);
        toggleButton.setStyle(
                "-fx-background-color: #D4B996; " +
                        "-fx-text-fill: #4A1F1C; " +
                        "-fx-font-size: 15; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-radius: 10; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 2);");

        buttonContainer.getChildren().addAll(actionButton, toggleButton);

        messageLabel = new Label();
        messageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(370);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setStyle(
                "-fx-padding: 10; " +
                        "-fx-background-color: rgba(255, 255, 255, 0.8); " +
                        "-fx-background-radius: 5; " +
                        "-fx-border-radius: 5;");

        actionButton.setOnAction(e -> handleAction());
        toggleButton.setOnAction(e -> toggleMode());
        passwordField.setOnAction(e -> handleAction());
        usernameField.setOnAction(e -> passwordField.requestFocus());

        loginCard.getChildren().addAll(
                titleLabel, subtitleLabel, decorativeLine, formContainer,
                buttonContainer, messageLabel);

        mainContainer.getChildren().add(loginCard);

        Scene scene = new Scene(mainContainer, 520, 650);
        stage.setScene(scene);
    }

    private AdminDao admin = new AdminDao();

    private void handleAction() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please fill in all fields!", Color.web("#8B4844"));
            return;
        }

        String hashedPassword = PasswordUtils.hashPassword(password);

        if (isLoginMode) {
            boolean success = admin.authenticate(username, hashedPassword);
            if (success) {
                showMessage("Login successful! Welcome back.", Color.web("#2E7D32"));
                MainWindow mainWindow = new MainWindow(username);
                mainWindow.show();
                stage.close();
            } else {
                showMessage("Invalid username or password. Please try again.", Color.web("#C62828"));
            }
        } else {
            // Registration mode
            if (admin.isAdminExist(username)) {
                showMessage("Registration failed! Username already exists.", Color.web("#C62828"));
            } else {
                boolean success = admin.insertAdmin(username, hashedPassword);
                if (success) {
                    showMessage("Registration successful! You can now login with your new account.",
                            Color.web("#2E7D32"));
                    isLoginMode = true;
                    updateButtonText();
                } else {
                    showMessage("Registration failed! Please try again.", Color.web("#C62828"));
                }
            }
        }
    }

    private void toggleMode() {
        isLoginMode = !isLoginMode;
        updateButtonText();
        clearFields();
    }

    private void updateButtonText() {
        if (isLoginMode) {
            actionButton.setText("LOGIN");
            toggleButton.setText("REGISTER");
            showMessage("New here? Click REGISTER to create your account.", Color.web("#6B2C28"));
        } else {
            actionButton.setText("REGISTER");
            toggleButton.setText("LOGIN");
            showMessage("Already have an account? Click LOGIN to access your account.", Color.web("#6B2C28"));
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        messageLabel.setText("");
    }

    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setTextFill(color);
        messageLabel.setVisible(true);
    }

    public void show() {
        stage.show();
        usernameField.requestFocus();
        showMessage("Welcome to TemuRasa POS System! \n Please login or register to continue.", Color.web("#6B2C28"));
    }
}