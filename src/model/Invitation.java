package model;

public class Invitation {

    private String invitationID;
    private String eventID;
    private String userID;  // ID of the user receiving the invitation
    private String invitationStatus;  // Status of the invitation (e.g., "Pending", "Accepted", "Rejected")
    private String invitationRole;  // Role of the guest in the event (e.g., "Attendee", "Organizer")

    public Invitation(String invitationID, String eventID, String userID, String invitationStatus, String invitationRole) {
        this.invitationID = invitationID;
        this.eventID = eventID;
        this.userID = userID;
        this.invitationStatus = invitationStatus;
        this.invitationRole = invitationRole;
    }

    // Getters and Setters
    public String getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(String invitationID) {
        this.invitationID = invitationID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String getInvitationRole() {
        return invitationRole;
    }

    public void setInvitationRole(String invitationRole) {
        this.invitationRole = invitationRole;
    }
}
