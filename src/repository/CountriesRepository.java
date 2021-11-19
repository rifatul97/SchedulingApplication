package repository;

import dao.CountriesDAO;
import javafx.collections.ObservableList;
import model.Country;

import java.util.Optional;

/**
 * This repository class provide a higher level of countries data CRUD manipulation and also
 * aims to hide their complexities.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class CountriesRepository {

    /**
     * the list of country data
     */
    private static ObservableList<Country> countryList;

    public CountriesRepository() {
    }

    /**
     * @return all the country data list
     */
    public static ObservableList<Country> getAll() {
        return countryList;
    }

    /**
     * <p>***<b>Lamba Expression Discussion</b>***:
     *  I have used lambda here to obtain object by Id.
     *  At first, it filters out the object that do match the id and returns the first orelse it evaluates to null.
     *  </p>
     *
     * @param country_id the id of the country data
     * @return the country data by id
     */
    public static Country getById(int country_id) {
        Optional<Country> matchingCountryId = Optional.ofNullable(countryList.stream()
                .filter(p -> p.getId() == country_id)
                .findFirst().orElse(null));

        return matchingCountryId.get();
    }

    /**
     * populate country list from the database
     */
    public static void populateData() {
        countryList = CountriesDAO.selectAll();
    }
}
