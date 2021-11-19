package utilities;

import repository.AppointmentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import model.Appointment;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * A abstract Class that handles the conversion of the LocalDateTime between different timezones.
 *
 * @author Rifatul Karim
 */
public abstract class TimeManager {

    private static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a";
    private static final String TIME_FORMAT_12HR_SYSTEM = "hh:mm a";
    private static final String TIME_FORMAT_24HR_SYSTEM = "hh:mm";
    private static final DateTimeFormatter TIME_FORMATTER_TO_12HR_SYSTEM = DateTimeFormatter.ofPattern(TIME_FORMAT_12HR_SYSTEM);
    private static final DateTimeFormatter TIME_FORMATTER_TO_24HR_SYSTEM = DateTimeFormatter.ofPattern(TIME_FORMAT_24HR_SYSTEM);

    /**
     * Converts the 24 hour format to 12 hour format that usually to display on the edit screen.
     * @param time in 12 hour format
     * @return time in 24 hour format
     */
    public static String To12hrTimeFormat(LocalTime time) {
        return time.format(TIME_FORMATTER_TO_12HR_SYSTEM);
    }

    /**
     * Converts string to LocalTime format
     *
     * @param timeIn12Hr time represented in String format
     * @return LocalTime
     */
    public static LocalTime strToLocalTimeFormat(String timeIn12Hr) {
        return LocalTime.parse(timeIn12Hr, TIME_FORMATTER_TO_12HR_SYSTEM);
    }

    /**
     * Purpose of this class to fill the combo box items from the appointment edit screen
     *
     * @param startTime
     * @return list of times start from the startTime till 2300
     */
    public static ObservableList<String> fillComboBoxStr(LocalTime startTime) {
        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime localTime = startTime;

        do {
            time.add(localTime.format(TIME_FORMATTER_TO_12HR_SYSTEM));
            localTime = localTime.plusMinutes(15);


        } while (localTime.isBefore(LocalTime.of(23, 30)));

        time.add(localTime.format(TIME_FORMATTER_TO_12HR_SYSTEM));

        return time;
    }

    /**
     * To check if the selected appointment time is outside business hours
     *
     * @param startDateTime startDateTime of the appointment
     * @param endDateTime endDateTime of the appointment
     * @return false if the time is not outside the business hours
     */
    public static boolean checkIfOutsideBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        ZonedDateTime easternStartTime = LocalToEST(startDateTime); // converts to EST time
        ZonedDateTime easternEndTime = LocalToEST(endDateTime);

        return !(easternStartTime.getHour() >= 8 && easternEndTime.getHour() < 22);
    }

    /**
     * Converts the user's LocalDateTime to EST timezone
     *
     * @param localDateTime localtime in user's locale
     * @return localtime in est timezone
     */
    public static ZonedDateTime LocalToEST(LocalDateTime localDateTime) {
        ZonedDateTime zdtPak = localDateTime.atZone(ZoneId.systemDefault());

        ZonedDateTime zdtUtc = zdtPak.withZoneSameInstant(ZoneId.of("US/Eastern"));

        return zdtUtc;
    }

    /**
     * Converts the user's LocalDateTime to UTC timezone
     *
     * @param localDateTime localtime in user's locale
     * @return localtime in utc timezone
     */
    public static LocalDateTime LocalToUTC(LocalDateTime localDateTime) {
        ZonedDateTime zdtPak = localDateTime.atZone(ZoneId.systemDefault());

        ZonedDateTime zdtUtc = zdtPak.withZoneSameInstant(ZoneId.of("Etc/UTC"));

        return zdtUtc.toLocalDateTime();
    }

    /**
     * Converts the utc LocalDateTime to user's Locale timezone
     *
     * @param localDateTime localtime in utc
     * @return localtime in user's locale timezone
     */
    public static ZonedDateTime UTCtoLocal(LocalDateTime localDateTime) {
        ZonedDateTime z = localDateTime.atZone(ZoneId.of("UTC"));

        ZonedDateTime zdtUtc = z.withZoneSameInstant(ZoneId.systemDefault());

        return zdtUtc;
    }

    /**
     * returns the current time in Timestamp format
     *
     * @return the current time in Timestamp format
     */
    public static Timestamp getNow() {
        //OffsetDateTime utcNowTime = OffsetDateTime.now(Clock.systemUTC());
        //Timestamp nowTimeStamp = Timestamp.valueOf(utcNowTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        return new Timestamp(System.currentTimeMillis());
    }

}
