package controller;

import javafx.collections.transformation.FilteredList;
import repository.AppointmentRepository;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import main.Main;
import model.Appointment;
import model.User;
import utilities.AlertBoxHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

/**
 * Controller class that controls the logic of the main screen of the application.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class MainScreenController implements Initializable {

    /**
     * Column of the allTable that displays the Appointment Id of a appointment.
     */
    public TableColumn<Appointment, Integer> ID_col;

    /**
     * Column of the allTable that displays the Customer Id of a appointment.
     */
    public TableColumn<Appointment, Integer> customerID_col;

    /**
     * Column of the allTable that displays the title of a appointment.
     */
    public TableColumn<Appointment, String> title_col;

    /**
     * Column of the allTable that displays the description of a appointment.
     */
    public TableColumn<Appointment, String> desc_col;

    /**
     * Column of the allTable that displays the location of a appointment.
     */
    public TableColumn<Appointment, String> location_col;
    
    /**
     * Column of the allTable that displays the Contact of a appointment.
     */
    public TableColumn<Appointment, String> contact_col;
    
    /**
     * Column of the allTable that displays the Type of a appointment.
     */
    public TableColumn<Appointment, String> type_col;
    
    /**
     * Column of the allTable that displays the StartTime of a appointment in user's LocalTime format.
     */
    public TableColumn<Appointment, String> start_col;

    /**
     * Column of the allTable that displays the EndTime of a appointment in user's LocalTime format.
     */
    public TableColumn<Appointment, String> end_col;
    

    /**
     * Column of the allTable that displays the Date of a appointment.
     */
    public TableColumn<Appointment, String> date_Col;


    /**
     * TreeTableColumn of the ByWeek Table that displays the appointment Id of a appointment.
     */
    public TreeTableColumn<Appointment, Integer> appIDByMonth_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the customer Id of a appointment.
     */
    public TreeTableColumn<Appointment, Integer> customerIDByMonth_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Title of a appointment.
     */
    public TreeTableColumn<Appointment, String> titleByMonth_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Description of a appointment.
     */
    public TreeTableColumn<Appointment, String> descByMonth_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Location of a appointment.
     */
    public TreeTableColumn<Appointment, String> locationByMonth_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Contact of a appointment.
     */
    public TreeTableColumn<Appointment, String> contactByMonth_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Type of a appointment.
     */
    public TreeTableColumn<Appointment, String> typeByMonth_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Date of a appointment.
     */
    public TreeTableColumn<Appointment, String> dateByMonth_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the StartTime of a appointment.
     */
    public TreeTableColumn<Appointment, String> startTimeByMonth_treeCol;
    

    /**
     * TreeTableColumn of the ByWeek Table that displays the EndTime of a appointment.
     */
    public TreeTableColumn<Appointment, String> endTimeByMonth_treeCol;

    /**
     * Table view for displaying all the appointments.
     */
    public TableView<Appointment> allTableView;

    /**
     * TreeTable view for displaying all the appointments only by month.
     */
    public TreeTableView<Appointment> byMonthTableView;

    /**
     * TreeTable view for displaying all the appointments only by week.
     */
    public TreeTableView<Appointment> byWeekTableView;

    /**
     * TreeTableColumn of the ByWeek Table that displays the Appointment of a appointment.
     */
    public TreeTableColumn<Appointment, Integer> appIDByWeek_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Customer Id of a appointment.
     */
    public TreeTableColumn<Appointment, Integer> customerIDByWeek_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Title of a appointment.
     */
    public TreeTableColumn<Appointment, String> titleByWeek_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Description of a appointment.
     */
    public TreeTableColumn<Appointment, String> descByWeek_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Location of a appointment.
     */
    public TreeTableColumn<Appointment, String> locationByWeek_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Contact of a appointment.
     */
    public TreeTableColumn<Appointment, String> contactByWeek_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the Type of a appointment.
     */
    public TreeTableColumn<Appointment, String> typeByWeek_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the date of a appointment.
     */
    public TreeTableColumn<Appointment, String> dateByWeek_treeCol;
    
    /**
     * TreeTableColumn of the ByWeek Table that displays the startTime of a appointment.
     */
    public TreeTableColumn<Appointment, String> startTimeByWeek_treeCol;

    /**
     * TreeTableColumn of the ByWeek Table that displays the endTime of a appointment.
     */
    public TreeTableColumn<Appointment, String> endTimeByWeek_treeCol;

    /**
     * label for to display custom message that there is no appointment within 15 minutes.
     */
    public Label bottomLabel;


    @FXML private Label mainLabel;


    /**
     * The current successful logged in user. 
     */
    public static User currentLoggedInUser;

    public void showBottomLabel() {
        if (AppointmentRepository.getNotFound().equals("notFound")) {
            bottomLabel.setText("There is no upcoming appointment.");
        } else {
            bottomLabel.setVisible(false);
        }
    }

    enum ScreenState {
            MAIN, ADD, MODIFY
    };

    enum TabSelected {
        ALL, WEEK, MONTH
    };

    /**
     * Tracks the various type of the screen selected. 
     */
    public static ScreenState currentScreenSelection;

    /**
     * Tracks the current tab selected. 
     */
    public static TabSelected currentTabSelected;

    /**
     * The appointment list 
     */
    public static ObservableList<Appointment> appointmentList;

    /**
     * Initializes all the tables' columns and populate the table data from the appointmentRepository.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        long startTime = System.currentTimeMillis();
        currentScreenSelection = ScreenState.MAIN;
        currentTabSelected = TabSelected.ALL;

        ID_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerID_col.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        location_col.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact_col.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        start_col.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        end_col.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        date_Col.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        appointmentList = AppointmentRepository.selectAll();

        allTableView.setItems(appointmentList);

        byMonthTableView.setRoot(generateMonthlyData());
        byMonthTableView.setShowRoot(false);


        byWeekTableView.setRoot(generateWeeklyData());
        byWeekTableView.setShowRoot(false);

        appIDByMonth_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        customerIDByMonth_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerId"));
        titleByMonth_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        descByMonth_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        locationByMonth_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("location"));
        contactByMonth_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("Contact"));
        typeByMonth_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
        startTimeByMonth_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("startTime"));
        endTimeByMonth_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("endTime"));
        dateByMonth_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateTime"));

        appIDByWeek_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        customerIDByWeek_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerId"));
        titleByWeek_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        descByWeek_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        locationByWeek_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("location"));
        contactByWeek_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("Contact"));
        typeByWeek_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
        startTimeByWeek_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("startTime"));
        endTimeByWeek_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("endTime"));
        dateByWeek_treeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateTime"));

        byMonthTableView.expandedItemCountProperty().addListener(e -> {
            byMonthTableView.refresh();
        });

        byWeekTableView.expandedItemCountProperty().addListener(e -> {
            byWeekTableView.refresh();
        });

        if (currentLoggedInUser != null ) {
            setCurrentUser(currentLoggedInUser);
        }

        byWeekTableView.setPlaceholder(new Label("There is no appointment scheduled on this week."));
        byMonthTableView.setPlaceholder(new Label("There is no appointment scheduled on this month."));


        this.showBottomLabel();
        //System.out.println("It took " + (System.currentTimeMillis() - startTime)/1000.0 + " seconds to initialize this screen!");
    }

    /**
     * <p><b>Lambda Expression Discussion</b></p>
     *      I have used the lambda expression here as to filter the appointment list and obtain the results
     *      whose's startDateTime are within this week by creating a predicate. Afterwards, I have streamed the
     *      data into the treeItem using forEach approach.
     *
     *
     * generates the view by week data to display onto the table.
     *
     * @return the filtered list of the appointmentList that only contains the appointment times that are within this current week
     */
    public TreeItem<Appointment> generateWeeklyData() {
        TreeItem<Appointment> mainRoot = new TreeItem<>();

        LocalDateTime startWeek = LocalDateTime.now();
        LocalDateTime nextWeek = startWeek.plusDays(7);

        FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
        filteredData.setPredicate(otherAppointment -> {

            LocalDateTime otherAppointmentStartDate = otherAppointment.getStartDateTime().toLocalDateTime();

            return otherAppointmentStartDate.isAfter(startWeek) &&
                    otherAppointmentStartDate.isBefore(nextWeek);
        });

        filteredData.stream()
                    .forEach( i -> mainRoot.getChildren().add(new TreeItem<>(i)));


        return mainRoot;
    }

    /**
     * Generates the view by Month data to display onto the table.
     *
     * <p><b>Lambda Expression Discussion</b></p>
     *      I have used the lambda expression here as to filter the appointment list and obtain the results
     *      which startDateTime matches the current month and year by creating a predicate. Afterwards, I have streamed the
     *      data into the treeItem using forEach approach.
     *
     * @return the filtered list of the appointmentList that only contains the appointment times that are within this current month
     */
    public TreeItem<Appointment> generateMonthlyData() {
        TreeItem<Appointment> mainRoot = new TreeItem<>();

        LocalDateTime currentDate = LocalDateTime.now();
        Month currentMonth = currentDate.getMonth();
        int currentYr = currentDate.getYear();

        FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
        filteredData.setPredicate(otherAppointment -> {

            LocalDateTime otherAppointmentStartDate = otherAppointment.getStartDateTime()
                                                                       .toLocalDateTime();

            return otherAppointmentStartDate.getMonth() == currentMonth &&
                    otherAppointmentStartDate.getYear() == currentYr;
        });


        if (filteredData.size() == 0) {

        } else {

        }
        filteredData.stream()
                .forEach( i -> mainRoot.getChildren().add(new TreeItem<>(i)));



        return mainRoot;
    }

    /**
     * Takes the user to the add New appointment screen.
     *
     * @param actionEvent
     */
    public void addAppointmentClickHandler(ActionEvent actionEvent) {
        currentScreenSelection = ScreenState.ADD;
        toSecondScreen(1);
    }

    /**
     * Modify the appointment data selected from the table and takes the user to the appointmentEditScreen.
     *
     * @param actionEvent
     */
    public void modifyAppointmentClickHandler(ActionEvent actionEvent) {
        Appointment selectedAppointment = null;
        switch (currentTabSelected) {
            case ALL:
                selectedAppointment = allTableView.getSelectionModel().getSelectedItem();
                break;
            case WEEK:
                selectedAppointment = byWeekTableView.getSelectionModel().getTreeTableView().getSelectionModel().getSelectedItem().getValue();
                break;
            case MONTH:
                selectedAppointment = byMonthTableView.getSelectionModel().getTreeTableView().getSelectionModel().getSelectedItem().getValue();
                break;
            default:
                break;
        }
        if (selectedAppointment == null) {
            return;
        }

        currentScreenSelection = ScreenState.MODIFY;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/appointmentEditScreen.fxml"));
            Parent root = loader.load();

            AppointmentEditScreenController appointmentEditScreenController = loader.getController();
            appointmentEditScreenController.setCurrentAppointmentToModify(selectedAppointment);

            Main.setScene(new Scene(root));
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * Deletes the appointment selected from one of tables.
     *
     * @param actionEvent
     */
    public void deleteAppointmentClickHandler(ActionEvent actionEvent) {
        Appointment selectedAppointment = null;

        // first we check in which tab user is selecting the appointment to delete:
        switch (currentTabSelected) {
            case ALL: selectedAppointment = allTableView.getSelectionModel().getSelectedItem();
                      break;
            case WEEK: selectedAppointment = byWeekTableView.getSelectionModel().getTreeTableView().getSelectionModel().getSelectedItem().getValue();
                       break;
            case MONTH: selectedAppointment = byMonthTableView.getSelectionModel().getTreeTableView().getSelectionModel().getSelectedItem().getValue();
                        break;
            default: break;
        }

        if (selectedAppointment != null) {
            if(AlertBoxHandler.displayConfirmationAlert("Do you wish to delete appointment#" + selectedAppointment.getId() +
                    "\nand Type of \"" + selectedAppointment.getType() + "\" ?")) {
                AppointmentRepository.delete(selectedAppointment.getId());
                AppointmentRepository.refresh();
                appointmentList = AppointmentRepository.selectAll();

                allTableView.setItems(null);
                byWeekTableView.setRoot(null);
                byMonthTableView.setRoot(null);

                allTableView.refresh();
                byMonthTableView.refresh();
                byWeekTableView.refresh();

                allTableView.setItems(appointmentList);
                byMonthTableView.setRoot(generateMonthlyData());
                byMonthTableView.setShowRoot(false);

                byWeekTableView.setRoot(generateWeeklyData());
                byWeekTableView.setShowRoot(false);

                allTableView.refresh();
                byMonthTableView.refresh();
                byWeekTableView.refresh();
            }
        }
    }



    /**
     * Switches the current stage to the report screen.
     */
    public void checkReportClickHandler(ActionEvent actionEvent) {
        toSecondScreen(3);
    }

    /**
     * Exits the application.
     */
    public void exitClickHandler(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Sets the current tab selected state to ALL.
     *
     * @param event Event
     */
    public void allTabClickHandler(Event event) {
        currentTabSelected = TabSelected.ALL;
    }

    /**
     * Sets the current tab selected state to MONTH.
     *
     * @param event Event
     */
    public void monthTabClickHandler(Event event) {
        if (byMonthTableView.getRoot().getChildren().size() == 0) {
            AlertBoxHandler.displayAlert(Alert.AlertType.INFORMATION, "There is no appointment scheduled in this month.");
        }

        currentTabSelected = TabSelected.MONTH;
    }

    /**
     * Sets the current tab selected state to WEEK.
     *
     * @param event Event
     */
    public void weekTabClickHandler(Event event) {
        if (byMonthTableView.getRoot().getChildren().size() == 0) {
            AlertBoxHandler.displayAlert(Alert.AlertType.INFORMATION, "There is no appointment scheduled within this week.");
        }

        currentTabSelected = TabSelected.WEEK;
    }

    /**
     * This function serves to switch current screen to another.
     *
     * @param screenType Type of screen to switch.
     */
    public void toSecondScreen(int screenType) {
        String filePath = "";

        switch (screenType) {
            case 1: filePath = "appointmentEditScreen";
                break;
            case 2: filePath = "customerScreen";
                break;
            case 3: filePath = "reportScreen";
                break;
            default: break;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/" + filePath + ".fxml"));
            Parent root = loader.load();

            if (filePath.equals("customerScreen")) {
                CustomerScreenController customerScreenController = loader.getController();
            } else if (filePath.equals("appointmentScreen")){
                AppointmentEditScreenController appointmentEditScreenController = loader.getController();
            }

            Main.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
            //AlertBoxHandler.displayWarningDialogue(filePath + ".fxml");
            currentScreenSelection = ScreenState.MAIN;
        }
    }

    public void viewCustomerListClickHandler(ActionEvent actionEvent) {
        toSecondScreen(2);
    }

    /**
     * Sets the current successful logged-In user and display it to the screen.
     *
     * @param user
     */
    public void setCurrentUser(User user) {
        currentLoggedInUser = user;
        mainLabel.setText(mainLabel.getText() + user.getUserName());
    }

}
