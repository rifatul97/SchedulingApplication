package dto;

import java.time.LocalDateTime;

/**
 * CustomerTableDTO transfers data from customer to the customer table in order to display data
 * in specific format
 *
 */
public class CustomerTableDTO {

    private final int id;
    private final String name;
    private final String division;
    private final LocalDateTime dateCreated;

    public CustomerTableDTO(int id, String name, String division, LocalDateTime dateCreated) {
        this.id = id;
        this.name = name;
        this.division = division;
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDivision() {
        return division;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
}
