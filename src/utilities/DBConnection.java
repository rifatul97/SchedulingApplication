package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that handles to start, get and close connection from the database.
 *
 * @Author Rifatul Karim
 * @Version 1.0
 */
public abstract class DBConnection {

    private static final String PROTOCOL = "jdbc";
    private static final String VENDORNAME = ":mysql:";
    private static final String IPADDRESS = "//wgudb.ucertify.com:3306/";
    private static final String DBNAME = "WJ08ppv"; //NOTE: Adding the dbName
    private static final String USERNAME = "U08ppv";
    private static final String PORT = "3306";
    private static final String PASSWORD = "53689358965";

    private static final String jdbcURL = PROTOCOL + VENDORNAME + IPADDRESS + DBNAME;
            //+ "?connectionTimeZone=SERVER";

    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection connection = null;

    /**
     * Starts the connection
     * @return gets the connection
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            connection = DriverManager.getConnection(jdbcURL, USERNAME, PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Gets the connection
     * @return Connection
     */
    public static Connection getConnection() {

        if (connection == null) {
            startConnection();
        }

        return connection;
    }

    /**
     * Closes the connection
     */
    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
        }
    }

}
