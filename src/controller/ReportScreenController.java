package controller;

import dto.CustomerReportDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.HBox;
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
import utilities.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controller class that controls the logic of the report screen.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class ReportScreenController implements Initializable {

    private final String SELECT_BY_TYPE = "Select type AS TYPE, COUNT(Customer_ID) AS numOfCustomers, YEAR(Start) AS yr, MONTH(Start) AS mm " +
            "from appointments " +
            "group by YEAR(Start), MONTH(Start), type";

    public TextArea customerReportTextArea;

    /**
     * Tree Table view for list all the contacts' appointments.
     */
    public TreeTableView<Appointment> contactReportTable;

    /**
     * Table view for displaying user and customer account updates.
     */
    public TableView accountUpdateTable;

    /**
     * Table Column that displays the ID of a user.
     */
    public TableColumn<CustomerReportDTO, String> user_col;

    /**
     * Table Column that displays the last updated.
     */
    public TableColumn<CustomerReportDTO, String> lastUpdated_col;

    /**
     * Table Column that displays the last updated by.
     */
    public TableColumn<CustomerReportDTO, String> lastUpdatedBy_col;

    public TableColumn<CustomerReportDTO, String> dateCreated_col;

    /**
     * TreeTableColumn that displays the Contact of a appointment.
     */
    public TreeTableColumn<Appointment, String> contactTreeCol;

    /**
     * TreeTableColumn that displays the appointment Id of a appointment.
     */
    public TreeTableColumn<Appointment, Integer> appIDTreeCol;

    /**
     * TreeTableColumn that displays the Title of a appointment.
     */
    public TreeTableColumn<Appointment, String> titleTreeCol;

    /**
     * TreeTableColumn that displays the Type of a appointment.
     */
    public TreeTableColumn<Appointment, String> typeTreeCol;

    /**
     * TreeTableColumn that displays the Description of a appointment.
     */
    public TreeTableColumn<Appointment, String> descTreeCol;

    /**
     * TreeTableColumn that displays the Date of a appointment.
     */
    public TreeTableColumn<Appointment, String> dateTreeCol;

    /**
     * TreeTableColumn that displays the StartTime of a appointment.
     */
    public TreeTableColumn<Appointment, String> startTimerTreeCol;

    /**
     * TreeTableColumn that displays the EndTime of a appointment
     */
    public TreeTableColumn<Appointment, String> endTimeTreeCol;

    /**
     * TreeTableColumn that displays the Customer Id.
     */
    public TreeTableColumn<Appointment, Integer> customerIdTreeCol;

    /**
     * displays the location of the appointment.
     */
    public TreeTableColumn<Appointment, String> locationCol;

    public RadioButton userAccountRButton;
    public RadioButton customerAccountRButton;

    private ObservableList<CustomerReportDTO> userReportList;
    private ObservableList<CustomerReportDTO> customerReportList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactReportTable.setRoot(generateContactReport());
        contactReportTable.setShowRoot(false);

        appIDTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        //contactTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("Contact"));
        titleTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        descTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        customerIdTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerId"));
        typeTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
        startTimerTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("startTime"));
        endTimeTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("endTime"));
        dateTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateTime"));
        locationCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("location"));

        contactReportTable.expandedItemCountProperty().addListener(e -> {
            contactReportTable.refresh();
        });

        lastUpdated_col.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedAgo"));
        lastUpdatedBy_col.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        dateCreated_col.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));

        customerReportTextArea.setScrollTop(Double.MAX_VALUE);
        customerReportTextArea.setEditable(false);

        generateCustomerReport(customerReportTextArea);

        collectAccountUpdatesReport();

        accountUpdateTable.setPlaceholder(new Label("Select one of button to check"));
    }

    private void generateCustomerReport(TextArea customerReportTextArea) {

        try (final PreparedStatement ps = DBConnection.getConnection().prepareStatement(SELECT_BY_TYPE)) {
            ResultSet rs = ps.executeQuery();
            boolean changeMonth = true;
            String currentMonth = "";

            if (rs.next()) {
                String type = rs.getString("TYPE");
                int numOfCustomers = rs.getInt("numOfCustomers");
                LocalDate date = LocalDate.of(rs.getInt("yr"), rs.getInt("mm"), 1);
                currentMonth = date.getMonth().toString() + " " + date.getYear();

                customerReportTextArea.appendText(currentMonth + ": \n");
                customerReportTextArea.appendText("             " + type + " - " + numOfCustomers + "\n");
            }

            while (rs.next()) {
                String type = rs.getString("TYPE");
                int numOfCustomers = rs.getInt("numOfCustomers");
                LocalDate date = LocalDate.of(rs.getInt("yr"), rs.getInt("mm"), 1);
                String datetime = date.getMonth().toString() + " " + date.getYear();

                if (!currentMonth.equals(datetime)) {
                    currentMonth = datetime;
                    customerReportTextArea.appendText("\n" + currentMonth + ": \n");
                }

                customerReportTextArea.appendText("             " + type + " - " + numOfCustomers + "\n");
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }

    }


    private TreeItem<Appointment> generateContactReport() {
        TreeItem<Appointment> mainRoot = new TreeItem<>();

        for (Contact contact : ContactRepository.getAllContact()) {
            ObservableList<Appointment> appointments = FXCollections.observableList(AppointmentRepository.selectByContactID(contact.getId()));

            String currentContact = contact.getContactName();
            TreeItem root = new TreeItem();

            for (Appointment appointment : appointments) {
                TreeItem<Appointment> branch = new TreeItem(appointment);
                root.getChildren().add(branch);
            }

            HBox hbox = new HBox();
            Label label = new Label(currentContact.substring(0, 1).toUpperCase() + currentContact.substring(1).toLowerCase() + " (" + appointments.size() + ")");
            label.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            label.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            hbox.getChildren().add(label);
            hbox.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            hbox.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            root.setGraphic(hbox);
            root.setExpanded(true);

            mainRoot.getChildren().add(root);
        }

        return mainRoot;
    }

    private void collectAccountUpdatesReport() {
        userReportList = FXCollections.observableArrayList();
        customerReportList = FXCollections.observableArrayList();

        for (User user : UserRepository.selectAll()) {
            String user_data = user.getUserName() + " (" + user.getUserId() + ")";
            CustomerReportDTO dto = new CustomerReportDTO(user_data, null,
                    user.getLastUpdate().toLocalDateTime(), user.getLastUpdatedBy(), user.getDateCreated());
            userReportList.add(dto);
        }

        for (Customer customer : CustomerRepository.selectAll()) {
            String customer_data = customer.getName() + " (" + customer.getId() + ")";
            customerReportList.add(new CustomerReportDTO(null, customer_data,
                    customer.getLastUpdated().toLocalDateTime(), customer.getLastUpdatedBy(), customer.getDateCreated()));
        }

    }

    public void checkUserAccount(ActionEvent actionEvent) {
        user_col.setText("User Data");

        accountUpdateTable.setItems(null);
        accountUpdateTable.refresh();
        accountUpdateTable.setItems(userReportList);
        user_col.setCellValueFactory(new PropertyValueFactory<>("userData"));
        accountUpdateTable.refresh();
    }

    public void checkCustomerAccount(ActionEvent actionEvent) {
        user_col.setText("Customer Data");

        accountUpdateTable.setItems(null);
        accountUpdateTable.refresh();
        accountUpdateTable.setItems(customerReportList);
        user_col.setCellValueFactory(new PropertyValueFactory<>("customerData"));
        accountUpdateTable.refresh();
    }

    public void exitActionHandler(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainScreen.fxml"));
            Parent root = loader.load();
            Main.setScene(new Scene(root));

        } catch (IOException e) {
            AlertBoxHandler.displayAlert(Alert.AlertType.ERROR, "mainScreen.fxml");
        }
    }

}
