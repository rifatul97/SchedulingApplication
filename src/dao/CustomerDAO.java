package dao;

import controller.MainScreenController;
import dto.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import utilities.AlertBoxHandler;
import utilities.DBConnection;
import utilities.TimeManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

/**
 * This Customer dao class directly from the database of data storage
 * to obtain, update or delete customer data.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class CustomerDAO {

    private static final String INSERT = "INSERT INTO customers("
            + "Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By," + " "
            + "Last_Update, Last_Updated_By, Division_ID) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL = "SELECT Customer_ID from customers";

    private static final String GET_BY_ID = "SELECT * FROM customers WHERE Customer_ID =?";

    private static final String DELETE_BY_ID = "DELETE FROM customers WHERE Customer_ID = ?";

    private static final String UPDATE_BY_ID = "UPDATE customers" +
            " SET Customer_Name = ? , Address = ? , Postal_Code = ? ," +
            "Phone = ? , Create_Date = ? , Created_By = ? , " +
            "Last_Update = ? , Last_Updated_By = ? , Division_ID = ? WHERE Customer_ID = ?";


    /**
     * select customer by id
     *
     * @param id the customer id
     * @return customer object model that matched the id
     */
    public static Customer selectById(int id) {
        Customer customer = new Customer();
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(GET_BY_ID)) {
            customer.setId(id);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customer.setName(rs.getString("Customer_Name"));
                customer.setAddress(rs.getString("Address"));
                customer.setCreatedBy(rs.getString("Created_By"));

                LocalDateTime dateCreatedInUTC = rs.getObject("Create_Date", LocalDateTime.class);

                customer.setDateCreated(TimeManager.UTCtoLocal(dateCreatedInUTC).toLocalDateTime());
                customer.setDivisionId(rs.getInt("Division_ID"));

                customer.setLastUpdated(rs.getTimestamp("Last_Update"));

                customer.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                customer.setPhoneNumber(rs.getString("Phone"));
                customer.setPostalCode(rs.getString("Postal_Code"));
            }
        } catch (SQLException err) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, err.getSQLState());
        }

        return customer;
    }

    /**
     * list of all customer from database
     *
     * @return list of all the customers from database
     */
    public static ObservableList<Customer> selectAll() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                final Customer customer = selectById(id);
                customerList.add(customer);
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }

        return customerList;
    }

    /**
     * update customer to the database by id
     *
     * @param id       the id of customer
     * @param customer the customer data transfer object
     */
    public static void update(int id, CustomerDTO customer) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(UPDATE_BY_ID)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhoneNumber());
            ps.setObject(5, customer.getDateCreated());
            ps.setString(6, customer.getCreatedBy());
            ps.setTimestamp(7, TimeManager.getNow());
            ps.setString(8, MainScreenController.currentLoggedInUser.getUserName());
            ps.setInt(9, customer.getDivisionId());

            ps.setInt(10, id);
            ps.execute();

        } catch (SQLException err) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, err.getSQLState());
        }

    }

    /**
     * insert data into database
     *
     * @param customer the customer data transfer object
     */
    public static void insert(CustomerDTO customer) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhoneNumber());
            ps.setObject(5, TimeManager.getNow().toLocalDateTime()); // Create_Date

            ps.setString(6, customer.getCreatedBy()); // Created_By
            ps.setTimestamp(7, TimeManager.getNow()); // Last_Update
            ps.setString(8, customer.getLastUpdatedBy());
            ps.setInt(9, customer.getDivisionId());
            ps.execute();
        } catch (SQLException err) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, err.getSQLState());
            err.printStackTrace();
        }
    }

    /**
     * delete customer by id from the database
     *
     * @param id the customer id
     */
    public static void delete(int id) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException err) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, err.getSQLState());
        }
    }
}
