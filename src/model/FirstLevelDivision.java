package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * the Division model
 *
 * @Author Rifatul Karim
 * @Version 1.0
 */
public class FirstLevelDivision {

    /**
     * The id of a division.
     */
    private int id;

    /**
     * The name of a division.
     */
    private String Division;

    /**
     * The division's data date creation.
     */
    private LocalDateTime dateCreated;

    /**
     * The division's data created by.
     */
    private String createdBy;

    /**
     * The last update timestamp of the division's data.
     */
    private Timestamp lastUpdated;

    /**
     * The last update By of the division's data.
     */
    private String lastUpdatedBy;

    /**
     * The Country Id of the Division.
     */
    private int countryId;

    public FirstLevelDivision(int id, String division, LocalDateTime dateCreated, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int countryId) {
        this.id = id;
        Division = division;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    public FirstLevelDivision() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
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

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return this.getDivision();
    }
}
