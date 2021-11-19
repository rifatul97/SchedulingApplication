package utilities;

import controller.LoginScreenController;
import controller.MainScreenController;

import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

/**
 * Handle recording login attempts to a file.
 *
 * @Author Rifatul Karim
 * @Version 1.0
 */
public abstract class LogHandler {

    public static Logger log;
    public static int numberOfAttempts = 0;
    private static logState state;
    //public static final DateTimeFormatter logTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    enum logState {
        FIRST_LOGIN_SUCCESS, SUCCESS, FAILURE, UNKNOWN_USER_LOGIN
    }

    /**
     * passes the log state information to the file.
     *
     * @param username the username field from the login screen.
     */
    public static void log(String username) {

        try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("login_activity.txt"),true));){

            switch (state) {
                case SUCCESS:
                    pw.append(username + " has logged in " + Timestamp.from(Instant.now()) + "after " + numberOfAttempts + "attempts\n");
                    break;
                case FIRST_LOGIN_SUCCESS:
                    pw.append(username + " has successfully logged in " + Timestamp.from(Instant.now()) + "\n");
                    break;
                case FAILURE:
                    pw.append(username + " couldn't logged in " + Timestamp.from(Instant.now()) + "\n");
                    break;
                case UNKNOWN_USER_LOGIN:
                    pw.append("one unknown username \"" + username + "\" tried to login on " + TimeManager.getNow() + "\n");
                default: break;
            }

            pw.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LogHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * sends the information to the file when user correctly input both username and
     * password. If the user could not login within first attempt, number of attempts
     * is written to the file.
     *
     * @param username from the username field of the login screen
     */
    public static void logUserLoginSuccess(String username) {
        state = numberOfAttempts > 1 ? logState.SUCCESS : logState.FIRST_LOGIN_SUCCESS;
        log(username);
    }

    /**
     * sends the information to the file when user enters incorrect password
     *
     * @param username from the username field of the login screen
     */
    public static void logUserLoginFailure(String username) {
        numberOfAttempts++;
        state = logState.FAILURE;
        log(username);
    }

    /**
     * sends the information to the file when user enters a username that was not
     * registered in the database.
     *
     * @param username from the username field of the login screen
     */
    public static void logUnknownUsernameLogin(String username) {
        state = logState.UNKNOWN_USER_LOGIN;
        log(username);
    }

}

