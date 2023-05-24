package Controller;

import DAO_DBAccess.AppointmentsDAO;
import Model.TypeMonthReport;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The TypeMonthReport FXML Controller class.
 */
public class TypeMonthReportController  implements Initializable {

    @FXML private TableView<TypeMonthReport> typeMonthTable;
    @FXML private TableColumn<TypeMonthReport, String> typeCol;
    @FXML private TableColumn<TypeMonthReport, String> monthCol;
    @FXML private TableColumn<TypeMonthReport, Integer> totalCol;


    /**
     * Back button action.
     *
     * @param actionEvent Back button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the controller.
     * Lambda expression used to set values in the table.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<TypeMonthReport> typeMonthAppointments = AppointmentsDAO.getByTypeMonth();

        //Values set by using a Lambda expression to improve the proficiency of the code.
        typeCol.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getApptType()));
        monthCol.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getMonth()));
        totalCol.setCellValueFactory(column -> new SimpleObjectProperty<>(column.getValue().getTotal()));

        /*
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
         */

        if (typeMonthAppointments == null) {
            typeMonthTable.setItems(null);
        }
        else {
            typeMonthTable.setItems(typeMonthAppointments);
        }
    }
}
