package Controller;

import DAO_DBAccess.CountriesDAO;
import DAO_DBAccess.CustomersDAO;
import DAO_DBAccess.DivisionsDAO;
import Model.Countries;
import Model.Customers;
import Model.Divisions;
import Model.Users;
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
 * The EditCustomer FXML Controller class.
 */
public class EditCustomerController implements Initializable {

    @FXML private TextField customerIDTextField;
    @FXML private TextField nameTextField;
    @FXML private  TextField addressTextField;
    @FXML private  TextField postalCodeTextField;
    @FXML private  TextField phoneTextField;
    @FXML private  ComboBox<Countries> countryComboBox;
    @FXML private  ComboBox<Divisions> divisionComboBox;


    private ObservableList<Countries> countryList = FXCollections.observableArrayList();
    private Users currentUser = LoginController.getCurrentUser();
    private Customers selectedCustomer;

    /**
     * Saves the info provided on the edit customer form.
     *
     * @param actionEvent Save button action.
     */
    @FXML void saveButtonClicked(ActionEvent actionEvent) {
        try {
            String name = nameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phone = phoneTextField.getText();

            if (nameTextField.getText().isBlank() || addressTextField.getText().isBlank() || postalCodeTextField.getText().isBlank() || phoneTextField.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Text Fields");
                alert.setHeaderText("Please fill in all text fields.");
                alert.showAndWait();
                return;
            }

            int divisionID = divisionComboBox.getSelectionModel().getSelectedItem().getDivisionID();

            Customers newCustomer = new Customers(name, address, postalCode, phone, divisionID);
            newCustomer.setCustomerID(selectedCustomer.getCustomerID());


            CustomersDAO.editCustomer(newCustomer);

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
        Parent root = FXMLLoader.load(getClass().getResource("/View/Customers.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Country combo box action.
     * Lambda expression used to set values in country and division combo boxes.
     *
     * @param actionEvent combo box action.
     */
    @FXML void countryComboBoxClicked(ActionEvent actionEvent) {
        try {
            Countries selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Divisions> databaseDivisions = DivisionsDAO.getAllDivisions();
            ObservableList<Divisions> countryDivisions = FXCollections.observableArrayList();

            //for (Divisions divisions : databaseDivisions) {
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
        selectedCustomer = CustomersController.getSelectedCustomer();

        customerIDTextField.setText(String.valueOf(selectedCustomer.getCustomerID()));
        nameTextField.setText(String.valueOf(selectedCustomer.getName()));
        addressTextField.setText(selectedCustomer.getAddress());
        postalCodeTextField.setText(selectedCustomer.getPostalCode());
        phoneTextField.setText(selectedCustomer.getPhone());

        try {
            Divisions selectedDivision = DivisionsDAO.getByID(selectedCustomer.getDivisionID());
            Countries countries = CountriesDAO.getByID(selectedDivision.getCountryID());


            countryList = CountriesDAO.getAllCountries();
            countryComboBox.setItems(countryList);
            countryComboBox.setValue(countries);

            ObservableList<Divisions> databaseDivisions = DivisionsDAO.getAllDivisions();
            ObservableList<Divisions> countryDivisions = FXCollections.observableArrayList();

            for (Divisions divisions : databaseDivisions) {

            }

            divisionComboBox.setItems(countryDivisions);
            divisionComboBox.setValue(selectedDivision);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
