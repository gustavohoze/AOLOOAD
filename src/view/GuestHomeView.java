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
import model.Guest;
import model.User;

public class GuestHomeView extends Application {
	private Guest guest;
	public GuestHomeView(Guest guest) {
		this.guest = guest;
	}
	public GuestHomeView() {
	}
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Guest Home");

        // Title Label
        Label titleLabel = new Label("Guest Home");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Buttons for each feature
        Button acceptInvitationButton = new Button("Accept Invitation");
        Button viewInvitationsButton = new Button("View Invitations");
        Button viewAcceptedEventsButton = new Button("View Accepted Events");
        Button viewAcceptedEventDetailsButton = new Button("View Accepted Event Details");

        // Set styles for buttons
        String buttonStyle = "-fx-background-color: #0078d7; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;";
        acceptInvitationButton.setStyle(buttonStyle);
        viewInvitationsButton.setStyle(buttonStyle);
        viewAcceptedEventsButton.setStyle(buttonStyle);
        viewAcceptedEventDetailsButton.setStyle(buttonStyle);

        // Set preferred width for buttons
        acceptInvitationButton.setPrefWidth(300);
        viewInvitationsButton.setPrefWidth(300);
        viewAcceptedEventsButton.setPrefWidth(300);
        viewAcceptedEventDetailsButton.setPrefWidth(300);

        // Layout Setup
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f9f9f9;");
        layout.getChildren().addAll(
                titleLabel,
                new Separator(),
                acceptInvitationButton,
                viewInvitationsButton,
                viewAcceptedEventsButton,
                viewAcceptedEventDetailsButton
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