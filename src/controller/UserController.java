package controller;

import dao.UserDAO;
import model.User;

public class UserController {
    private UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Update the user's email
    public void updateEmail(User user, String newEmail) {
        if (userDAO.isEmailTaken(newEmail)) {
            throw new IllegalArgumentException("Email is already in use.");
        }
        user.setEmail(newEmail);
    }

    // Update the user's username
    public void updateUsername(User user, String newUsername) {
        if (userDAO.isUsernameTaken(newUsername)) {
            throw new IllegalArgumentException("Username is already in use.");
        }
        user.setUsername(newUsername);
    }

    // Update the user's password
    public void updatePassword(User user, String oldPassword, String newPassword) {
        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Old password is incorrect.");
        }
        user.setPassword(newPassword);
    }

    // Update the user in the database
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }
}
