package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * the Country model
 *
 * @Author Rifatul Karim
 * @Version 1.0
 */
public class Country {

    /**
     * The Id of a Country.
     */
    private int id;

    /**
     * The Name of a Country.
     */
    private String countryName;

    /**
     * The Country's data creation date.
     */
    private LocalDateTime dateCreated;

    /**
     * The created by of a country data.
     */
    private String createdBy;

    /**
     * The last update of a country data.
     */
    private Timestamp lastUpdate;
    
    /**
     * The last update by of a country data.
     */
    private String lastUpdatedBy;

    public Country(int id, String countryName,
                   LocalDateTime dateCreated, String createdBy,
                   Timestamp lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.countryName = countryName;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Country() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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
        return this.getCountryName();
    }
}
