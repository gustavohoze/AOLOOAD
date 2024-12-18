package controller;

import model.User;
import util.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserController {

    // Register method (using MySQL)
    public User register(String email, String username, String password, String role) {
        checkRegisterInput(email, username, password);
        if (role == null || role.isEmpty()) {
            throw new IllegalArgumentException("Role must be selected.");
        }

        // Check if email or username already exists in the MySQL database
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String emailCheckQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(emailCheckQuery)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new IllegalArgumentException("Email is already in use.");
                }
            }

            String usernameCheckQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(usernameCheckQuery)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new IllegalArgumentException("Username is already in use.");
                }
            }

            // Create a new user
            User newUser = new User(email, username, password, role);
            saveUserToDatabase(newUser);

            return newUser;
        } catch (SQLException e) {
            throw new RuntimeException("Error checking user data in the database: " + e.getMessage());
        }
    }

    // Save user to the database
    private void saveUserToDatabase(User user) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "INSERT INTO users (user_id, email, username, password, role) VALUES (?,?, ?, ?, ?)";
            String userID = UUID.randomUUID().toString();
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
            	stmt.setString(1, userID);
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getUsername());
                stmt.setString(4, user.getPassword()); // In real scenarios, hash the password before storing
                stmt.setString(5, user.getRole());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user to the database: " + e.getMessage());
        }
    }

    // Login method (using MySQL)
    public User login(String email, String password) throws IllegalArgumentException {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    if (storedPassword.equals(password)) {
                        return new User(rs.getString("user_id"), rs.getString("email"), rs.getString("username"),
                                storedPassword, rs.getString("role"));
                    } else {
                        throw new IllegalArgumentException("Invalid password.");
                    }
                } else {
                    throw new IllegalArgumentException("Email not found.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying user data: " + e.getMessage());
        }
    }

    // Change profile method (email, username, and password)
    public void changeProfile(User user, String email, String username, String oldPassword, String newPassword) throws IllegalArgumentException {
        checkChangeProfileInput(email, username, oldPassword, newPassword);

        // Update the user's information in the database
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            // Check if the email is already taken
            String emailCheckQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(emailCheckQuery)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new IllegalArgumentException("Email is already in use.");
                }
            }

            // Check if the username is already taken
            String usernameCheckQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(usernameCheckQuery)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new IllegalArgumentException("Username is already in use.");
                }
            }

            // Update user profile in the database
            String updateQuery = "UPDATE users SET email = ?, username = ?, password = ? WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                stmt.setString(1, email);
                stmt.setString(2, username);
                stmt.setString(3, newPassword); // In real applications, hash the password before storing
                stmt.setString(4, user.getId());
                stmt.executeUpdate();
            }

            // Update the local user object
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(newPassword);

        } catch (SQLException e) {
            throw new RuntimeException("Error updating user profile: " + e.getMessage());
        }
    }

    // Get user by email (from MySQL)
    public User getUserByEmail(String email) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new User(rs.getString("user_id"), rs.getString("email"), rs.getString("username"),
                            rs.getString("password"), rs.getString("role"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying user by email: " + e.getMessage());
        }
        return null;
    }

    // Get user by username (from MySQL)
    public User getUserByUsername(String username) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new User(rs.getString("user_id"), rs.getString("email"), rs.getString("username"),
                            rs.getString("password"), rs.getString("role"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying user by username: " + e.getMessage());
        }
        return null;
    }

    // Validation Methods

    // Register Validation
    public void checkRegisterInput(String email, String username, String password) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (password == null || password.isEmpty() || password.length() < 5) {
            throw new IllegalArgumentException("Password must be at least 5 characters long.");
        }
    }

    // Change Profile Validation
    public void checkChangeProfileInput(String email, String username, String oldPassword, String newPassword) {
        // Email validation: Must be different from current email and unique
        if (email == null || email.isEmpty() || email.equals(getUserByEmail(email).getEmail())) {
            throw new IllegalArgumentException("Email must be unique and different from the current email.");
        }

        // Username validation: Must be different from current username and unique
        if (username == null || username.isEmpty() || username.equals(getUserByUsername(username).getUsername())) {
            throw new IllegalArgumentException("Username must be unique and different from the current username.");
        }

        // Password validation: Old password must match current, new password must be valid
        if (oldPassword == null || oldPassword.isEmpty()) {
            throw new IllegalArgumentException("Old password cannot be empty.");
        }
        if (newPassword == null || newPassword.isEmpty() || newPassword.length() < 5) {
            throw new IllegalArgumentException("New password must be at least 5 characters long.");
        }
    }
}
