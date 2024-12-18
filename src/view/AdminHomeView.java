package view;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class AdminHomeView extends Application {
private User user;
	public AdminHomeView(User user) {
		this.user = user;
	}
	public AdminHomeView() {
	}
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin Home");

        // Title Label
        Label titleLabel = new Label("Admin Home");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Buttons for each feature
        Button viewAllEventsButton = new Button("View All Events");
        Button viewEventDetailsButton = new Button("View Event Details");
        Button deleteEventsButton = new Button("Delete Events");
        Button viewAllUsersButton = new Button("View All Users");
        Button deleteUsersButton = new Button("Delete Users");

        // Set styles for buttons
        String buttonStyle = "-fx-background-color: #0078d7; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;";
        viewAllEventsButton.setStyle(buttonStyle);
        viewEventDetailsButton.setStyle(buttonStyle);
        deleteEventsButton.setStyle(buttonStyle);
        viewAllUsersButton.setStyle(buttonStyle);
        deleteUsersButton.setStyle(buttonStyle);

        // Set preferred width for buttons
        viewAllEventsButton.setPrefWidth(300);
        viewEventDetailsButton.setPrefWidth(300);
        deleteEventsButton.setPrefWidth(300);
        viewAllUsersButton.setPrefWidth(300);
        deleteUsersButton.setPrefWidth(300);
        
//        Set Button functionality
        

        // Layout Setup
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f9f9f9;");
        layout.getChildren().addAll(
                titleLabel,
                new Separator(),
                viewAllEventsButton,
                viewEventDetailsButton,
                deleteEventsButton,
                viewAllUsersButton,
                deleteUsersButton
        );
        layout.setAlignment(Pos.CENTER);

        // Set Scene
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
