package dao;

import model.User;
import java.util.UUID;
import java.sql.*;
import java.util.Optional;

public class UserDAO {
    private final Connection conn;

    // Constructor that accepts a database connection
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // Check if email is already taken
    public boolean isEmailTaken(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;  // Email is taken
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Email is not taken
    }

    // Check if username is already taken
    public boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;  // Username is taken
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Username is not taken
    }

    // Register a new user
    public boolean registerUser(User user) {
        String query = "INSERT INTO users (id, email, username, password, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            String randomId = UUID.randomUUID().toString();
            stmt.setString(1, randomId); // Set the random ID
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                user.setId(randomId);  // Set the ID for the user object
                return true;  // Registration successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Registration failed
    }

    // Validate login credentials
    public Optional<User> validateLogin(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getString("id"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Update user profile
    public boolean updateUser(User user) {
        String query = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getId());
            return stmt.executeUpdate() > 0;  // Profile updated successfully
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Profile update failed
    }
}
