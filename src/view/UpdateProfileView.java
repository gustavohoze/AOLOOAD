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

public class UpdateProfileView {
    private UserController userController;
    private User currentUser;

    public UpdateProfileView(UserController userController, User currentUser) {
        this.userController = userController;
        this.currentUser = currentUser;
    }

    public void display(Stage stage) {
        // Create UI components
        Label emailLabel = new Label("New Email:");
        emailLabel.setFont(Font.font("Arial", 14));

        TextField emailField = new TextField();
        emailField.setPromptText("Enter new email");
        emailField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-padding: 5px;");

        Label usernameLabel = new Label("New Username:");
        usernameLabel.setFont(Font.font("Arial", 14));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter new username");
        usernameField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-padding: 5px;");

        Label oldPasswordLabel = new Label("Old Password:");
        oldPasswordLabel.setFont(Font.font("Arial", 14));

        PasswordField oldPasswordField = new PasswordField();
        oldPasswordField.setPromptText("Enter old password");
        oldPasswordField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-padding: 5px;");

        Label newPasswordLabel = new Label("New Password:");
        newPasswordLabel.setFont(Font.font("Arial", 14));

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Enter new password");
        newPasswordField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-padding: 5px;");

        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #0078d7; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        updateButton.setPrefWidth(150);

        // Event Handling for update
        updateButton.setOnAction(e -> {
            try {
                String newEmail = emailField.getText().trim();
                String newUsername = usernameField.getText().trim();
                String oldPassword = oldPasswordField.getText().trim();
                String newPassword = newPasswordField.getText().trim();

                userController.checkChangeProfileInput(newEmail, newUsername, oldPassword, newPassword);

                userController.changeProfile(currentUser, newEmail, newUsername, oldPassword, newPassword);

                showAlert(Alert.AlertType.INFORMATION, "Profile Updated", "Your profile has been successfully updated.");
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        // Layout setup
        VBox layout = new VBox(15); // Adjusted spacing between elements
        layout.getChildren().addAll(
                emailLabel, emailField, 
                usernameLabel, usernameField, 
                oldPasswordLabel, oldPasswordField, 
                newPasswordLabel, newPasswordField, 
                updateButton
        );
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #ffffff;");

        Scene scene = new Scene(layout, 500, 500, Color.WHITE);

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
