package view;

import controller.UserController;
import dao.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class UpdateProfileView {
    private UserDAO userDAO;
    private UserController userController;
    private User currentUser;

    public UpdateProfileView(UserDAO userDAO, UserController userController, User currentUser) {
        this.userDAO = userDAO;
        this.userController = userController;
        this.currentUser = currentUser;
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

                if (!newEmail.isEmpty()) {
                    userController.updateEmail(currentUser, newEmail);
                }
                if (!newUsername.isEmpty()) {
                    userController.updateUsername(currentUser, newUsername);
                }
                if (!newPassword.isEmpty() && !oldPassword.isEmpty()) {
                    userController.updatePassword(currentUser, oldPassword, newPassword);
                }

                if (userController.updateUser(currentUser)) {
                    showAlert(Alert.AlertType.INFORMATION, "Profile Updated", "Your profile has been successfully updated.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Update Failed", "Failed to update profile. Please try again.");
                }
            } catch (IllegalArgumentException ex) {
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
