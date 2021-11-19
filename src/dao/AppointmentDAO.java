package dao;

import controller.MainScreenController;
import dto.AppointmentDTO;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;
import utilities.AlertBoxHandler;
import utilities.DBConnection;
import utilities.TimeManager;

import java.sql.*;
import java.time.*;

/**
 * This appointment dao class directly from the database of data storage
 * to obtain, update or delete appointment data.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class AppointmentDAO {

    private static final String INSERT = "INSERT INTO appointments (" +
            "Appointment_ID , Title, Description, Location, Type, " +
            "Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, " +
            "Customer_ID, User_ID, Contact_ID) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL = "SELECT Appointment_ID FROM appointments";

    private static final String SELECT_BY_ID = "SELECT Appointment_ID , Title, Description, " +
            "Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By," +
            "Customer_ID, User_ID, appointments.Contact_ID, Contact_Name  FROM appointments JOIN contacts" +
            "  ON (appointments.Contact_ID = contacts.Contact_ID) WHERE Appointment_ID = ?";

    private static final String DELETE_BY_ID = "DELETE FROM appointments WHERE Appointment_ID = ?";

    private static final String UPDATE_BY_ID = "UPDATE appointments" + " "
            + "SET Title = ?, Description = ? , Location = ? , Type = ? , "
            + "Start = ? , End = ? , Last_Update = ? , Last_Updated_By = ? , "
            + "Customer_ID = ? , User_ID = ? , Contact_ID = ? WHERE Appointment_ID = ?";

    ////private static final String SELECT_BY_STARTORDER = "select Appointment_ID from appointments ORDER BY Start";

    /**
     * obtain appointment object by id from the database
     * @param id the appointment's id
     * @return appointment data object
     */
    public static Appointment selectById(int id) {
        Appointment appointment = new Appointment();

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                appointment.setId(id);
                appointment.setContactId(rs.getInt("Contact_ID"));
                appointment.setCustomerId(rs.getInt("Customer_ID"));
                appointment.setUserId(rs.getInt("User_ID"));
                appointment.setCreatedBy(rs.getString("Created_By"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setLocation(rs.getString("Location"));
                appointment.setType(rs.getString("Type"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                appointment.setDateCreated(rs.getTimestamp("Create_Date").toLocalDateTime());

                LocalDateTime startDateTime = rs.getObject("Start", LocalDateTime.class);
                LocalDateTime endDateTime = rs.getObject("End", LocalDateTime.class);

                appointment.setStartDateTime(TimeManager.UTCtoLocal(startDateTime));
                appointment.setEndDateTime(TimeManager.UTCtoLocal(endDateTime));

                appointment.setLastUpdated(rs.getTimestamp("Last_Update"));
                appointment.setContact(rs.getString("Contact_Name"));
            }

        } catch (SQLException err) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, err.getSQLState());
        }

        return appointment;
    }

    /**
     * list of appointment from the database
     * @return list of appointments from the database
     */
    public static ObservableList<Appointment> selectAll() {
        DBConnection.startConnection();
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try (final PreparedStatement ps = DBConnection.getConnection().prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                Appointment appointment = selectById(id);
                appointmentsList.add(appointment);
            }

        } catch (SQLException err) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, err.getSQLState());
        }

        return appointmentsList;
    }

    /**
     * update the appointment by id to the database
     *
     * @param id the id of the appointment
     * @param title the title of the appointment
     * @param description the description of the appointment
     * @param location the location of the appointment
     * @param type the type of the appointment
     * @param startDateTime the startdateTime of the appointment
     * @param endDateTime the endDateTime of the appointment
     * @param lastUpdatedBy the last update by of the appointment
     * @param customerId the customer id of the appointment
     * @param userId the current user logged in id
     * @param contactId the contact id
     */
    public static void update(int id, String title, String description, String location, String type,
                              ZonedDateTime startDateTime, ZonedDateTime endDateTime, String lastUpdatedBy,
                              int customerId, int userId, int contactId) {

        try (final PreparedStatement ps = DBConnection.getConnection().prepareStatement(UPDATE_BY_ID)) {

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);

            ps.setObject(5, startDateTime.toLocalDateTime());
            ps.setObject(6, endDateTime.toLocalDateTime());

            ps.setTimestamp(7, TimeManager.getNow());
            ps.setString(8, lastUpdatedBy);
            ps.setInt(9, customerId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);

            ps.setInt(12, id);

            ps.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    /**
     * insert appointment data object into the database
     * @param appointment object
     */
    public static void insert(AppointmentDTO appointment) {

        try (final PreparedStatement ps = DBConnection.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
            ResultSet rs = ps.getGeneratedKeys();

            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setObject(5, appointment.getStartDateTime().toLocalDateTime());
            ps.setObject(6, appointment.getEndDateTime().toLocalDateTime());
            ps.setObject(7, TimeManager.getNow().toLocalDateTime());
            ps.setTimestamp(9, TimeManager.getNow());

            String createdBy = MainScreenController.currentLoggedInUser.getUserName();

            ps.setString(8, createdBy);
            ps.setString(10, createdBy);
            ps.setInt(11, appointment.getCustomerId());
            ps.setInt(12, appointment.getUserId());
            ps.setInt(13, appointment.getContactId());

            ps.execute();
        } catch (SQLException err) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, err.getSQLState());
        }
    }

    /**
     * delete by id
     * @param id the appointment id
     */
    public static void delete(int id) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException err) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, err.getSQLState());
        }
    }

    /**
     * list appointment by start order from the database
     * @return
     */
    @Deprecated
    public static ObservableList<Appointment> selectOrderByStart() {
        final ObservableList<Appointment> appointmentsOrderByStart = FXCollections.observableArrayList();
    /*    try (final PreparedStatement ps = DBConnection.getConnection().prepareStatement("")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                final Appointment appointment = selectById(id);
                appointmentsOrderByStart.add(appointment);
            }

        } catch (SQLException err) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, err.getSQLState());
        }
    */
        return appointmentsOrderByStart;
    }
}
