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

public class VendorHomeView extends Application {
	private User user;
	public VendorHomeView(User user) {
		this.user = user;
	}
	public VendorHomeView() {
	}
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vendor Home");

        // Title Label
        Label titleLabel = new Label("Vendor Home");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Buttons for each feature
        Button acceptInvitationButton = new Button("Accept Invitation");
        Button viewAcceptedEventsButton = new Button("View Accepted Events");
        Button viewAcceptedEventDetailsButton = new Button("View Accepted Event Details");
        Button viewInvitationsButton = new Button("View Invitations");
        Button manageVendorButton = new Button("Manage Vendor");

        // Set styles for buttons
        String buttonStyle = "-fx-background-color: #0078d7; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;";
        acceptInvitationButton.setStyle(buttonStyle);
        viewAcceptedEventsButton.setStyle(buttonStyle);
        viewAcceptedEventDetailsButton.setStyle(buttonStyle);
        viewInvitationsButton.setStyle(buttonStyle);
        manageVendorButton.setStyle(buttonStyle);

        // Set preferred width for buttons
        acceptInvitationButton.setPrefWidth(300);
        viewAcceptedEventsButton.setPrefWidth(300);
        viewAcceptedEventDetailsButton.setPrefWidth(300);
        viewInvitationsButton.setPrefWidth(300);
        manageVendorButton.setPrefWidth(300);

        // Layout Setup
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f9f9f9;");
        layout.getChildren().addAll(
                titleLabel,
                new Separator(),
                acceptInvitationButton,
                viewAcceptedEventsButton,
                viewAcceptedEventDetailsButton,
                viewInvitationsButton,
                manageVendorButton
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
