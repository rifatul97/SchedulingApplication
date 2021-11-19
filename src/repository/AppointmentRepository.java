package repository;

import controller.MainScreenController;
import dao.AppointmentDAO;
import dao.CustomerDAO;
import dto.AppointmentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import main.Main;
import model.Appointment;
import model.User;
import utilities.AlertBoxHandler;
import utilities.DBConnection;
import utilities.TimeManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * This repository class provide a higher level of appointment data CRUD manipulation and also
 * aims to hide their complexities.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class AppointmentRepository {

    /**
     * the list of appointments
     */
    public static ObservableList<Appointment> appointments;

    /**
     * the list of appointments order by start
     */

    /**
     * the query for checking the overlap of selected appointment
     */
    private static final String CHECK_APPOINTMENT_OVERLAP = "SELECT * FROM appointments "
            + "WHERE ? BETWEEN start AND end OR ? BETWEEN start AND end OR ? < start AND ? > end "
            + "AND Appointment_ID != ?";


    /**
     * populate the list from the database
     */
    public static void populateData() {
        appointments = AppointmentDAO.selectAll();
    }

    /**
     * obtains the list of appointments
     * @return the list of appointments
     */
    public static ObservableList<Appointment> selectAll() {
        return appointments;
    }

    static String upcomingAppointment = "";

    /**
     * <p>***<b>Lamba Discussion</b>***:
     * I have used lambda here to obtain object by Id.
     * At first, it filters out the object that do match the id and returns the first orelse it evaluates to null.
     * </p>
     * @param appointment_Id the id of the appointment
     * @return the appointment data object that matched the appointment_id
     */
    public static Appointment selectById(int appointment_Id) {
        Optional<Appointment> matchingAppointmentId = Optional.ofNullable(appointments.stream()
                .filter(p -> p.getUserId() == appointment_Id)
                .findFirst().orElse(null));

        return matchingAppointmentId.get();
    }

    /**
     * transfers the appointment data object to the appointment dao which will insert into the database accordingly
     *
     * @param appointment the appointment object to be insert into the database
     */
    public static void insert(AppointmentDTO appointment) {
        AppointmentDAO.insert(appointment);
    }

    /**
     * transfers the appointment Id and the data object to the appointment dao which will update into the database accordingly
     *
     * @param id the appointment id
     * @param appointmentDTO the appointment data transfer object
     */
    public static void update(int id, AppointmentDTO appointmentDTO) {
        AppointmentDAO.update(id, appointmentDTO.getTitle(), appointmentDTO.getDescription(),
                appointmentDTO.getLocation(), appointmentDTO.getType(), appointmentDTO.getStartDateTime(),
                appointmentDTO.getEndDateTime(), appointmentDTO.getLastUpdatedBy(), appointmentDTO.getCustomerId(), appointmentDTO.getUserId(), appointmentDTO.getContactId());
    }

    /**
     * repopulate the list from the database.
     */
    public static void refresh() {
        appointments.clear();
        appointments = AppointmentDAO.selectAll();
    }

    /**
     * To check if the specified customer id has any appointment scheduled
     * @param customer_id the id of the customer
     * @return true if it has scheduled.
     */
    public static boolean checkIfCustomerHasAppointments(int customer_id)
    {
        for (Appointment appointment : appointments) {
            if (appointment.getCustomerId() == customer_id) {
                return true;
            }
        }
        return false;
    }

    /**
     * alerts to user if any upcoming appointment within 15 minutes.
     * @return true if found.
     */
    public static boolean displayIfAnyUpcomingAppointment() {
        ObservableList<Appointment> appointmentsById = FXCollections.observableArrayList();
        final LocalDateTime timeNow = LocalDateTime.now();

        for (Appointment appointment : appointments) {
            LocalDateTime localDateTime = (appointment.getStartDateTime().toLocalDateTime());
            long difMinutes = localDateTime.toEpochSecond(ZoneOffset.UTC) - timeNow.toEpochSecond(ZoneOffset.UTC);

            if (difMinutes <= 900 && difMinutes >= 0) {
                setNotFound(false);

                AlertBoxHandler.displayAlert(Alert.AlertType.INFORMATION, "Appointment #" + appointment.getId() + "  starts at " +
                        "" + appointment.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                        +"\nwhich is within " + Math.floor(difMinutes/60) +" minutes away!");
                return true;
            }
        }

        setNotFound(true);

        return false;
    }


    /**
     * deletes the appointment by id
     * @param id the id of the appointment
     */
    public static void delete(int id) {
        AppointmentDAO.delete(id);
    }

    public static ObservableList<Appointment> selectByContactID(int id) {
        ObservableList<Appointment> appointmentsById = FXCollections.observableArrayList();

        for (Appointment appointment : appointments) {
            if (appointment.getContactId() == id) {
                appointmentsById.add(appointment);
            }
        }

        return appointmentsById;
    }

    /**
     * check if selected appointment time overlap with other scheduled appointments in the database
     *
     * @param currentAppointmentId the id of the appointment to check
     * @param startDateTime the startDateTime of the appointment to check
     * @param endDateTime the startDateTime of the appointment to check
     * @return true if overlaps.
     */
    public static boolean ifSelectedAppointmentTimeOverlaps(int currentAppointmentId,
                                                            LocalDateTime startDateTime, LocalDateTime endDateTime) {

        try(final PreparedStatement ps = DBConnection.getConnection().prepareStatement(CHECK_APPOINTMENT_OVERLAP)) {

            ps.setObject(1, startDateTime);
            ps.setObject(2, endDateTime);
            ps.setObject(3, startDateTime);
            ps.setObject(4, endDateTime);
            ps.setInt(5, currentAppointmentId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if (!(rs.getInt("Appointment_ID") == currentAppointmentId)) {
                    return true;
                }
            }
        } catch (SQLException err) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, err.getLocalizedMessage());
        }

        return false;
    }

    public static void setNotFound(boolean found) {
        if (found == true) {
            upcomingAppointment = "notFound";
        }
    }

    public static String getNotFound() {
        return upcomingAppointment;
    }
}
