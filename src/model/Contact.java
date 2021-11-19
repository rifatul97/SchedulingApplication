package model;

/**
 * the Contact model
 *
 * @Author Rifatul Karim
 * @Version 1.0
 */
public class Contact {

    /**
     * The Id of a Contact.
     */
    private int id;

    /**
     * The Contact name.
     */
    private String contactName;

    /**
     * The Contact's email.
     */
    private String email;

    public Contact () {}

    public Contact(int id, String contactName, String email) {
        this.id = id;
        this.contactName = contactName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getContactName();
    }
}
