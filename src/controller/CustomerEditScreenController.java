package controller;

import dto.CustomerDTO;
import exception.ScreenExceptionHandler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import main.Main;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import repository.CountriesRepository;
import repository.CustomerRepository;
import repository.FirstLevelDivisionRepository;
import utilities.AlertBoxHandler;
import utilities.TimeManager;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * Controller class that controls the logic of the customer edit screen
 * that is calls when the user add new or modify a appointment from the Customer screen of the application.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class CustomerEditScreenController implements Initializable {

    /**
     * The main label of the screen.
     */
    @FXML
    private Label mainLabel;

    /**
     * The textfield for inputting postal code.
     */
    @FXML
    private TextField pcodeTextField;

    /**
     * The textfield for inputting address.
     */
    @FXML
    private TextArea addressTextField;

    /**
     * The textfield for inputting phoneNumber.
     */
    @FXML
    private TextField phoneTextField;

    /**
     * The ComboBox that will display one of many Country choice to select.
     */
    @FXML
    private ComboBox<Country> countryChoiceBox;

    /**
     * The ComboBox that will display one of many city choice to select.
     */
    @FXML
    private ComboBox<FirstLevelDivision> cityChoiceBox;

    /**
     * The textfield for the Customer Id. This is always in read-only mode.
     */
    @FXML
    private TextField idTextField;

    /**
     * The textfield for the Customer Name.
     */
    @FXML
    private TextField nameTextField;

    /**
     * Label for displaying error from a Name TextField.
     */
    @FXML
    private Label nameFieldErrorLabel;

    /**
     * Label for displaying error from a Country combo box.
     */
    @FXML
    private Label countryFieldErrorLabel;
    @FXML

    /**
     * Label for displaying error from a City combo box.
     */
    private Label cityFieldErrorLabel;
    @FXML

    /**
     * Label for displaying error from a Postal Code TextField.
     */
    private Label pcodeFieldErrorLabel;


    /**
     * Label for displaying error from a Address TextField.
     */
    @FXML
    private Label addressFieldErrorLabel;

    /**
     * Label for displaying error from a phoneNumber TextField.
     */
    @FXML
    private Label phoneNumberFieldErrorLabel;


    /**
     * The division list
     */
    private ObservableList<FirstLevelDivision> fldList;

    /**
     * The selected customer selected from the table to modify
     */
    private Customer currentSelectedCustomer;

    private FirstLevelDivisionRepository firstLevelDivisionRepository;
    private CountriesRepository countriesRepository;

    /**
     * This step is to fill the Division, Country comboBox choices that is obtain from the repository
     * and finally adds the label to distinguish the current screen to the user.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fldList = firstLevelDivisionRepository.getAll();

        switch (MainScreenController.currentScreenSelection) {
            case ADD:
                mainLabel.setText("Add Customer");
                cityChoiceBox.setDisable(true);
                break;
            case MODIFY:
                mainLabel.setText("Modify Customer");
                break;
            default:
                break;
        }

        countryChoiceBox.setItems(countriesRepository.getAll());
    }

    /**
     * This function is very essential to remove the disable of the Division comboBox as the data
     * for the Division which depends on the country choice selected.
     *
     * @param actionEvent
     */
    public void countryChoiceHandler(ActionEvent actionEvent) {
        Country country = countryChoiceBox.getSelectionModel().getSelectedItem();

        // clears the cityChoiceBox whenever the user changes country comboBox choice and this way the other are reset.
        cityChoiceBox.getItems().clear();

        int selectedCountryId = country.getId();

        // removes the disable of the division comboBox
        cityChoiceBox.setDisable(false);

        for (FirstLevelDivision fld : firstLevelDivisionRepository.getByCountryId(selectedCountryId)) {
            cityChoiceBox.getItems().add(fld);
        }
    }

    /**
     * Saves or Update the data and takes the user to the customerScreen.
     *
     * @param actionEvent
     */
    public void saveActionHandler(ActionEvent actionEvent) {
        if (checkErrors()) { return; }

        FirstLevelDivision fld = cityChoiceBox.getSelectionModel().getSelectedItem();

        if (MainScreenController.currentScreenSelection == MainScreenController.ScreenState.MODIFY) {

            CustomerDTO customerDTO = new CustomerDTO(nameTextField.getText(),
                    currentSelectedCustomer.getDateCreated(),
                    addressTextField.getText(),
                    pcodeTextField.getText(),
                    phoneTextField.getText(), currentSelectedCustomer.getCreatedBy(),
                    TimeManager.getNow(),
                    MainScreenController.currentLoggedInUser.getUserName(),
                    fld.getId());

            CustomerRepository.update(currentSelectedCustomer.getId(), customerDTO);
        } else {
            CustomerDTO customerDTO = new CustomerDTO(nameTextField.getText(),
                    TimeManager.getNow().toLocalDateTime(),
                    addressTextField.getText(), pcodeTextField.getText(),
                    phoneTextField.getText(), MainScreenController.currentLoggedInUser.getUserName(),
                    new Timestamp(System.currentTimeMillis()),
                    MainScreenController.currentLoggedInUser.getUserName(),
                    fld.getId());

            CustomerRepository.insert(customerDTO);
        }

        CustomerRepository.refresh();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/customerScreen.fxml"));
            Parent root = loader.load();

            Main.setScene(new Scene(root));
        } catch (IOException e) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, "customerScreen.fxml");
        }

    }

    /**
     * Takes the user to the customerScreen without saving the data.
     *
     * @param actionEvent
     */
    public void cancelActionHandler(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/customerScreen.fxml"));
            Parent root = loader.load();

            Main.setScene(new Scene(root));
        } catch (IOException e) {
            //AlertBoxHandler.displayWarningDialogue("customerScreen.fxml");
        }
    }

    /**
     * check the errors of the data input.
     *
     * @return true if errors are found.
     */
    private boolean checkErrors() {
        boolean notfound = false;

        if (nameTextField.getText().trim().length() == 0) {
            ScreenExceptionHandler.showErrorMsg(4, nameFieldErrorLabel);
            notfound = true;
        }
        if (addressTextField.getText().trim().length() == 0) {
            ScreenExceptionHandler.showErrorMsg(4, addressFieldErrorLabel);
            notfound = true;
        }
        if (phoneTextField.getText().trim().length() == 0) {
            ScreenExceptionHandler.showErrorMsg(4, phoneNumberFieldErrorLabel);
            notfound = true;
        }
        if (pcodeTextField.getText().trim().length() == 0) {
            ScreenExceptionHandler.showErrorMsg(4, pcodeFieldErrorLabel);
            notfound = true;
        }
        if (countryChoiceBox.getSelectionModel().isEmpty()) {
            ScreenExceptionHandler.showErrorMsg(4, countryFieldErrorLabel);
            notfound = true;
        }
        if (cityChoiceBox.getSelectionModel().isEmpty()) {
            ScreenExceptionHandler.showErrorMsg(4, cityFieldErrorLabel);
            notfound = true;
        }

        return notfound;
    }


    /**
     * The objective of this function is to display the data of the customer to modify which is selected
     * from the customer table.
     *
     * @param customerToModify customer data selected from the table
     */
    public void setCurrentSelectedCustomer(Customer customerToModify) {
        this.currentSelectedCustomer = customerToModify;

        idTextField.setText(customerToModify.getId() + "");
        nameTextField.setText(customerToModify.getName());
        addressTextField.setText(customerToModify.getAddress());
        pcodeTextField.setText(customerToModify.getPostalCode());
        phoneTextField.setText(customerToModify.getPhoneNumber());

        int country_id = firstLevelDivisionRepository.getCountryIdByDivisionId(currentSelectedCustomer.getDivisionId());
        int fld_id = currentSelectedCustomer.getDivisionId();

        for (FirstLevelDivision fld : firstLevelDivisionRepository.getByCountryId(country_id)) {
            cityChoiceBox.getItems().add(fld);
        }

        countryChoiceBox.getSelectionModel().select(countriesRepository.getById(country_id));
        cityChoiceBox.getSelectionModel().select(firstLevelDivisionRepository.getById(fld_id));
    }

}
