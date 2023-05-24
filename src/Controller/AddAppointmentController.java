package Controller;

import DAO_DBAccess.AppointmentsDAO;
import DAO_DBAccess.ContactsDAO;
import DAO_DBAccess.CustomersDAO;
import DAO_DBAccess.UsersDAO;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
import Utilities.TimeHelper;
import Utilities.ValidAppointment;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * The AddAppointment FXML Controller class.
 */
public class AddAppointmentController implements Initializable {

    @FXML private TextField apptTitleTextField;
    @FXML private TextField apptLocTextField;
    @FXML private TextField apptDescTextField;
    @FXML private TextField apptTypeTextField;
    @FXML private DatePicker startDatePicker;
    @FXML private ComboBox<LocalTime> startTimeComboBox;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<LocalTime> endTimeComboBox;
    @FXML private ComboBox<Contacts> contactComboBox;
    @FXML private ComboBox<Customers> customerIDComboBox;
    @FXML private ComboBox<Users> userIDComboBox;


    private ObservableList<Contacts> contactList = FXCollections.observableArrayList();
    private ObservableList<Customers> customersList = FXCollections.observableArrayList();
    private ObservableList<Users> usersList = FXCollections.observableArrayList();


    /**
     * Saves the info provided on the add appointment form.
     *
     * @param actionEvent Save button action.
     */
    @FXML void saveButtonClicked(ActionEvent actionEvent) {

        try {
            String title = apptTitleTextField.getText();
            String description = apptDescTextField.getText();
            String location = apptLocTextField.getText();
            String type = apptTypeTextField.getText();

            if (apptTitleTextField.getText().isBlank() || apptDescTextField.getText().isBlank() || apptLocTextField.getText().isBlank() || apptTypeTextField.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Text Field");
                alert.setHeaderText("Please make sure all text fields are filled out");
                alert.showAndWait();
                return;
            }

            if (startDatePicker.getValue() == null || startTimeComboBox.getValue() == null || endDatePicker.getValue() == null || endTimeComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Date and/or time slots are empty.");
                alert.setHeaderText("Please fill in all Date and Time slots.");
                alert.showAndWait();
                return;
            }

            LocalDateTime startTime = getStartInfo();
            LocalDateTime endTime = getEndInfo();

            Contacts contacts = contactComboBox.getValue();
            Customers customers = customerIDComboBox.getValue();
            Users users = userIDComboBox.getValue();

            if (contacts == null || customers == null || users == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Contact, Customer or User combo box");
                alert.setHeaderText("Please make sure all combo boxes have a selection");
                alert.showAndWait();
                return;
            }

            int contactID = contacts.getContactID();
            int customerID = customers.getCustomerID();
            int userID = users.getUserID();

            LocalTime openHours = LocalTime.of(8, 0);
            LocalTime closedHours = LocalTime.of(22, 0);

            Appointments newAppointment = new Appointments(title, description, location, type, startTime, endTime, customerID, userID, contactID);

            if (ValidAppointment.Overlapping(newAppointment)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The appointment overlaps with other existing appointments, please choose another time.");
                alert.showAndWait();
            }

            if (!ValidAppointment.startAfterEnd(newAppointment) && !ValidAppointment.orgHours(newAppointment) && !ValidAppointment.Overlapping(newAppointment)) {
                AppointmentsDAO.addAppointment(newAppointment);

                Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates object for start date and time.
     *
     * @return start date and time.
     */
    private LocalDateTime getStartInfo() {
        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = startTimeComboBox.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        return startDateTime;
    }

    /**
     * Creates object for end date and time.
     *
     * @return end date and time.
     */
    private LocalDateTime getEndInfo() {
        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = endTimeComboBox.getValue();
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        return endDateTime;
    }


    /**
     * Cancel button action.
     *
     * @param actionEvent Cancel button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML void cancelButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
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

        startTimeComboBox.setItems(TimeHelper.getStartTimes());
        endTimeComboBox.setItems(TimeHelper.getEndTimes());

        try {
            contactList = ContactsDAO.getAllContacts();
            contactComboBox.setItems(contactList);

            customersList = CustomersDAO.getAllCustomers();
            customerIDComboBox.setItems(customersList);

            usersList = UsersDAO.getAllUsers();
            userIDComboBox.setItems(usersList);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
