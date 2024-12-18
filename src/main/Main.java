package main;

import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import util.DatabaseConnection;
import view.AdminHomeView;
import view.EventOrganizerHomeView;
import view.GuestHomeView;
import view.LoginView;
import view.VendorHomeView;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize database connection
            Connection connection = DatabaseConnection.getInstance().getConnection();

            UserController userController = new UserController();  // Create controller instance

            // Start with the LoginView
            LoginView loginView = new LoginView(userController);  
            loginView.display(primaryStage);
            primaryStage.setTitle("StellarFest");
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
