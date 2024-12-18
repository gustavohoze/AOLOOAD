package model;

import java.util.List;

public class Event {
    private String eventId;
    private String eventName;
    private String eventDate;
    private String eventLocation;
    private String eventDescription;
    private String organizerId; // Organizer ID as a String
    private List<String> guests;  // List of guest names or IDs
    private List<String> vendors; // List of vendor names or IDs

    // Constructor
    public Event(String eventId, String eventName, String eventDate, String eventLocation, String eventDescription, String organizerId, List<String> guests, List<String> vendors) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventDescription = eventDescription;
        this.organizerId = organizerId;
        this.guests = guests;
        this.vendors = vendors;
    }

    public Event(String eventId2, String eventName2, String date, String location, String description,
			String organizerId2) {
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public List<String> getGuests() {
        return guests;
    }

    public void setGuests(List<String> guests) {
        this.guests = guests;
    }

    public List<String> getVendors() {
        return vendors;
    }

    public void setVendors(List<String> vendors) {
        this.vendors = vendors;
    }
}
