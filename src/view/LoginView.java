package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Admin;
import model.EventOrganizer;
import model.Guest;
import model.User;
import model.Vendor;
import controller.UserController;

public class LoginView {
    private UserController userController;

    public LoginView(UserController userController) {
        this.userController = userController;
    }

    public void display(Stage stage) {
        // Create UI components
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("Arial", 14));

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-padding: 5px;");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("Arial", 14));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-padding: 5px;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #0078d7; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        loginButton.setPrefWidth(150);

        Button goToRegisterButton = new Button("Doesn't have an account? Register here!");
        goToRegisterButton.setStyle("-fx-background-color: transparent; -fx-text-fill: grey; -fx-font-size: 14px; -fx-cursor: hand;");
        goToRegisterButton.setPrefWidth(300);
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

                // Redirect based on user role
                switch (user.getRole()) {
                case "Guest":
                    if (user instanceof Guest) {
                        new GuestHomeView((Guest) user).start(stage); // Cast to Guest and pass it to the Guest view
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Login Error", "User is not a Guest.");
                    }
                    break;

                case "Event Organizer":
                    if (user instanceof EventOrganizer) {
                        new EventOrganizerHomeView((EventOrganizer) user).start(stage); // Cast to EventOrganizer and pass it to the Event Organizer view
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Login Error", "User is not an Event Organizer.");
                    }
                    break;

                case "Admin":
                    if (user instanceof Admin) {
                        new AdminHomeView((Admin) user).start(stage); // Cast to Admin and pass it to the Admin view
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Login Error", "User is not an Admin.");
                    }
                    break;

                case "Vendor":
                    if (user instanceof Vendor) {
                        new VendorHomeView((Vendor) user).start(stage); // Cast to Vendor and pass it to the Vendor view
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Login Error", "User is not a Vendor.");
                    }
                    break;

                default:
                    showAlert(Alert.AlertType.ERROR, "Login Error", "Unknown user role: " + user.getRole()); // Handle unknown roles
                    break;
            }

            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Login Error", ex.getMessage()); // Show error if login fails
            }

        });

        goToRegisterButton.setOnAction(e -> {
            new RegisterView(userController).display(stage); // Pass UserController to RegisterView
        });

        // Styling the layout
        VBox layout = new VBox(15); // Adjusted spacing between elements
        layout.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField, loginButton, goToRegisterButton);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #ffffff;");

        Scene scene = new Scene(layout, 500, 500, Color.WHITE);

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
