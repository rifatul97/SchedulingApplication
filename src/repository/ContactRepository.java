package repository;

import dao.ContactsDAO;
import javafx.collections.ObservableList;
import model.Contact;

import java.util.Optional;

/**
 * This repository class provide a higher level of contact data CRUD manipulation and also
 * aims to hide their complexities.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class ContactRepository {

    /**
     * list of contact data
     */
    private static ObservableList<Contact> contacts;

    /**
     * populate contact list from the database
     */
    public static void populateData() {
        contacts = ContactsDAO.selectAll();
    }

    /**
     * get all contacts
     *
     * @return list of contacts
     */
    public static ObservableList<Contact> getAllContact() {
        return contacts;
    }

    /**
     * Return contact data by specified contact id
     *
     * @param id the contact's id
     * @return contact by specified contact id
     */
    public static Contact getById(int id) {
        Optional<Contact> matchingId = Optional.ofNullable(contacts.stream()
                .filter(p -> p.getId() == id)
                .findFirst().orElse(null));

        return matchingId.get();
    }
}
