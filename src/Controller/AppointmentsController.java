package Controller;


import DAO_DBAccess.AppointmentsDAO;
import DAO_DBAccess.ContactsDAO;
import DAO_DBAccess.CustomersDAO;
import DAO_DBAccess.UsersDAO;
import Model.Appointments;
import Model.Customers;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * The Appointments FXML Controller class.
 */
public class AppointmentsController implements Initializable {



    @FXML private TableView<Appointments> appointmentsTable;
    @FXML private TableColumn<Appointments, Integer> idCol;
    @FXML private TableColumn<Appointments, String> titleCol;
    @FXML private TableColumn<Appointments, String> descCol;
    @FXML private TableColumn<Appointments, String> locCol;
    @FXML private TableColumn<Appointments, String> typeCol;
    @FXML private TableColumn<Appointments, LocalDateTime> startCol;
    @FXML private TableColumn<Appointments, LocalDateTime> endCol;
    @FXML private TableColumn<Appointments, Integer> customerIdCol;
    @FXML private TableColumn<Appointments, Integer> userIdCol;
    @FXML private TableColumn<Appointments, String> contactCol;
    @FXML private ToggleGroup appointmentsToggleGroup;
    @FXML private RadioButton allRadioButton;
    @FXML private RadioButton monthRadioButton;
    @FXML private RadioButton weekRadioButton;
    @FXML private TextField apptSearch;


    /**
     * The appointment selected to modify.
     */
    private static Appointments selectedAppointment;

    public static Appointments getSelectedAppointment() {
        return selectedAppointment;
    }


    /**
     * Sets table to view all appointments.
     *
     * @param actionEvent All radio button action.
     */
    @FXML void allRbClicked(ActionEvent actionEvent) {
        appointmentsTable.setItems(AppointmentsDAO.getAllAppointments());
    }

    /**
     * Sets table to view appointments for that month.
     *
     * @param actionEvent Month radio button action.
     */
    @FXML void monthRbClicked(ActionEvent actionEvent) {
        appointmentsTable.setItems(AppointmentsDAO.getMonthlyAppointments());
    }

    /**
     * Sets table to view appointments for that week.
     *
     * @param actionEvent Week radio button action.
     */
    @FXML void weekRbClicked(ActionEvent actionEvent) {
        appointmentsTable.setItems(AppointmentsDAO.getWeeklyAppointments());
    }

    /**
     * Loads AddAppointmentController.
     *
     * @param actionEvent Add new appointment button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML void newAppointmentClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads EditAppointmentController.
     *
     * @param actionEvent Edit appointment button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML void editAppointmentClicked(ActionEvent actionEvent) throws IOException{
        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select an appointment to Edit.");
            alert.showAndWait();

        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("/View/EditAppointment.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Deletes selected appointment from table.
     *
     * @param actionEvent delete appointment button action.
     */
    @FXML void deleteAppointmentClicked(ActionEvent actionEvent) {
        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if(selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select an appointment to Delete.");
            alert.showAndWait();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure you want to delete this Appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    AppointmentsDAO.deleteAppointment(selectedAppointment.getAppointmentID());

                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Appointment deleted");
                alert1.setHeaderText("Your appointment was successfully deleted.");
                alert1.showAndWait();

                if (allRadioButton.isSelected()) {
                    try {
                        appointmentsTable.setItems(AppointmentsDAO.getAllAppointments());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Exits the program.
     *
     * @param actionEvent Exit button action.
     */
    @FXML void exitButtonClicked(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Loads the CustomerController.
     *
     * @param actionEvent Customers button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML void customerButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Customers.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the ReportsController.
     *
     * @param actionEvent Reports button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML void reportsButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML void apptSearchClicked(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointments> daoAppointmentsList = AppointmentsDAO.getAllAppointments(); // All appointments list
        ObservableList<Appointments> searchedAppointments = FXCollections.observableArrayList();
        String searchString = apptSearch.getText().toLowerCase();
        for (Appointments a : daoAppointmentsList) {
            if (a.getTitle().toLowerCase().contains(searchString) || a.getDescription().toLowerCase().contains(searchString) || a.getLocation().contains(searchString) ||
                    a.getType().contains(searchString)) {
                searchedAppointments.add(a);
            }
        }
        appointmentsTable.setItems(searchedAppointments);
    }

    /*
    @FXML void apptSearchClicked(ActionEvent actionEvent) {
        String searchString = apptSearch.getText();
        if (searchString.matches("\\d+")) {
            int id = Integer.parseInt(searchString);
            Appointments searchAppts = AppointmentsDAO.lookupApptID(id);
            appointmentsTable.getSelectionModel().select(searchAppts);
        }
        else {
            appointmentsTable.setItems(AppointmentsDAO.getAllAppointments());
        }
    }

     */


    /**
     * Initializes the controller.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentsTable.setItems(AppointmentsDAO.getAllAppointments());
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        allRadioButton.setSelected(true);

    }
}
