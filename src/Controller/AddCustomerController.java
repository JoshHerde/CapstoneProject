package Controller;

import DAO_DBAccess.CountriesDAO;
import DAO_DBAccess.CustomersDAO;
import DAO_DBAccess.DivisionsDAO;
import Model.Countries;
import Model.Customers;
import Model.Divisions;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The AddCustomer FXML Controller class.
 */
public class AddCustomerController implements Initializable {

    @FXML private TextField nameTextField;
    @FXML private  TextField addressTextField;
    @FXML private  TextField postalCodeTextField;
    @FXML private  TextField phoneTextField;
    @FXML private  ComboBox<Countries> countryComboBox;
    @FXML private  ComboBox<Divisions> divisionComboBox;

    private ObservableList<Countries> countryList = FXCollections.observableArrayList();

    /**
     * Saves the info provided on the add customer form.
     *
     * @param actionEvent Save button action.
     */
    @FXML void saveButtonClicked(ActionEvent actionEvent) {
        try {
            String name = nameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phone = phoneTextField.getText();

            if (nameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() || postalCodeTextField.getText().isEmpty() || phoneTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Text Fields");
                alert.setHeaderText("Please fill in all text fields.");
                alert.showAndWait();
                return;
            }

            Divisions divisions = divisionComboBox.getValue();

            if (divisions == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Country or Division combo box");
                alert.setHeaderText("Please make sure all combo boxes have a selection");
                alert.showAndWait();
                return;
            }

            int divisionID = divisions.getDivisionID();

            Customers newCustomer = new Customers(name, address, postalCode, phone, divisionID);
            CustomersDAO.addCustomer(newCustomer);

            Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Cancel button action.
     *
     * @param actionEvent Cancel button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML void cancelButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Country combo box action.
     * Lambda expression used to set values in country and division combo boxes.
     *
     * @param actionEvent Combo box action.
     */
    public void countryComboBoxClicked(ActionEvent actionEvent) {
        try {
            Countries selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Divisions> databaseDivisions = DivisionsDAO.getAllDivisions();
            ObservableList<Divisions> countryDivisions = FXCollections.observableArrayList();

            //for (Divisions divisions : databaseDivisions) {
            // Used Lambda expression to set values in country and division combo box.
            databaseDivisions.forEach( divisions -> {
                if (divisions.getCountryID() == selectedCountry.getCountryID()) {
                    countryDivisions.add(divisions);
                }
            });
            divisionComboBox.setItems(countryDivisions);
        }
        catch (SQLException e) {
            e.printStackTrace();
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
        countryList = CountriesDAO.getAllCountries();
        countryComboBox.setItems(countryList);
    }
}
