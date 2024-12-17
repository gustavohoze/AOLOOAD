package controller;

import model.Invitation;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvitationController {

    private Connection connection;
	UserController uc = new UserController();
    public InvitationController() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Send an invitation to a guest by email
    public void sendInvitation(String email, String eventID, String invitationRole) {

        String userID = uc.getUserByEmail(email).getId();
        if (userID != null) {
            String invitationID = generateInvitationID();
            Invitation invitation = new Invitation(invitationID, eventID, userID, "Pending", invitationRole);
            insertInvitationInDatabase(invitation);
            System.out.println("Invitation sent to " + email + " for event ID: " + eventID);
        } else {
            System.out.println("User with email " + email + " not found.");
        }
    }

    // Accept an invitation for a specific event
    public void acceptInvitation(String eventID, String userID) {
        Invitation invitation = getInvitationByEventIDAndUserID(eventID, userID);
        if (invitation != null && invitation.getInvitationStatus().equals("Pending")) {
            invitation.setInvitationStatus("Accepted");
            updateInvitationStatusInDatabase(invitation);
            System.out.println("Invitation accepted for event ID: " + eventID);
        } else {
            System.out.println("Invitation already accepted or not found.");
        }
    }

    // Get all invitations for a specific email (user)
    public void getInvitations(String email) {
        String userID = uc.getUserByEmail(email).getId();
        if (userID != null) {
            List<Invitation> invitations = getInvitationsByUserID(userID);
            if (invitations.isEmpty()) {
                System.out.println("No invitations found for user with email: " + email);
            } else {
                System.out.println("Invitations for " + email + ":");
                for (Invitation invitation : invitations) {
                    System.out.println("Event ID: " + invitation.getEventID() + ", Status: " + invitation.getInvitationStatus());
                }
            }
        } else {
            System.out.println("User with email " + email + " not found.");
        }
    }

    // Helper method to generate a new invitation ID (could be based on database auto-increment or UUID)
    private String generateInvitationID() {
        return "INV" + System.currentTimeMillis();  // Simple example, can be improved
    }


    // Helper method to fetch an invitation based on event ID and user ID
    private Invitation getInvitationByEventIDAndUserID(String eventID, String userID) {
        Invitation invitation = null;
        String query = "SELECT * FROM invitations WHERE event_id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, eventID);
            stmt.setString(2, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                invitation = new Invitation(
                        rs.getString("invitation_id"),
                        rs.getString("event_id"),
                        rs.getString("user_id"),
                        rs.getString("invitation_status"),
                        rs.getString("invitation_role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invitation;
    }

    // Helper method to fetch all invitations for a user by user ID
    private List<Invitation> getInvitationsByUserID(String userID) {
        List<Invitation> invitations = new ArrayList<>();
        String query = "SELECT * FROM invitations WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invitations.add(new Invitation(
                        rs.getString("invitation_id"),
                        rs.getString("event_id"),
                        rs.getString("user_id"),
                        rs.getString("invitation_status"),
                        rs.getString("invitation_role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invitations;
    }

    // Helper method to insert an invitation into the database
    private void insertInvitationInDatabase(Invitation invitation) {
        String query = "INSERT INTO invitations (invitation_id, event_id, user_id, invitation_status, invitation_role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, invitation.getInvitationID());
            stmt.setString(2, invitation.getEventID());
            stmt.setString(3, invitation.getUserID());
            stmt.setString(4, invitation.getInvitationStatus());
            stmt.setString(5, invitation.getInvitationRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to update invitation status in the database
    private void updateInvitationStatusInDatabase(Invitation invitation) {
        String query = "UPDATE invitations SET invitation_status = ? WHERE invitation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, invitation.getInvitationStatus());
            stmt.setString(2, invitation.getInvitationID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
