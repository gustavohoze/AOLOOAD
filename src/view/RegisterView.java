package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.User;

public class RegisterView {
    private UserController userController;

    public RegisterView(UserController userController) {
        this.userController = userController;
    }

    public void display(Stage stage) {
        // Create UI components
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("Arial", 14));

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-padding: 5px;");

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(Font.font("Arial", 14));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-padding: 5px;");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("Arial", 14));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-padding: 5px;");

        Label roleLabel = new Label("Role:");
        roleLabel.setFont(Font.font("Arial", 14));

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll( "Guest", "Event Organizer", "Vendor");
        roleComboBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc;");

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #0078d7; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        registerButton.setPrefWidth(150);

        Button goToLoginButton = new Button("Already have an account? Login here!");
        goToLoginButton.setStyle("-fx-background-color: transparent; -fx-text-fill: grey; -fx-font-size: 14px; -fx-cursor: hand;");
        goToLoginButton.setPrefWidth(250);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        // Event Handling for register
        registerButton.setOnAction(e -> {
            try {
                String email = emailField.getText().trim();
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();
                String role = roleComboBox.getValue();

                userController.checkRegisterInput(email, username, password); // Validation

                if (role == null) {
                    throw new IllegalArgumentException("Role must be selected.");
                }

                userController.register(email, username, password, role); // Registration logic

                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully!");
                new LoginView(userController).display(stage);
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        goToLoginButton.setOnAction(e -> {
            new LoginView(userController).display(stage);
        });

        // Layout setup
        VBox layout = new VBox(15); // Adjusted spacing between elements
        layout.getChildren().addAll(
                emailLabel, emailField, 
                usernameLabel, usernameField, 
                passwordLabel, passwordField, 
                roleLabel, roleComboBox, 
                registerButton, goToLoginButton, 
                errorLabel
        );
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #ffffff;");

        Scene scene = new Scene(layout, 500, 500, Color.WHITE);

        stage.setScene(scene);
        stage.setTitle("Register");
    }

    // Method to show alert
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
