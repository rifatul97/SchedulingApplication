package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * the User model
 *
 * @Author Rifatul Karim
 * @Version 1.0
 */
public class User {

    /**
     * The user Id.
     */
    private int userId;

    /**
     * The name of the User.
     */
    private String userName;

    /**
     * The password of the User.
     */
    private String password;

    /**
     * The date creation of the user's data into the database.
     */
    private LocalDateTime dateCreated;

    /**
     * The data of who created the user's data into the database.
     */
    private String createdBy;

    /**
     * The last update timestamp of the division's data.
     */
    private Timestamp lastUpdate;

    /**
     * The last update timestamp of the division's data.
     */
    private String lastUpdatedBy;

    public User() {}

    public User(int userId,
                String userName, String password,
                LocalDateTime dateCreated,
                String createdBy,
                Timestamp lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getUserId());
    }
}
