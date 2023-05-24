package Utilities;

import DAO_DBAccess.AppointmentsDAO;
import Model.Appointments;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.time.*;

/**
 * Class that validates appointments.
 */
public class ValidAppointment {

    /**
     * Checks for empty text fields on add or edit appointment screen.
     *
     * @param appointments the appointment.
     * @return boolean true or false.
     */
    public static boolean missingFields(Appointments appointments) {
        boolean emptyFields = false;

        if (appointments.getTitle().equals("")) {
            emptyFields = true;
        }
        else if (appointments.getDescription().equals("")) {
            emptyFields = true;
        }
        else if (appointments.getLocation().equals("")) {
            emptyFields = true;
        }
        else if (appointments.getType().equals("")) {
            emptyFields = true;
        }

        if (emptyFields) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Make sure all text fields are filled out");
            alert.showAndWait();
        }
        return emptyFields;
    }

    /**
     * Checks for overlapping appointment times.
     *
     * @param newAppointment the new appointment.
     * @return boolean true or false.
     * @throws SQLException from DAO class.
     */
    public static boolean Overlapping(Appointments newAppointment) throws SQLException {
        ObservableList<Appointments> allAppointmentList = AppointmentsDAO.getAllAppointments();
        boolean overlapping = false;
        Appointments Overlapping = new Appointments();

        for (Appointments appointments : allAppointmentList) {
            if ( appointments.getCustomerID() != newAppointment.getCustomerID() || appointments.getAppointmentID() == newAppointment.getAppointmentID()) {
                continue;
            }

            //int newApptCustomerID = newAppointment.getCustomerID();
            LocalDateTime newApptStart = newAppointment.getStartTime();
            LocalDateTime newApptEnd = newAppointment.getEndTime();

            //int apptCustomerID = appointments.getCustomerID();
            LocalDateTime apptStart = appointments.getStartTime();
            LocalDateTime apptEnd = appointments.getEndTime();

            Overlapping = appointments;

            if (newApptStart.isAfter(apptStart) && newApptStart.isBefore(apptEnd)) {
                overlapping = true;
                break;

            } else if (newApptEnd.isAfter(apptStart) && newApptEnd.isBefore(apptEnd)) {
                overlapping = true;
                break;

            } else if (newApptStart.isBefore(apptStart) && newApptEnd.isAfter(apptEnd)) {
                overlapping = true;
                break;

            } else if (newApptStart.isEqual(apptStart) || newApptEnd.isEqual(apptEnd)) {
                overlapping = true;
                break;

            } else if (newApptStart.isAfter(apptStart) && newApptEnd.isBefore(apptEnd)) {
                overlapping = true;
                break;
            }
        }
        return overlapping;
    }

    /**
     * Checks if appointment start time is after the end time.
     *
     * @param appointments the appointment
     * @return boolean true or false.
     */
    public static boolean startAfterEnd(Appointments appointments) {
        boolean startAfterEnd = false;
        LocalDateTime startTime = appointments.getStartTime();
        LocalDateTime endTime = appointments.getEndTime();

        if (startTime.isAfter(endTime)) {
            startAfterEnd = true;
        }

        if (startAfterEnd) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Make sure start time is before end time.");
            alert.showAndWait();
        }
        return startAfterEnd;
    }

    /**
     * Checks if appointment is within the organizations hours.
     *
     * @param appointments the appointment.
     * @return boolean true or false.
     */
    public static boolean orgHours (Appointments appointments) {

        LocalDateTime startDateTime = appointments.getStartTime();
        LocalDate startDate = startDateTime.toLocalDate();
        ZonedDateTime startTimeZone = startDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime startTimeEST = startTimeZone.withZoneSameInstant(ZoneId.of("America/New_York"));

        LocalTime orgOpenTime = LocalTime.parse("08:00:00");
        LocalDateTime orgStartDateTime = LocalDateTime.of(startDate, orgOpenTime);
        ZonedDateTime orgStartTimeZone = orgStartDateTime.atZone(ZoneId.of("America/New_York"));

        LocalDateTime endDateTime = appointments.getEndTime();
        ZonedDateTime endTimeZone = endDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime endTimeEST = endTimeZone.withZoneSameInstant(ZoneId.of("America/New_York"));

        LocalTime orgCloseTime = LocalTime.parse("22:00:00");
        LocalDateTime orgEndDateTime = LocalDateTime.of(startDate, orgCloseTime);
        ZonedDateTime orgEndTimeZone = orgEndDateTime.atZone(ZoneId.of("America/New_York"));

        boolean orgClosed = startTimeEST.isBefore(orgStartTimeZone) || endTimeEST.isAfter(orgEndTimeZone);

        if (orgClosed) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("The appointment time you chose is outside of the organization's hours. \nHours are 8:00 - 22:00 EST.");
            alert.showAndWait();
        }
        return orgClosed;
    }

}
