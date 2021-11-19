package main;

import repository.RepositoryFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import utilities.DBConnection;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Scheduling Application.
 *
 *  <p>javadocs are located in src\javadocs\index</p>
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class Main extends Application {

    static Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        //long startTime = System.currentTimeMillis();
        mainStage = primaryStage;
        Locale locale = Locale.getDefault();

        ResourceBundle rb = ResourceBundle.getBundle("resources/lang", Locale.getDefault());

        DBConnection.startConnection();
        RepositoryFactory.loadDBData();

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/views/loginScreen.fxml"), rb);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Scheduling Application");
            primaryStage.setScene(scene);
            primaryStage.show();

        //    System.out.println("It took " + (System.currentTimeMillis() - startTime)/1000.0 + " seconds");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setScene(Scene scene) {
        mainStage.setX(0.0);
        mainStage.setY(0.0);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static Stage getStage() {
        return mainStage;
    }



}
