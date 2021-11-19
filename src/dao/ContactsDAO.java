package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import utilities.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This Contact dao class directly from the database of data storage
 * to store delete contact data.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class ContactsDAO {

    private static final String INSERT = "INSERT INTO contacts " +
            "VALUES (NULL, ?, ?)";

    private static final String SELECT_ALL = "SELECT Contact_ID from contacts";

    private static final String GET_BY_ID = "SELECT * FROM contacts WHERE Contact_ID =?";

    private static final String DELETE_BY_ID = "DELETE FROM contacts WHERE Contact_ID = ?";

    private static final String UPDATE_BY_ID = "UPDATE contacts SET Contact_Name = ?, Email = ?" +
            " WHERE Contact_ID = ?";


    public static Contact selectById(int id) {
        Contact contact = new Contact();
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(GET_BY_ID)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contact.setId(rs.getInt("Contact_ID"));
                contact.setContactName(rs.getString("Contact_Name"));
                contact.setEmail(rs.getString("Email"));
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }

        return contact;
    }

    public static ObservableList<Contact> selectAll() {
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                Contact contact = selectById(id);

                contactsList.add(contact);
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }

        return contactsList;
    }

    public static void update(Contact contact) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(UPDATE_BY_ID)) {
            ps.setString(1, contact.getContactName());
            ps.setString(2, contact.getEmail());

            ps.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }

        return; //user?
    }

    public static void insert(Contact contact) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(INSERT)) {
            ps.setString(1, contact.getContactName());
            ps.setString(2, contact.getEmail());

            ps.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public static void delete(int id) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException err) {
            // alerts its not found?
            err.printStackTrace();
            ;
        }
    }


}
