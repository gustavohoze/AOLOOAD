package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import controller.UserController;

public class LoginView {
    private UserController userController;  // Use UserController instead of UserDAO

    public LoginView(UserController userController) {
        this.userController = userController;  // Initialize UserController
    }

    public void display(Stage stage) {
        // Create UI components
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button goToRegisterButton = new Button("Register");

        // Event Handling
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Login Error", "Email and Password cannot be empty.");
                return;
            }

            try {
                User user = userController.login(email, password); // Use the UserController to validate login
                showAlert(Alert.AlertType.INFORMATION, "Login Success", "Login successful!");
                // Proceed to the next view or action here
                new UpdateProfileView(userController, user).display(stage); // Pass UserController and User to the next view
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Login Error", ex.getMessage()); // Show error if login fails
            }
        });

        goToRegisterButton.setOnAction(e -> {
            new RegisterView(userController).display(stage); // Pass UserController to RegisterView
        });

        // Layout setup
        VBox layout = new VBox(10, emailLabel, emailField, passwordLabel, passwordField, loginButton, goToRegisterButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Login");
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
