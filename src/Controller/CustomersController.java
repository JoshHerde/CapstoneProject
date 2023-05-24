package Controller;

import DAO_DBAccess.AppointmentsDAO;
import DAO_DBAccess.CustomersDAO;
import Model.Appointments;
import Model.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The Customers FXML Controller class.
 */
public class CustomersController implements Initializable {

    @FXML private TableView<Customers> customersTable;
    @FXML private TableColumn<Customers, Integer> customerIDCol;
    @FXML private TableColumn<Customers, String> customerNameCol;
    @FXML private TableColumn<Customers, String> customerAddressCol;
    @FXML private TableColumn<Customers, String> customerPostalCol;
    @FXML private TableColumn<Customers, String> customerPhoneCol;
    @FXML private TableColumn<Customers, String> customerCountryCol;
    @FXML private TableColumn<Customers, Integer> customerDivisionCol;

    /**
     * The customer selected to modify.
     */
    public static Customers selectedCustomer;

    public static Customers getSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     * Loads AddCustomerController.
     *
     * @param actionEvent Add new customer button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML  void newCustomerClicked(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads EditCustomerController.
     *
     * @param actionEvent Edit customer button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML  void editCustomerClicked(ActionEvent actionEvent) throws IOException {
        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a customer to Edit.");
            alert.showAndWait();
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("/View/EditCustomer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Deletes selected customer from table.
     *
     * @param actionEvent delete customer button action.
     */
    @FXML void deleteCustomerClicked(ActionEvent actionEvent) {
        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a customer to delete.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure you want to delete this Customer?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    //CustomersDAO.deleteCustomer(selectedCustomer.getCustomerID());
                    ObservableList<Appointments> apptList = AppointmentsDAO.getAllAppointments();
                    for (Appointments appointments : apptList) {
                        if (appointments.getCustomerID() == selectedCustomer.getCustomerID()) {
                            AppointmentsDAO.deleteAppointment(appointments.getAppointmentID());
                        }
                    }
                    CustomersDAO.deleteCustomer(selectedCustomer.getCustomerID());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Confirm");
                alert1.setHeaderText(selectedCustomer.getName() + " and all of their appointments have been removed from our database.");
                alert1.showAndWait();

                customersTable.setItems(CustomersDAO.getAllCustomers());
            }
        }


    }

    /**
     * Back button action.
     *
     * @param actionEvent Back button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Initializes the controller.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customersTable.setItems(CustomersDAO.getAllCustomers());
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("countryID"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));


    }


}
