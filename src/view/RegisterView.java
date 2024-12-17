package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class RegisterView {
    private UserController userController; // Remove UserDAO, only use UserController

    public RegisterView(UserController userController) {
        this.userController = userController; // Initialize UserController
    }

    public void display(Stage stage) {
        // Create UI components
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label roleLabel = new Label("Role:");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "Guest", "Event Organizer", "Vendor");

        Button registerButton = new Button("Register");
        Button goToLoginButton = new Button("Login");

        // Error message label
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Event Handling for register
        registerButton.setOnAction(e -> {
            try {
                String email = emailField.getText().trim();
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();
                String role = roleComboBox.getValue();

                // Validate inputs through UserController
                userController.checkRegisterInput(email, username, password); // Validation

                // Check if role is selected
                if (role == null) {
                    throw new IllegalArgumentException("Role must be selected.");
                }

                User user = new User(email, username, password, role); // Create user object

                // Register the user using UserController
                userController.register(email, username, password, role); // Registration logic

                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully!");
                new LoginView(userController).display(stage); // Go to login view after successful registration
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        // Event Handling for login redirection
        goToLoginButton.setOnAction(e -> {
            new LoginView(userController).display(stage); // Go to login view
        });

        // Layout setup
        VBox layout = new VBox(10, emailLabel, emailField, usernameLabel, usernameField, passwordLabel, passwordField, roleLabel, roleComboBox, registerButton, goToLoginButton, errorLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 400);
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
