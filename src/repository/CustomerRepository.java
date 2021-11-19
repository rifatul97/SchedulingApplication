package repository;

import dao.CustomerDAO;
import dto.CustomerDTO;
import javafx.collections.ObservableList;
import model.Customer;

import java.util.Optional;

/**
 * This repository class provide a higher level of appointment data CRUD manipulation and also
 * aims to hide their complexities.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class CustomerRepository {

    /**
     * the list of customer data
     */
    private static ObservableList<Customer> customers;

    /**
     * obtains the customer data from the database and populate to the list.
     */
    public static void populateData() {
        customers = CustomerDAO.selectAll();
    }

    /**
     * @return the list of customer data obtained from the database
     */
    public static ObservableList<Customer> selectAll() {
        return customers;
    }

    /**
     * selects the customer data by id
     *
     * @param customer_Id the id of the customer to look for
     * @return the customer data that matched the specific customer id
     */
    public static Customer selectById(int customer_Id) {
        return CustomerDAO.selectById(customer_Id);
    }

    /**
     * transfers the customer data object to the customer dao which will insert into the database accordingly
     *
     * @param dto customer data transfer object
     */
    public static void insert(CustomerDTO dto) {
        CustomerDAO.insert(dto);
    }

    /**
     * transfers the customer data object to the customer dao which will update into the database accordingly
     *
     * @param dto         customer data transfer object
     * @param customer_id the customer id
     */
    public static void update(int customer_id, CustomerDTO dto) {
        CustomerDAO.update(customer_id, dto);
    }

    /**
     * transfers the customer id to the customer dao which will delete into the database accordingly
     *
     * @param id the customer's id
     */
    public static void delete(int id) {
        CustomerDAO.delete(id);
    }

    public static String getDivisionById(int division_id) {
        return FirstLevelDivisionRepository.getDivisionById(division_id);
    }

    /**
     * repopulate the list. This method usually called upon whenever CRUD operation
     * occurred on the application
     */
    public static void refresh() {
        customers.clear();
        customers = CustomerDAO.selectAll();
    }

    /**
     <p>***<b>Lamba Discussion</b>***:
     * I have used lambda here to obtain object by Id.
     * At first, it filters out the object that do match the id and returns the first orelse it evaluates to null.
     * </p>
     * @param id the customer id
     * @return the matching customer data
     */
    public static Customer getById(int id) {
        Optional<Customer> matchingCustomerId = Optional.ofNullable(customers.stream()
                .filter(p -> p.getId() == id)
                .findFirst().orElse(null));

        return matchingCustomerId.get();
    }
}
