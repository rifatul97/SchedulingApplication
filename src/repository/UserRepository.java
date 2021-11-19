package repository;

import dao.UserDAO;
import javafx.collections.ObservableList;
import model.User;

import java.util.Optional;

/**
 * This repository class provide a higher level of First Level Division data CRUD manipulation and also
 * aims to hide their complexities.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class UserRepository {

    public static ObservableList<User> users;

    public static void populateData() {
        users = UserDAO.selectAll();
    }

    public static ObservableList<User> selectAll() {
        return users;
    }

    public static User selectById(int user_id) {
        Optional<User> matchingUserId = Optional.ofNullable(users.stream()
                .filter(p -> p.getUserId() == user_id)
                .findFirst().orElse(null));

        return matchingUserId.get();

    }

    /**
     * The purpose of the class to check if the username and password record within the database
     *
     * @param userNameInput the input of username from the login screen
     * @param passwordInput the input of password from the login screen
     * @return null to indicate that its not found otherwise returns the matching user data
     */
    public static User matchUsernameAndPassword(String userNameInput, String passwordInput) {

        for (User user : users) {
            if (user.getUserName().equals(userNameInput) && user.getPassword().equals(passwordInput)) {
                return user;
            }
        }

        return null;
    }

    /**
     * This class is execute to check the input of username is found in the database in
     * that way, program can let user know that only the password is incorrect.
     *
     * @param userNameInput the username input from the login screen
     * @return true if the username was recorded in the database.
     */
    public static boolean matchUsername(String userNameInput) {

        for (User user : users) {
            if (user.getUserName().equals(userNameInput)) {
                return true;
            }
        }

        return false;
    }
}
