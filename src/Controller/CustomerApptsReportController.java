package Controller;

import DAO_DBAccess.AppointmentsDAO;
import DAO_DBAccess.CustomersDAO;
import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The CustomerApptsReport FXML Controller class.
 */
public class CustomerApptsReportController implements Initializable {


    @FXML private TableView<Appointments> customerTable;
    @FXML private TableColumn<Appointments, Integer> IdColumn;
    @FXML private TableColumn<Appointments, String> titleColumn;
    @FXML private TableColumn<Appointments, String> typeColumn;
    @FXML private TableColumn<Appointments, String> descriptionColumn;
    @FXML private TableColumn<Appointments, String> startColumn;
    @FXML private TableColumn<Appointments, String> endColumn;
    @FXML private TableColumn<Appointments, Integer> customerIdColumn;
    @FXML private ComboBox<Customers> customerComboBox;



    /**
     * Back button action.
     *
     * @param actionEvent Back button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Contact combo box action.
     *
     * @param actionEvent combo box action.
     * @throws SQLException from DAO class.
     */
    @FXML void customerComboClicked(ActionEvent actionEvent) throws SQLException {

        Customers selectedCustomer = (Customers) customerComboBox.getSelectionModel().getSelectedItem();
        ObservableList<Appointments> customerAppointments = AppointmentsDAO.getAllByCustomer(selectedCustomer.getCustomerID());

        IdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        if (customerAppointments.isEmpty()) {
            customerTable.setItems(null);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setHeaderText("This Customer doesn't have any appointments.");
            alert.showAndWait();
        }
        else {
            customerTable.setItems(customerAppointments);
        }
    }

    /**
     * Initializes the controller.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerComboBox.setItems(CustomersDAO.getAllCustomers());

    }
}
