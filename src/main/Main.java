package main;

import dao.UserDAO;
import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import util.DatabaseConnection;
import view.LoginView;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize database connection
            Connection connection = DatabaseConnection.getInstance().getConnection();

            // Create DAO and Controller instances
            UserDAO userDAO = new UserDAO(connection);
            UserController userController = new UserController(userDAO);  // Create controller instance

            // Start with the LoginView
            LoginView loginView = new LoginView(userDAO, userController);  // Pass both DAO and Controller
            loginView.display(primaryStage);

            primaryStage.setTitle("User Management System");
            primaryStage.setResizable(false); // Consistent size across views
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error initializing the application: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
