package repository;

/**
 * A repository acts like an in-memory domain object collection that can be potentially use for
 * unit testing.
 *
 * This class objective is to initialize all other repositories in one function.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class RepositoryFactory {

    public static CountriesRepository countriesRepository;
    public static FirstLevelDivisionRepository firstLevelDivisionRepository;
    public static UserRepository userRepository;
    public static ContactRepository contactRepository;
    public static AppointmentRepository appointmentRepository;
    public static CustomerRepository customerRepository;

    public static void loadDBData() {
        countriesRepository.populateData();
        firstLevelDivisionRepository.populateData();
        appointmentRepository.populateData();
        contactRepository.populateData();
        userRepository.populateData();
        customerRepository.populateData();

    }
}
