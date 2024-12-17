package view;

import dao.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import javafx.application.Platform;

import java.util.Optional;

import controller.UserController;

public class LoginView {
    private UserDAO userDAO;
    private UserController userController;  // Add UserController

    public LoginView(UserDAO userDAO, UserController userController) {
        this.userDAO = userDAO;
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

            Optional<User> userOptional = userDAO.validateLogin(email, password);
            if (userOptional.isPresent()) {
                showAlert(Alert.AlertType.INFORMATION, "Login Success", "Login successful!");
                // Proceed to the next view or action here
                User user = userOptional.get();
                new UpdateProfileView(userDAO, userController,user).display(stage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid credentials.");
            }
        });

        goToRegisterButton.setOnAction(e -> {
            new RegisterView(userDAO, userController).display(stage);
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
