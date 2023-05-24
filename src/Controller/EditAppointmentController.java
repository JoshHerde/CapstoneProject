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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * The EditAppointment FXML Controller class.
 */
public class EditAppointmentController implements Initializable {

    @FXML private TextField apptIdTextField;
    @FXML private  TextField apptTitleTextField;
    @FXML private  TextField apptDescTextField;
    @FXML private  TextField apptLocTextField;
    @FXML private  ComboBox<Contacts> contactComboBox;
    @FXML private  TextField apptTypeTextField;
    @FXML private  DatePicker startDatePicker;
    @FXML private ComboBox<LocalTime> startTimeComboBox;
    @FXML private  DatePicker endDatePicker;
    @FXML private ComboBox<LocalTime> endTimeComboBox;
    @FXML private ComboBox<Customers> customerIDComboBox;
    @FXML private ComboBox<Users> userIDComboBox;

    /**
     * Gets all the selected objects.
     */
    public Contacts selectedContact;
    public Customers selectedCustomer;
    public Users selectedUser;
    public Appointments selectedAppointment;

    /**
     * Lists for combo boxes.
     */
    private ObservableList<Contacts> contactList = FXCollections.observableArrayList();
    private ObservableList<Customers> customersList = FXCollections.observableArrayList();
    private ObservableList<Users> usersList = FXCollections.observableArrayList();

    /**
     * Cancel button action.
     *
     * @param actionEvent Cancel button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML void cancelButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Saves the info provided on the edit appointment form.
     *
     * @param actionEvent Save button action.
     */
    @FXML void saveButtonClicked(ActionEvent actionEvent) {

        try {
            int id = selectedAppointment.getAppointmentID();
            String title = apptTitleTextField.getText();
            String description = apptDescTextField.getText();
            String location = apptLocTextField.getText();
            String type = apptTypeTextField.getText();

            LocalDateTime startTime = getStartInfo();
            LocalDateTime endTime = getEndInfo();

            if (apptTitleTextField.getText().isBlank() || apptDescTextField.getText().isBlank() || apptLocTextField.getText().isBlank() || apptTypeTextField.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Text Field");
                alert.setHeaderText("Please make sure all text fields are filled out");
                alert.showAndWait();
                return;
            }

            int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();
            int customerID = customerIDComboBox.getSelectionModel().getSelectedItem().getCustomerID();
            int userID = userIDComboBox.getSelectionModel().getSelectedItem().getUserID();

            LocalTime openHours = LocalTime.of(8, 0);
            LocalTime closedHours = LocalTime.of(22, 0);

            if (startTime.toLocalTime().isBefore(openHours) || endTime.toLocalTime().isAfter(closedHours)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Scheduling Time Error");
                alert.setHeaderText("The time you have chosen for your appointment isn't within business hours of 08:00 EST and 10:00 EST.");
                alert.showAndWait();
                return;
            }

            Appointments newAppointment = new Appointments(id, title, description, location, type, startTime, endTime, customerID, userID, contactID);

            if (ValidAppointment.Overlapping(newAppointment)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The appointment overlaps with other existing appointments, please choose another time.");
                alert.showAndWait();
            }

            if (!ValidAppointment.startAfterEnd(newAppointment) && !ValidAppointment.orgHours(newAppointment) && !ValidAppointment.Overlapping(newAppointment)) {

                AppointmentsDAO.editAppointment(newAppointment);

                Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                System.out.println("root = " + root + " stage = " + stage + " scene = " + scene);
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
        return LocalDateTime.of(startDate, startTime);
    }

    /**
     * Creates object for end date and time.
     *
     * @return end date and time.
     */
    private LocalDateTime getEndInfo() {
        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = endTimeComboBox.getValue();
        return  LocalDateTime.of(endDate, endTime);
    }


    /**
     * Initializes the controller.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedAppointment = AppointmentsController.getSelectedAppointment();

        apptIdTextField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        apptTitleTextField.setText(selectedAppointment.getTitle());
        apptDescTextField.setText(selectedAppointment.getDescription());
        apptLocTextField.setText(selectedAppointment.getLocation());
        apptTypeTextField.setText(selectedAppointment.getType());

        startDatePicker.setValue(selectedAppointment.getStartTime().toLocalDate());
        endDatePicker.setValue(selectedAppointment.getEndTime().toLocalDate());

        startTimeComboBox.setItems(TimeHelper.getStartTimes());
        endTimeComboBox.setItems(TimeHelper.getEndTimes());
        startTimeComboBox.setValue(selectedAppointment.getStartTime().toLocalTime());
        endTimeComboBox.setValue(selectedAppointment.getEndTime().toLocalTime());

        try {
            contactList = ContactsDAO.getAllContacts();
            contactComboBox.setItems(contactList);
            selectedContact = ContactsDAO.getContactNameByID(selectedAppointment.getContactID());
            contactComboBox.setValue(selectedContact);

            customersList = CustomersDAO.getAllCustomers();
            customerIDComboBox.setItems(customersList);
            selectedCustomer = CustomersDAO.getCustomerByID(selectedAppointment.getCustomerID());
            customerIDComboBox.setValue(selectedCustomer);

            usersList = UsersDAO.getAllUsers();
            userIDComboBox.setItems(usersList);
            selectedUser = UsersDAO.getUserByID(selectedAppointment.getUserID());
            userIDComboBox.setValue(selectedUser);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
