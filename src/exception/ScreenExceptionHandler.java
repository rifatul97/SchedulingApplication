package exception;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * This class objective is to handle the various common error message to print
 * on the specific label from a screen that are passed in a parameter.
 *
 * <p><b>FUTURE ENHANCEMENT</b> I will like to add small enchantment for new transform method
 *                      that will style the error textfield into red.</p>
 *
 * @author Rifatul Karim
 * @version 0.95
 */
public final class ScreenExceptionHandler {

    /**
     * This method is used to show common error of the application
     *
     * @param type check for the type of error
     * @param errorLabel Label to print the message.
     */
    public static void showErrorMsg(int type, Label errorLabel) {

        switch (type) {
            case 1: errorLabel.setText("The username and password is not registered");
                break;
            case 2: errorLabel.setText("Please enter the right password!");
                break;
            case 3: errorLabel.setText("This value cannot be negative.");
                break;
            case 4: errorLabel.setText("This field cannot be empty");
                break;
            default: break;
        }
    }

    /** Generates a fadescreen transition effect to a label. */
    @Deprecated
    public static void transformLabel (Label label) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(4.0), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }

}
