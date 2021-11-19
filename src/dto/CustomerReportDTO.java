package dto;

import java.time.LocalDateTime;

/**
 * CustomerReportDTO transfers data from customer to the customer table in order to display data
 * in specific format
 */
public class CustomerReportDTO {

    private final String userData;
    private final String customerData;
    private final String lastUpdatedAgo;
    private final String lastUpdatedBy;
    private final String dateCreated;

    public CustomerReportDTO(String userData, String customerData, LocalDateTime last_Updated, String last_Updated_By, LocalDateTime dateCreated) {
        this.userData = userData;
        this.customerData = customerData;

        this.lastUpdatedAgo = last_Updated.toString();
        this.lastUpdatedBy = last_Updated_By;

        this.dateCreated = generateDate(dateCreated);

    }

    private String generateDate(LocalDateTime dateCreated) {
        return dateCreated.getMonth().name()
                + "/" + dateCreated.getDayOfMonth()
                + " " + dateCreated.getYear();
    }

    public String getUserData() {
        return userData;
    }

    public String getCustomerData() {
        return customerData;
    }

    public String getLastUpdatedAgo() {
        return lastUpdatedAgo;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public String getDateCreated() {
        return dateCreated;
    }
}
