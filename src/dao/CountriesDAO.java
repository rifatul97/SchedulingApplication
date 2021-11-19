package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utilities.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This country dao class directly from the database of data storage
 * to obtain country data.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class CountriesDAO {

    private static final String INSERT = "INSERT INTO countries VALUES(NULL, ?, ?, ?, ?)";

    private static final String SELECT_ALL = "SELECT Country_ID from countries";

    private static final String GET_BY_ID = "SELECT * FROM countries WHERE Country_ID = ?";

    private static final String DELETE_BY_ID = "DELETE FROM countries WHERE Country_ID = ?";

    private static final String UPDATE_BY_ID = "UPDATE countries" +
            " SET Country = ?,  Create_Date = ?, Created_By = ?, Last_Updated = ?, Last_Updated_By = ?" +
            " WHERE Country_ID = ?";

    private static final String SELECT_DATECREATED = "select Create_Date from countries";


    public static Country selectById(int id) {
        Country country = new Country();

        try(PreparedStatement ps = DBConnection.getConnection().prepareStatement(GET_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                country.setId(rs.getInt("Country_ID"));
                country.setCountryName(rs.getString("Country"));
                country.setDateCreated(rs.getObject("Create_Date", LocalDateTime.class));
                country.setCreatedBy(rs.getString("Created_By"));
                country.setLastUpdate(rs.getTimestamp("Last_Update"));
                country.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }

        return country;
    }

    public static ObservableList<Country> selectAll() {
        DBConnection.startConnection();
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Country_ID");

                Country country = selectById(id);
                countryList.add(country);
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }

        return countryList;
    }

    public static void update(Country country) {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(UPDATE_BY_ID,
                PreparedStatement.RETURN_GENERATED_KEYS);){

            ps.setString(1, country.getCountryName());
            //ps.setDate(2, dateCreated, dateCreated.);
            ps.setString(3, country.getCreatedBy());
            ps.setTimestamp(4, country.getLastUpdate());
            ps.setString(5, country.getLastUpdatedBy());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void insert(Country country)  {
        try(PreparedStatement ps = DBConnection.getConnection().prepareStatement(INSERT,
                PreparedStatement.RETURN_GENERATED_KEYS);){

            ps.setString(1, country.getCountryName());
            //ps.setDate(2, dateCreated, dateCreated.);
            ps.setString(3, country.getCreatedBy());
            ps.setTimestamp(4, country.getLastUpdate());
            ps.setString(5, country.getLastUpdatedBy());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public static void checkDateConversion() {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(SELECT_DATECREATED)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void delete(int index) {}

}
