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

public class RegisterView {
    private UserDAO userDAO;
    private UserController userController;

    public RegisterView(UserDAO userDAO, UserController userController) {
        this.userDAO = userDAO;
        this.userController = userController;
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
        roleComboBox.getItems().addAll("Admin", "User");

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

                if (role == null) {
                    throw new IllegalArgumentException("Role must be selected.");
                }

                User user = new User(email, username, password, role);

                // Use controller to check if email or username is taken
                if (userDAO.isEmailTaken(email)) {
                    showAlert(Alert.AlertType.ERROR, "Registration Failed", "Email is already in use.");
                    return;
                }
                if (userDAO.isUsernameTaken(username)) {
                    showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username is already in use.");
                    return;
                }

                if (userDAO.registerUser(user)) {
                    showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully!");
                    new LoginView(userDAO, userController).display(stage); // Go to login view after successful registration
                } else {
                    showAlert(Alert.AlertType.ERROR, "Registration Failed", "Failed to register user.");
                }
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        // Event Handling for login redirection
        goToLoginButton.setOnAction(e -> {
            new LoginView(userDAO, userController).display(stage);
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
