package controller;

import dto.CustomerDTO;
import dto.CustomerTableDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import model.Customer;
import repository.AppointmentRepository;
import repository.CustomerRepository;
import utilities.AlertBoxHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that controls the logic of the customer screen to display
 * the list of customer stored in the database.
 *
 * <p><b>FUTURE ENHANCEMENT</b></p>
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class CustomerScreenController implements Initializable {

    /**
     * Table view for listing the customers from the database.
     */
    public TableView<CustomerTableDTO> customerTableView;

    /**
     * Table Column that displays the ID of a customer.
     */
    public TableColumn<CustomerDTO, Integer> customerId_Col;

    /**
     * Table Column that displays the Name of a Customer.
     */
    public TableColumn<CustomerDTO, String> customerName_Col;

    /**
     * Table Column that displays the Division of a Customer.
     */
    public TableColumn<CustomerDTO, String> customerFLD_Col;


    /**
     * The customer list
     */
    private ObservableList<CustomerTableDTO> customersFldList;

    /**
     * This step is to populate the data from customerRepository to the table.
     * The table displays the customer Id, Name and the Division.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainScreenController.currentScreenSelection = MainScreenController.ScreenState.MAIN;
        customersFldList = FXCollections.observableArrayList();

        for (Customer customer : CustomerRepository.selectAll()) {
            CustomerTableDTO dto = new CustomerTableDTO(customer.getId(),
                    customer.getName(),
                    CustomerRepository.getDivisionById(customer.getDivisionId()),
                    customer.getDateCreated());
            customersFldList.add(dto);
        }

        customerId_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerFLD_Col.setCellValueFactory(new PropertyValueFactory<>("division"));

        customerTableView.setItems(customersFldList);

    }

    /**
     * Switches to the customerEditScreen to add new customer.
     *
     * @param actionEvent Action Event
     */
    public void addNewClickHandler(ActionEvent actionEvent) {
        MainScreenController.currentScreenSelection = MainScreenController.ScreenState.ADD;
        toSecondScreen(1);
    }

    /**
     * Modify the customer data selected from the table and takes the user to the customerEditScreen.
     *
     * @param actionEvent
     */
    public void modifyCustomerClickHandler(ActionEvent actionEvent) {
        CustomerTableDTO selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            return;
        }

        try {
            MainScreenController.currentScreenSelection = MainScreenController.ScreenState.MODIFY;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/customerEditScreen.fxml"));
            Parent root = loader.load();

            Customer customer = CustomerRepository.selectById(selectedCustomer.getId());

            CustomerEditScreenController customerEditScreenController = loader.getController();
            customerEditScreenController.setCurrentSelectedCustomer(customer);

            Main.setScene(new Scene(root));

        } catch (IOException e) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, e.getLocalizedMessage());
            MainScreenController.currentScreenSelection = MainScreenController.ScreenState.MAIN;
        }
    }

    /**
     * Deletes the selected customer from the table. The user is given a second thought by showing
     * the confirmation temporary alert screen.
     *
     * @param actionEvent
     */
    public void deleteCustomerClickHandler(ActionEvent actionEvent) {
        CustomerTableDTO selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            if (AlertBoxHandler.displayConfirmationAlert("Do you wish to delete Customer#" + selectedCustomer.getId() + " ?")) {
                if (AppointmentRepository.checkIfCustomerHasAppointments(selectedCustomer.getId())) {
                    AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, "Sorry this customer cannot be deleted" +
                            " because this customer Id has made appointment scheduled.");
                    return;
                }

                CustomerRepository.delete(selectedCustomer.getId());
                CustomerRepository.refresh();
                refreshTable();
            }
        }

    }

    /**
     * Refreshes the table to display up-to date information
     */
    private void refreshTable() {
        customersFldList.clear();
        for (Customer customer : CustomerRepository.selectAll()) {
            CustomerTableDTO dto = new CustomerTableDTO(customer.getId(),
                    customer.getName(),
                    CustomerRepository.getDivisionById(customer.getDivisionId()),
                    customer.getDateCreated());
            customersFldList.add(dto);
        }
        customerTableView.refresh();
    }

    /**
     * Exits the screen and switches to the main screen.
     *
     * @param actionEvent
     */
    public void exitCustomerScreenAction(ActionEvent actionEvent) {
        MainScreenController.currentScreenSelection = MainScreenController.ScreenState.MAIN;
        toSecondScreen(0);
    }

    /**
     * Takes the user to the Add new customer or Main screen.
     *
     * @param type type of the screen to switch
     */
    public void toSecondScreen(int type) {
        String filePath = "";

        switch (type) {
            case 1:
                filePath = "customerEditScreen.fxml";
                break;
            case 0:
                filePath = "mainScreen.fxml";
                break;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/" + filePath));
            Parent root = loader.load();

            Main.setScene(new Scene(root));

        } catch (IOException e) {
            //AlertBoxHandler.displayWarningDialogue(filePath + ".fxml");
        }
    }


}
