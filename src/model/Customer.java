package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
/**
 * the Customer model
 *
 * @Author Rifatul Karim
 * @Version 1.0
 */
public class Customer {

    /**
     * The id of a customer.
     */
    private int id;

    /**
     * The name of a customer.
     */
    private String name;

    /**
     * The address of a customer.
     */
    private String address;

    /**
     * The postal code of a customer.
     */
    private String postalCode;

    /**
     * The phone number of a customer.
     */
    private String phoneNumber;

    /**
     * The customer data date creation.
     */
    private LocalDateTime dateCreated;

    /**
     * The customer data created by.
     */
    private String createdBy;

    /**
     * The last update timestamp of the customer data.
     */
    private Timestamp lastUpdated;
    
    /**
     * The last update by of the customer data.
     */
    private String lastUpdatedBy;

    /**
     * The division id of a customer data that use to store the division data. 
     */
    private int divisionId;

    public Customer () {}

   /**
    * Creates a new Customer Data.
    */
    public Customer(String name, String address, String postalCode, String phoneNumber,
                    LocalDateTime dataCreated,
                    String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int divisionId) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.dateCreated = dataCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
}
