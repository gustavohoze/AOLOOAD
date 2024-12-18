package view;

import java.sql.Connection;

import javax.swing.JOptionPane;

import controller.EventOrganizerController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.EventOrganizer;
import model.User;

public class EventOrganizerHomeView extends Application {
	private EventOrganizer eventOrganizer;
	private EventOrganizerController controller;
	
	public EventOrganizerHomeView(EventOrganizer eventOrganizer) {
		this.eventOrganizer = eventOrganizer;
		this.controller = new EventOrganizerController(eventOrganizer);
	}

    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Event Organizer Home");

        // Title Label
        Label titleLabel = new Label("Event Organizer Home");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Buttons for each feature
        Button viewEventsButton = new Button("View Organized Events");
        Button viewEventDetailsButton = new Button("View Organized Event Details");
        Button addVendorsButton = new Button("Add Vendors");
        Button addGuestsButton = new Button("Add Guests");
        Button editEventNameButton = new Button("Edit Event Name");
        Button createEventButton = new Button("Create Event");

        // Set styles for buttons
        String buttonStyle = "-fx-background-color: #0078d7; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;";
        viewEventsButton.setStyle(buttonStyle);
        viewEventDetailsButton.setStyle(buttonStyle);
        addVendorsButton.setStyle(buttonStyle);
        addGuestsButton.setStyle(buttonStyle);
        editEventNameButton.setStyle(buttonStyle);
        createEventButton.setStyle(buttonStyle);

        // Set preferred width for buttons
        viewEventsButton.setPrefWidth(300);
        viewEventDetailsButton.setPrefWidth(300);
        addVendorsButton.setPrefWidth(300);
        addGuestsButton.setPrefWidth(300);
        editEventNameButton.setPrefWidth(300);
        createEventButton.setPrefWidth(300);
        
//        Set button functionality
        viewEventsButton.setOnAction(e->controller.viewOrganizedEvents(eventOrganizer.getId()));
        createEventButton.setOnAction(e->{
        	String eventName = "";
        	String date= "";
        	String location= "";
        	String description= "";
        	do {
        		eventName = JOptionPane.showInputDialog("Enter Event Name:");
                date = JOptionPane.showInputDialog("Enter Event Date [YYYY-MM-DD]:");
                location = JOptionPane.showInputDialog("Enter Event Location:");
                description = JOptionPane.showInputDialog("Enter Event Description:");
        	}
        	while(controller.createEvent(eventName, date, location, description, eventOrganizer.getId()) == false) ;

        // Call createEvent method with the user input data
        ;});

        // Layout Setup
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f9f9f9;");
        layout.getChildren().addAll(
                titleLabel,
                new Separator(),
                viewEventsButton,
                viewEventDetailsButton,
                addVendorsButton,
                addGuestsButton,
                editEventNameButton,
                createEventButton
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
