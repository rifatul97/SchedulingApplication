package controller;

import dto.AppointmentDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import main.Main;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import repository.AppointmentRepository;
import repository.ContactRepository;
import repository.CustomerRepository;
import repository.UserRepository;
import utilities.AlertBoxHandler;
import utilities.TimeManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * Controller class that controls the logic of the appointment edit screen
 * that is calls when the user add new or modify a appointment from the Main screen of the application.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class AppointmentEditScreenController implements Initializable {

    /**
     * The main label of the screen.
     */
    @FXML
    public Label mainLabel;

    /**
     * The contact combo box.
     */
    @FXML
    public ComboBox<Contact> contactComboBox;

    /**
     * TextField to input the title of the appointment.
     */
    @FXML
    public TextField titleTextField;

    /**
     * TextField to input the Type of the appointment.
     */
    @FXML
    public TextField appointmentTypeField;

    /**
     * The TextField for the id of the appointment. This field is always disabled.
     */
    @FXML
    public TextField idTextField;

    /**
     * The current selected appointment from the main table to modify in the edit screen.
     */
    @FXML
    public static Appointment currentSelectedAppointment;

    @FXML
    public static ContactRepository contactRepository;

    /**
     * TextField to input the Location of the appointment.
     */
    @FXML
    public TextField locationTextfField;

    /**
     * TextField to input the Description of the appointment.
     */
    @FXML
    public TextArea descTextField;

    //@FXML public Label dateTextField;

    /**
     * Tool to pick a date for the appointment.
     */
    @FXML
    public DatePicker datePicker;

    /**
     * ComboBox to select the startTime of the appointment.
     */
    @FXML
    public ComboBox<String> startTimeField;

    /**
     * ComboBox to select the endTime of the appointment.
     * On add screen, this is disabled until the user selects the startTime of the appointment.
     */
    @FXML
    public ComboBox<String> endTimeField;

    /**
     * ComboBox to select a customer from the list of their Id.
     */
    @FXML
    public ComboBox<Customer> customerIDChoiceBox;
    public ComboBox<User> userIdButton;

    public Label contactErrorLabel;
    public Label dateErrorLabel;
    public Label locationErrorLabel;
    public Label startTimeErrorLabel;
    public Label endTimeErrorLabel;
    public Label titleErrorLabel;
    public Label typeErrorLabel;
    public Label userIdFieldErrorLabel;
    public Label descErrorLabel;



    /**
     * Initializes when this screen loads and before the stage sets up the scene.
     * This step is required to insert the values of the ComboBoxes and set the main label
     * to distinguish the current screen.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactComboBox.setItems(ContactRepository.getAllContact());
        customerIDChoiceBox.setItems(CustomerRepository.selectAll());
        userIdButton.setItems(UserRepository.selectAll());

        datePicker.setEditable(false);
        startTimeField.setItems(TimeManager.fillComboBoxStr(LocalTime.of(0, 00)));

        idTextField.setDisable(true);

        switch (MainScreenController.currentScreenSelection) {
            case ADD:
                mainLabel.setText("Add Appointment");
                endTimeField.setDisable(true);
                break;
            case MODIFY:
                mainLabel.setText("Modify Appointment");
                break;
        }

    }

    /**
     * This function is executes once the user clicks the Save button.
     * Firstly, the field validity are checked and once after that, the data is updated/saved into the database
     * and the main table is repopulate and reflects the changes.
     *
     * @param actionEvent Action Event
     */
    public void submitActionHandler(ActionEvent actionEvent) throws SQLException {
        if (checkErrorFound() != false) {
            return;
        }

        LocalDate datePicked = datePicker.getValue();
        LocalTime startTimePicked = TimeManager.strToLocalTimeFormat(startTimeField.getSelectionModel().getSelectedItem());
        LocalTime endTimePicked = TimeManager.strToLocalTimeFormat(endTimeField.getSelectionModel().getSelectedItem());

        LocalDateTime startDateTimePicked = LocalDateTime.of(datePicked, startTimePicked);
        LocalDateTime endDateTimePicked = LocalDateTime.of(datePicked, endTimePicked);

        int temp_appointment_Id = currentSelectedAppointment == null ? -1 : currentSelectedAppointment.getId();
        //System.out.println("StartDateTime picked: " + startDateTimePicked.format(DateTimeFormatter.ofPattern("HH:mm mm:dd")));

        if (endDateTimePicked.isBefore(startDateTimePicked)) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, "Appointment cannot have Start Time after the End Time!");
            return;
        } else if (TimeManager.checkIfOutsideBusinessHours(startDateTimePicked, endDateTimePicked)) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, "Sorry the time selected are outside business hours!\nTry different Time!");
            return;
        } else if (AppointmentRepository.ifSelectedAppointmentTimeOverlaps(temp_appointment_Id, startDateTimePicked, endDateTimePicked)) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, "Sorry there are already other appointments scheduled in the selected time!\nTry different time!");
            return;
        }

        int contactId = contactComboBox.getSelectionModel().getSelectedItem().getId();
        int customerId = customerIDChoiceBox.getSelectionModel().getSelectedItem().getId();
        int userId = userIdButton.getSelectionModel().getSelectedItem().getUserId();

        if (MainScreenController.currentScreenSelection == MainScreenController.ScreenState.MODIFY) {

            AppointmentDTO appointmentDTO = new AppointmentDTO(titleTextField.getText(), descTextField.getText(),
                    locationTextfField.getText(), appointmentTypeField.getText(), startDateTimePicked.atZone(ZoneId.systemDefault()), endDateTimePicked.atZone(ZoneId.systemDefault()),
                    currentSelectedAppointment.getDateCreated(), currentSelectedAppointment.getCreatedBy(), MainScreenController.currentLoggedInUser.getUserName(),
                    customerId, userId, contactId);

            AppointmentRepository.update(currentSelectedAppointment.getId(), appointmentDTO);

        } else {
            AppointmentRepository.insert(new AppointmentDTO(titleTextField.getText(), descTextField.getText(),
                    locationTextfField.getText(), appointmentTypeField.getText(), startDateTimePicked.atZone(ZoneId.systemDefault()),
                    endDateTimePicked.atZone(ZoneId.systemDefault()), contactId, userId, customerId));
        }

        // this is to repopulate the appointment data so that the tables of the main table reflect the changes.
        AppointmentRepository.refresh();

        // After adding/updating, the application switches the screen to the main.
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainScreen.fxml"));
            Parent root = loader.load();

            MainScreenController mainScreenController = loader.getController();
            Main.setScene(new Scene(root));

        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public void startTimeChoiceHandler(ActionEvent actionEvent) {
        if (startTimeField.getSelectionModel().getSelectedItem() != null) {
            endTimeField.getItems().clear();
            endTimeField.setDisable(false);

            LocalTime startTime = TimeManager.strToLocalTimeFormat(startTimeField.getSelectionModel().getSelectedItem());
            endTimeField.setItems(TimeManager.fillComboBoxStr(startTime.plusMinutes(30)));
        }
    }

    /**
     * The objective of this function is to display the data of the appointment to modify which is selected
     * from the MainScreen table.
     * This function is called from the MainController class before the scene is set.
     *
     * @param appointmentToModify appointment selected from the main screen table.
     */
    public void setCurrentAppointmentToModify(Appointment appointmentToModify) {
        this.currentSelectedAppointment = appointmentToModify;

        int startTimeHourHand = currentSelectedAppointment.getStartDateTime().getHour();
        int startTimeMinuteHand = currentSelectedAppointment.getStartDateTime().getMinute();

        titleTextField.setText(appointmentToModify.getTitle());
        idTextField.setText(appointmentToModify.getId() + "");
        appointmentTypeField.setText(appointmentToModify.getType());
        descTextField.setText(appointmentToModify.getDescription());
        locationTextfField.setText(appointmentToModify.getLocation());

        startTimeField.getSelectionModel().select(TimeManager.To12hrTimeFormat(currentSelectedAppointment.getStartDateTime().toLocalTime()));
        endTimeField.getSelectionModel().select(TimeManager.To12hrTimeFormat(currentSelectedAppointment.getEndDateTime().toLocalTime()));

        datePicker.setValue(appointmentToModify.getStartDateTime().toLocalDate());
        customerIDChoiceBox.getSelectionModel().select(CustomerRepository.getById(appointmentToModify.getCustomerId()));
        contactComboBox.getSelectionModel().select(ContactRepository.getById(appointmentToModify.getContactId()));
        userIdButton.getSelectionModel().select(UserRepository.selectById(appointmentToModify.getUserId()));

        endTimeField.setItems(TimeManager.fillComboBoxStr(LocalTime.of(startTimeHourHand, startTimeMinuteHand).plusMinutes(15)));

    }

    /**
     * Converts the ComboBox choice to LocalTime format.
     *
     * @param comboBox
     * @return LocalTime selected from the comboBox
     */
    public LocalTime getSelectedTimeFromComboBox(ComboBox<LocalTime> comboBox) {
        int hourHand = comboBox.getSelectionModel().getSelectedItem().getHour();
        int minuteHand = comboBox.getSelectionModel().getSelectedItem().getMinute();

        return LocalTime.of(hourHand, minuteHand);
    }

    private boolean checkErrorFound() {
        cleanErrorLabels();
        boolean errorFound = false;

        if (titleTextField.getText().length() == 0) {
            titleErrorLabel.setText("This field cannot be empty.");
            errorFound = true;
        }
        if (descTextField.getText().length() == 0) {
            descErrorLabel.setText("This field cannot be empty.");
            errorFound = true;
        }
        if (locationTextfField.getText().length() == 0) {
            locationErrorLabel.setText("This field cannot be empty.");
            errorFound = true;
        }
        if (datePicker.getValue() == null) {
            dateErrorLabel.setText("This field cannot be empty.");
            errorFound = true;
        }
        if (userIdButton.getSelectionModel().getSelectedItem() == null) {
            userIdFieldErrorLabel.setText("Select one user.");
            errorFound = true;
        }
        if (contactComboBox.getSelectionModel().getSelectedItem() == null) {
            contactErrorLabel.setText("You must select one");
            errorFound = true;
        }
        if (customerIDChoiceBox.getSelectionModel().getSelectedItem() == null) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, "Customer is not selected. You must pick one!");
            errorFound = true;
        }
        if (startTimeField.getSelectionModel().getSelectedItem() == null) {
            startTimeErrorLabel.setText("The appointment must have both start and end Time");
            errorFound = true;
        }
        if (endTimeField.getSelectionModel().getSelectedItem() == null) {
            endTimeErrorLabel.setText("This field cannot be empty.");
            errorFound = true;
        }
        if (titleTextField.getText().length() == 0) {
            titleErrorLabel.setText("This field cannot be empty.");
            errorFound = true;
        }

        return errorFound;
    }

    /**
     * Switches the mainStage to the main screen when the user clicks the Exit button.
     *
     * @param actionEvent Action Event
     */
    public void cancelActionHandler(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainScreen.fxml"));
            Parent root = loader.load();
            Main.setScene(new Scene(root));
        } catch (IOException e) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, "mainScreen.fxml");
        }
    }

    /*public void fillTextFields() {
        titleTextField.setText("title");
        descTextField.setText("description");
        appointmentTypeField.setText("type");
        locationTextfField.setText("location");
    }*/

    public void cleanErrorLabels() {
        userIdFieldErrorLabel.setText("");
        endTimeErrorLabel.setText("");
        startTimeErrorLabel.setText("");
        dateErrorLabel.setText("");
        contactErrorLabel.setText("");
        descErrorLabel.setText("");
        typeErrorLabel.setText("");
        locationErrorLabel.setText("");
    }

    public void userIdComboBoxAction(ActionEvent actionEvent) {
    }
}