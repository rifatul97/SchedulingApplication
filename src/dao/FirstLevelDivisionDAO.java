package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import utilities.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This FirstLevelDivision dao class directly from the database of data storage
 * to store division data.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class FirstLevelDivisionDAO {

    private static final String INSERT = "INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By," +
            " " + "Last_Update, Last_Updated_By, COUNTRY_ID) VALUES(?, NULL, ?, ?, NOW(), ?, ? )";

    private static final String SELECT_ALL = "SELECT Division_ID FROM first_level_divisions";

    private static final String SELECT_ALL_BY_CountryID = "SELECT Division_ID FROM first_level_divisions WHERE Country_ID = ?";

    private static final String GET_BY_ID = "SELECT * FROM first_level_divisions" + " "
            + "WHERE Division_ID = ?";

    private static final String DELETE_BY_ID = "DELETE FROM first_level_divisions WHERE Division_ID = ?";

    private static final String UPDATE_BY_ID = "UPDATE first_level_divisions" + " "
            + "SET Division = ?, Create_Date = ?, Created_By = ?," + " "
            + "Last_Update = ?, Last_Updated_By = ?, COUNTRY_ID = ? WHERE Division_ID = ?";

    /**
     * select division by Id directly from the database
     * @param id the id of the division to look for
     * @return the matching division data object
     */
    public static FirstLevelDivision selectById(int id) {
        FirstLevelDivision fld = new FirstLevelDivision();

        try(PreparedStatement ps = DBConnection.getConnection().prepareStatement(GET_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                fld.setId(id);
                fld.setCountryId(rs.getInt("Country_ID"));
                fld.setCreatedBy(rs.getString("Created_By"));
                //fld.setDateCreated(rs.getDate("Create_Date"));
                fld.setDivision(rs.getString("Division"));
                fld.setLastUpdated(rs.getTimestamp("Last_Update"));
                fld.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }

        return fld;
    }

    /**
     * return all the division data from the database
     * @return the list of division data
     */
    public static ObservableList<FirstLevelDivision> selectAll() {
        ObservableList<FirstLevelDivision> fldList = FXCollections.observableArrayList();

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");

                FirstLevelDivision fld = selectById(id);
                fldList.add(fld);
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }

        return fldList;
    }


    public static ObservableList<FirstLevelDivision> selectAllById(int countryId) {
        ObservableList<FirstLevelDivision> fldList = FXCollections.observableArrayList();

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(SELECT_ALL_BY_CountryID)) {
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");

                FirstLevelDivision fld = selectById(id);
                fldList.add(fld);
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }

        return fldList;
    }

    /**
     * update by id to the database
     * @param fld the id of division
     */
    public static void update(FirstLevelDivision fld) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(UPDATE_BY_ID,
                PreparedStatement.RETURN_GENERATED_KEYS);) {

            ps.setString(1, fld.getDivision());
            //ps.setDate(2, fld.getDateCreated());
            ps.setString(3, fld.getCreatedBy());
            ps.setTimestamp(4, fld.getLastUpdated());
            ps.setString(5, fld.getLastUpdatedBy());
            ps.setInt(6, fld.getCountryId());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * insert the division object data to the database
     * @param fld the division object
     */
    @Deprecated
    public static void insert(FirstLevelDivision fld) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(INSERT,
                PreparedStatement.RETURN_GENERATED_KEYS);) {

            ps.setString(1, fld.getDivision());
            //ps.setDate(2, fld.getDateCreated());
            ps.setString(3, fld.getCreatedBy());
            ps.setTimestamp(4, fld.getLastUpdated());
            ps.setString(5, fld.getLastUpdatedBy());
            ps.setInt(6, fld.getCountryId());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

}
