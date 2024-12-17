package model;

import java.util.ArrayList;
import java.util.List;

public class EventOrganizer extends User {
	private List<String> eventsCreated;
    public EventOrganizer(String id, String email, String username, String password, String role) {
		super(id, email, username, password, role);
		this.eventsCreated = new ArrayList<>();
	}
    public EventOrganizer(String email, String username, String password, String role) {
    	super( email, username, password, role);
    	this.eventsCreated = new ArrayList<>();
    }

    public List<String> getEventsCreated() {
        return eventsCreated;
    }

    public void addEvent(String eventId) {
        if (!eventsCreated.contains(eventId)) {
            eventsCreated.add(eventId);
        }
    }
}
