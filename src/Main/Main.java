package Main;

import Utilities.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This class creates a GUI based scheduling application where the user
 * can manage appointments and customers.
 */
public class Main extends Application {


    /**
     * The entry of the application. Opens and closes the database connection.
     *
     * @param args Argument List.
     */
    public static void main(String[] args) {

        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();

    }

    /**
     * The start method creates the FXML stage and loads the initial scene.
     *
     * @param primaryStage Primary stage of application.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Login.fxml")));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}








