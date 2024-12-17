package model;

import dao.UserDAO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String id;
    private String email;
    private String username;
    private String password; // This will be stored as a hashed password
    private String role;

    // Constructor for creating new users
    public User(String email, String username, String password, String role) throws IllegalArgumentException {
        validateEmail(email);
        validateUsername(username);
        validatePassword(password);
        validateRole(role);

        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Constructor for loading users from the database
    public User(String id, String email, String username, String password, String role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password; // Password stored as a hash
        this.role = role;
    }

    // Validation Methods
    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Email must be a valid email address.");
        }
    }

    private void validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
    }

    private boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (password.length() < 5) {
            throw new IllegalArgumentException("Password must be at least 5 characters long.");
        }
        return true;
    }

    private void validateRole(String role) {
        if (role == null || role.isEmpty()) {
            throw new IllegalArgumentException("Role must be selected.");
        }
    }
    // Methods to update user attributes with validation
    public void updateEmail(String newEmail, UserDAO userDAO) throws IllegalArgumentException {
        validateEmail(newEmail);
        if (userDAO.isEmailTaken(newEmail)) {
            throw new IllegalArgumentException("Email is already in use.");
        }
        this.email = newEmail;
    }

    public void updateUsername(String newUsername, UserDAO userDAO) throws IllegalArgumentException {
        validateUsername(newUsername);
        if (userDAO.isUsernameTaken(newUsername)) {
            throw new IllegalArgumentException("Username is already in use.");
        }
        this.username = newUsername;
    }

    public void updatePassword(String oldPassword, String newPassword) throws IllegalArgumentException {
        if (!validatePassword(oldPassword)) {
            throw new IllegalArgumentException("Old password does not match current password.");
        }
        validatePassword(newPassword);
        this.password = newPassword;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
