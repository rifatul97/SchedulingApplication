package controller;

import dao.AppointmentDAO;
import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import model.User;
import repository.AppointmentRepository;
import repository.UserRepository;
import utilities.AlertBoxHandler;
import utilities.LogHandler;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * A controller class that handles the logic of login screen functionalities
 */
public class LoginScreenController implements Initializable {

    /**
     * label to display current user's zone
     */
    public Label hBoxLabel;
    /**
     * TextField that takes the username input
     */
    @FXML
    private TextField userNameTextField;

    /**
     * TextField that takes the password input
     */
    @FXML
    private TextField passwordTextField;

    /**
     * Label to display the error if username is not found in the database.
     */
    @FXML
    private Label userNameErrorLabel;

    /**
     * Label that display the error of the incorrect password.
     */
    @FXML
    private Label passwordErrorLabel;

    private static UserDAO userDAO;
    private static AppointmentDAO appointmentDAO;
    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hBoxLabel.setText(ZoneId.systemDefault().toString());
        this.rb = resourceBundle;
        cleanErrorLabels();
    }

    /**
     * This function is executed as the user clicks the login button and it checks the username
     * and password and if it is found, the user is taken to the main screen of the application. Otherwise, error
     * message is displayed accordingly onto the main screen. The login attempts also recorded into the log file.
     *
     * @param actionEvent
     */
    public void loginClickHandler(ActionEvent actionEvent) {
        this.cleanErrorLabels();

        String userNameInput = userNameTextField.getText().trim();
        String passwordInput = passwordTextField.getText().trim();

        User user = UserRepository.matchUsernameAndPassword(userNameInput, passwordInput);

        if (user != null) {

            try {
                if(AppointmentRepository.displayIfAnyUpcomingAppointment()) {

                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainScreen.fxml"));
                Parent root = loader.load();



                MainScreenController mainScreenController = loader.getController();
                LogHandler.logUserLoginSuccess(user.getUserName());
                mainScreenController.setCurrentUser(user);


                Main.setScene(new Scene(root));

            } catch (IOException e) {
                AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, e.getLocalizedMessage());
                System.out.println(e.getCause());
            }
        } else if (UserRepository.matchUsername(userNameInput)) {
            passwordErrorLabel.setVisible(true);
            LogHandler.logUserLoginFailure(userNameInput);
        } else {
            userNameErrorLabel.setVisible(true);
            //ScreenExceptionHandler.showErrorMsg(1, userNameErrorLabel);
            LogHandler.logUnknownUsernameLogin(userNameTextField.getText());
        }


    }

    /**
     * Removes the error labels from the screen.
     */
    public void cleanErrorLabels() {
        userNameErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
    }

}
