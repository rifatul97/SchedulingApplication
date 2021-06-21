import com.mysql.cj.protocol.Resultset;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/loginScreen.fxml"));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc-test", "root", "Pokemon1997");

            Statement statement = connection.createStatement();

            ResultSet resultset = statement.executeQuery("select * from user where id > 1");

            while (resultset.next()) {
                System.out.println(resultset.getString("username"));
            }
        } catch (Exception e) {
            System.out.println("Cannot do it!!");
        }

        launch(args);
    }
}
