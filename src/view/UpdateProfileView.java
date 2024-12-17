package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class UpdateProfileView {
    private UserController userController;
    private User currentUser;

    public UpdateProfileView(UserController userController, User currentUser) {
        this.userController = userController;  // Initialize UserController
        this.currentUser = currentUser;        // Initialize the current user
    }

    public void display(Stage stage) {
        // Create UI components
        Label emailLabel = new Label("New Email:");
        TextField emailField = new TextField();
        Label usernameLabel = new Label("New Username:");
        TextField usernameField = new TextField();
        Label oldPasswordLabel = new Label("Old Password:");
        PasswordField oldPasswordField = new PasswordField();
        Label newPasswordLabel = new Label("New Password:");
        PasswordField newPasswordField = new PasswordField();

        Button updateButton = new Button("Update");

        // Event Handling for update
        updateButton.setOnAction(e -> {
            try {
                String newEmail = emailField.getText().trim();
                String newUsername = usernameField.getText().trim();
                String oldPassword = oldPasswordField.getText().trim();
                String newPassword = newPasswordField.getText().trim();

                // Check if the inputs are valid (this could throw an IllegalArgumentException)
                userController.checkChangeProfileInput(newEmail, newUsername, oldPassword, newPassword);

                // Attempt to change the profile (no return value from changeProfile method)
                userController.changeProfile(currentUser, newEmail, newUsername, oldPassword, newPassword);

                // If no exception was thrown, profile update is successful
                showAlert(Alert.AlertType.INFORMATION, "Profile Updated", "Your profile has been successfully updated.");
            } catch (IllegalArgumentException ex) {
                // Show error if validation fails or any issues occur
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        // Layout setup
        VBox layout = new VBox(10, emailLabel, emailField, usernameLabel, usernameField, oldPasswordLabel, oldPasswordField, newPasswordLabel, newPasswordField, updateButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Update Profile");
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
