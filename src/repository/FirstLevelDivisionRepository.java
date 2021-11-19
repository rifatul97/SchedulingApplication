package repository;

import dao.FirstLevelDivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.util.Optional;

/**
 * This repository class provide a higher level of First Level Division data CRUD manipulation and also
 * aims to hide their complexities.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class FirstLevelDivisionRepository {

    /**
     * the list of division data
     */
    private static ObservableList<FirstLevelDivision> fldList;

    /**
     * populates the division list from the database
     */
    public static void populateData() {
        fldList = FirstLevelDivisionDAO.selectAll();
    }

    public static ObservableList<FirstLevelDivision> getAll() {
        return fldList;
    }

    /**
     * @param country_id the country's ID
     * @return the list of division list that have the same country_id
     */
    public static ObservableList<FirstLevelDivision> getByCountryId(int country_id) {
        ObservableList<FirstLevelDivision> fldListById = FXCollections.observableArrayList();

        for (FirstLevelDivision fld : fldList) {
            if (fld.getCountryId() == country_id) {
                fldListById.add(fld);
            }
        }

        return fldListById;
    }

    /**
     * @param division_id the division's ID
     * @return the country's Id that have the same division_id accordingly
     */
    public static int getCountryIdByDivisionId(int division_id) {
        return getById(division_id).getCountryId();
    }

    /**
     * @param div_id the Division's id
     * @return the name of the division
     */
    public static String getDivisionById(int div_id) {
        return getById(div_id).getDivision();
    }

    /**
     * @param fld_id the division id
     * @return the division data that has the same division id of the parameter
     * or else null to indicate it was not found.
     */
    public static FirstLevelDivision getById(int fld_id) {
        Optional<FirstLevelDivision> matchingDivisionId = Optional.ofNullable(fldList.stream()
                .filter(p -> p.getId() == fld_id)
                .findFirst().orElse(null));

        return matchingDivisionId.get();
    }
}
