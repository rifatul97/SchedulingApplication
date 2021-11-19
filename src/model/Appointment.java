package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import utilities.TimeManager;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * the Appointment model
 *
 * @Author Rifatul Karim
 * @Version 1.0
 */
public class Appointment {

    /**
     * The dateTime of a appointment stored in string format.
     */
    private String dateTime;

    /**
     * The startTime of a appointment stored in string format.
     */
    private String startTime;

    /**
     * The endTime of a appointment stored in string format.
     */
    private String endTime;

    /**
     * The Appointment Id.
     */
    private int id;

    /**
     * The title of a appointment.
     */
    private String title;

    /**
     * The description of a appointment.
     */
    private String description;

    /**
     * The location of a appointment.
     */
    private String location;

    /**
     * The type of a appointment.
     */
    private String type;

    /**
     * The startDateTime of a appointment in user's locale.
     */
    private ZonedDateTime startDateTime;

    /**
     * The endDateTime of a appointment in user's locale.
     */
    private ZonedDateTime endDateTime;

    /**
     * The dateCreated of a appointment.
     */
    private LocalDateTime dateCreated;

    /**
     * Appointment created by.
     */
    private String createdBy;
    
    /**
     * Appointment created by.
     */
    private Timestamp lastUpdated;
    
    /**
     * Appointment created by.
     */
    private String lastUpdatedBy;
    
    /**
     * The customer Id of a appointment.
     */
    private int customerId;
    
    /**
     * The user Id of a appointment.
     */
    private int userId;
    
    /**
     * The contact Id of a appointment.
     */
    private int contactId;
    
    /**
     * The contact of a appointment.
     */
    private String Contact;

    public Appointment () {}

    public Appointment(int id, String title, String description, String location, String type,
                       ZonedDateTime startDateTime, ZonedDateTime endDateTime, LocalDateTime dataCreated,
                       String createdBy, Timestamp lastUpdated, String lastUpdatedBy,
                       int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.dateCreated = dataCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;

        this.startTime = startDateTime.toLocalTime().toString();
        this.endTime = endDateTime.toLocalTime().toString();

        this.dateTime = startDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dataCreated) {
        this.dateCreated = dataCreated;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getDateTime() {
        return startDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public void setDateTime(String dateTime) {
        this.dateTime = startDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getStartTime() {
        return startDateTime.toLocalTime().toString();
    }

    public void setStartTime(String startTime) {
        this.startTime = startDateTime.toLocalTime().toString();
    }

    public String getEndTime() {
        return endDateTime.toLocalTime().toString();
    }

    public void setEndTime(String endTime) {
        this.endTime = endDateTime.toLocalTime().toString();
    }

    @Override
    public String toString() {
        return getTitle();
    }

}
