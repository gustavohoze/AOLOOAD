package controller;

import model.EventOrganizer;
import util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventOrganizerController {

    private EventOrganizer eventOrganizer;
    private Connection connection;

    public EventOrganizerController(EventOrganizer eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create an event
    public boolean createEvent(String eventName, String date, String location, String description, String organizerID) {
        if (checkCreateEventInput(eventName, date, location, description)) {
            String eventId = UUID.randomUUID().toString(); // Unique ID for new event
            eventOrganizer.addEvent(eventId);

            try {
                // Insert event into the database
                String query = "INSERT INTO events (event_id, event_name, event_date, event_location, event_description, organizer_id) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, eventId);
                    stmt.setString(2, eventName);
                    stmt.setString(3, date);
                    stmt.setString(4, location);
                    stmt.setString(5, description);
                    stmt.setString(6, organizerID);
                    stmt.executeUpdate();
                    System.out.println("Event Created: " + eventName);
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // View all events organized by this organizer
    public void viewOrganizedEvents(String userID) {
    	
        System.out.println("List of events organized by User ID " + userID + ":");
        String query = "SELECT event_id, event_name, event_date, event_location FROM events WHERE organizer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String eventId = rs.getString("event_id");
                String eventName = rs.getString("event_name");
                String eventDate = rs.getString("event_date");
                String eventLocation = rs.getString("event_location");
                System.out.println("Event ID: " + eventId + ", Name: " + eventName + ", Date: " + eventDate + ", Location: " + eventLocation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View detailed event information
    public void viewOrganizedEventDetails(String eventID) {
        System.out.println("Event Details for Event ID: " + eventID);
        String query = "SELECT event_name, event_date, event_location, event_description FROM events WHERE event_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, eventID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String eventName = rs.getString("event_name");
                String eventDate = rs.getString("event_date");
                String eventLocation = rs.getString("event_location");
                String eventDescription = rs.getString("event_description");
                System.out.println("Name: " + eventName + ", Date: " + eventDate + ", Location: " + eventLocation + ", Description: " + eventDescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void displayInvitedVendorsAndGuests(String eventID) {
        List<String> invitedVendors = new ArrayList<>();
        List<String> invitedGuests = new ArrayList<>();

        // Query to fetch all vendors invited to the event
        String vendorQuery = "SELECT u.username FROM Users u " +
                             "JOIN Event_Vendor ev ON u.user_id = ev.user_id " +
                             "WHERE ev.event_id = ?";
        try (PreparedStatement vendorStmt = connection.prepareStatement(vendorQuery)) {
            vendorStmt.setString(1, eventID);  // Set the eventID in the query
            ResultSet vendorRs = vendorStmt.executeQuery();
            while (vendorRs.next()) {
                invitedVendors.add(vendorRs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Query to fetch all guests invited to the event
        String guestQuery = "SELECT u.username FROM Users u " +
                            "JOIN Event_Guest eg ON u.user_id = eg.user_id " +
                            "WHERE eg.event_id = ?";
        try (PreparedStatement guestStmt = connection.prepareStatement(guestQuery)) {
            guestStmt.setString(1, eventID);  // Set the eventID in the query
            ResultSet guestRs = guestStmt.executeQuery();
            while (guestRs.next()) {
                invitedGuests.add(guestRs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Display the list of invited vendors and guests
        System.out.println("Invited Vendors for Event ID: " + eventID);
        for (String vendor : invitedVendors) {
            System.out.println(vendor);
        }

        System.out.println("Invited Guests for Event ID: " + eventID);
        for (String guest : invitedGuests) {
            System.out.println(guest);
        }
    }


    // Get list of guests for a particular event
    public List<String> getGuests(String eventID) {
        List<String> guests = new ArrayList<>();
        // Query to fetch all users with role 'Guest' who are not yet added to the event
        String query = "SELECT u.username FROM Users u " +
                       "WHERE u.role = 'Guest' " +
                       "AND NOT EXISTS (SELECT 1 FROM Event_Guest eg WHERE eg.user_id = u.user_id AND eg.event_id = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, eventID);  // Set the eventID in the query
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String guestName = rs.getString("username");
                guests.add(guestName);  // Add to the list of guests
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guests;  // Return the list of available guest usernames
    }


    // Get list of vendors for a particular event
    public List<String> getVendors(String eventID) {
        List<String> vendors = new ArrayList<>();
        // Query to fetch all users with role 'Vendor' who are not yet added to the event
        String query = "SELECT u.username FROM Users u " +
                       "WHERE u.role = 'Vendor' " +
                       "AND NOT EXISTS (SELECT 1 FROM Event_Vendor ev WHERE ev.user_id = u.user_id AND ev.event_id = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, eventID);  // Set the eventID in the query
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String vendorName = rs.getString("username");
                vendors.add(vendorName);  // Add to the list of vendors
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendors;  // Return the list of available vendor usernames
    }
    public String getUserIdByUsername(String username) {
        String userId = null;
        String query = "SELECT user_id FROM Users WHERE username = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);  // Set the username in the query
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                userId = rs.getString("user_id");  // Retrieve the userId
            } else {
                System.out.println("User not found with username: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userId;  // Return the userId or null if not found
    }


    // Get guests by transaction ID
    public void getGuestsByTransactionID(String eventID) {
        String query = "SELECT guest_name FROM guests WHERE event_id = ? AND transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, eventID);
            stmt.setString(2, "someTransactionID");  // Replace with actual transaction ID
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String guestName = rs.getString("guest_name");
                System.out.println("Guest (by Transaction ID): " + guestName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get vendors by transaction ID
    public void getVendorsByTransactionID(String eventID) {
        String query = "SELECT vendor_name FROM vendors WHERE event_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, eventID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String vendorName = rs.getString("vendor_name");
                System.out.println("Vendor (by Transaction ID): " + vendorName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check input for creating an event
    public boolean checkCreateEventInput(String eventName, String date, String location, String description) {
        // Validate event name (cannot be empty)
        if (eventName == null || eventName.isEmpty()) {
            System.out.println("Event name cannot be empty.");
            return false;
        }

        // Validate event date (cannot be empty and must be a future date)
        if (date == null || date.isEmpty()) {
            System.out.println("Event date cannot be empty.");
            return false;
        }

        // Check if the date is in the future
        try {
            LocalDate eventDate = LocalDate.parse(date); // Parsing the date to check format
            if (eventDate.isBefore(LocalDate.now())) {
                System.out.println("Event date must be in the future.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return false;
        }

        // Validate event location (cannot be empty and must be at least 5 characters long)
        if (location == null || location.isEmpty() || location.length() < 5) {
            System.out.println("Event location must be at least 5 characters long.");
            return false;
        }

        // Validate event description (cannot be empty and must be at most 200 characters)
        if (description == null || description.isEmpty() || description.length() > 200) {
            System.out.println("Event description must be between 1 and 200 characters.");
            return false;
        }

        // All validations passed
        return true;
    }

    // Add vendor to event (ensure vendor hasn't already been added)
    public void addVendor(String userID, String eventID) {
        // Check if the vendor is already associated with the event
        String checkQuery = "SELECT COUNT(*) FROM Event_Vendor WHERE event_id = ? AND user_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, eventID);
            checkStmt.setString(2, userID);
            ResultSet checkRs = checkStmt.executeQuery();
            if (checkRs.next() && checkRs.getInt(1) == 0) {
                // If not already a vendor, add to Event_Vendor table
                String query = "INSERT INTO Event_Vendor (event_id, user_id) VALUES (?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, eventID);
                    stmt.setString(2, userID);
                    stmt.executeUpdate();
                    System.out.println("Vendor added to event ID: " + eventID);
                }
            } else {
                System.out.println("Vendor already invited to this event.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add guest to event (ensure guest hasn't already been added)
    public void addGuest(String userID, String eventID) {
        // Check if the guest is already associated with the event
        String checkQuery = "SELECT COUNT(*) FROM Event_Guest WHERE event_id = ? AND user_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, eventID);
            checkStmt.setString(2, userID);
            ResultSet checkRs = checkStmt.executeQuery();
            if (checkRs.next() && checkRs.getInt(1) == 0) {
                // If not already a guest, add to Event_Guest table
                String query = "INSERT INTO Event_Guest (event_id, user_id) VALUES (?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, eventID);
                    stmt.setString(2, userID);
                    stmt.executeUpdate();
                    System.out.println("Guest added to event ID: " + eventID);
                }
            } else {
                System.out.println("Guest already invited to this event.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Edit event name
    public void editEventName(String eventID, String newEventName) {
        String query = "UPDATE events SET event_name = ? WHERE event_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newEventName);
            stmt.setString(2, eventID);
            stmt.executeUpdate();
            System.out.println("Event name updated to: " + newEventName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if a vendor is eligible to be added to an event
    public boolean checkAddVendorInput(String userID, String eventID) {
        // Query to check if the vendor is already added to the event
        String query = "SELECT COUNT(*) FROM users WHERE user_id = ? AND event_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userID);
            stmt.setString(2, eventID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Vendor already added to this event
                System.out.println("This vendor has already been invited to this event.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Vendor can be added to the event
        return true;
    }

    // Check if the guest is eligible to be added to the event
    public boolean checkAddGuestInput(String userID, String eventID) {
        // Query to check if the guest is already added to the event
        String query = "SELECT COUNT(*) FROM users WHERE user_id = ? AND event_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userID);
            stmt.setString(2, eventID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Guest already added to this event
                System.out.println("This guest has already been invited to this event.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Guest can be added to the event
        return true;
    }

}
