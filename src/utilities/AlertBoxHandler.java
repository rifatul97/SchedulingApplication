package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * AlertBoxHandler class use to display various alert box with custom dialogue
 */
public abstract class AlertBoxHandler {

    /**
     * This class objective is to display confirmation message
     *
     * @return true indicates that the "OK"/"Yes" button was pressed
     */
    public static boolean displayConfirmationAlert(String dialogue) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(dialogue);

        return alert
                .showAndWait()
                .orElse(null) == ButtonType.OK;
    }

    /**
     * Displays alert with given alertType and dialogue.
     *
     * @param alertType type of alert like ERROR, INFORMATION etc
     * @param Dialogue message that will be display on the box's content
     */
    public static void displayAlert(Alert.AlertType alertType, String Dialogue) {
        Alert alert = new Alert(alertType);
        alert.setContentText(Dialogue);
        alert.showAndWait();
    }


}
