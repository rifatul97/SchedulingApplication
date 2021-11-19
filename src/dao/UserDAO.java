package dao;

import exception.ScreenExceptionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utilities.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * This User dao class directly from the database of data storage
 * to obtain, store or delete user data.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class UserDAO {

    private static final String INSERT = "INSERT INTO users ("
            + "User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By"
            + ") VALUES (NULL, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL = "SELECT User_ID from users";

    private static final String GET_BY_ID = "SELECT * FROM users WHERE User_ID = ?";

    private static final String DELETE_BY_ID = "DELETE FROM users WHERE User_ID = ?";

    private static final String UPDATE_BY_ID = "UPDATE users" + " "
            + "SET User_Name = ?, Password = ?, Create_Date = ?, Created_By = ?," + " "
            + "Last_Update = ?, Last_Updated_By = ? WHERE User_ID = ?";

    private static final String SELECT_BY_NAME_AND_PASSWORD = "Select * from users" + " "
            + "WHERE User_Name = ? AND Password = ?";

    /**
     * obtain user data object from the database by id
     * @param id the id of the user
     * @return matched user
     */
    public static User selectById(int id) {
        User user = new User();

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(GET_BY_ID)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user.setUserId(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPassword(rs.getString("Password"));
                user.setDateCreated(rs.getObject("Create_Date", LocalDateTime.class));
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdate(rs.getTimestamp("Last_Update"));
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                //
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }

        return user;
    }

    /**
     * obtain all the user list from the database
     * @return the list of user data
     */
    public static ObservableList<User> selectAll()  {
        DBConnection.startConnection();
        ObservableList<User> userList = FXCollections.observableArrayList();

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("User_ID");

                User user = selectById(id);
                userList.add(user);
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }

        return userList;
    }

}
