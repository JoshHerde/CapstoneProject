package DAO_DBAccess;

import Model.Appointments;
import Model.TypeMonthReport;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DAO class that accesses the database Appointments table.
 */
public class AppointmentsDAO {

    /**
     * Gets all appointments from database.
     *
     * @return all appointments.
     */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> allAppointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments AS a INNER JOIN contacts AS c ON a.Contact_ID = c.Contact_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments newAppointment = new Appointments(appointmentID, title, description, location, type,
                                                                startTime, endTime, customerID, userID, contactID);
                allAppointmentList.add(newAppointment);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allAppointmentList;

    }

    /**
     * Gets appointments from database for a set month.
     *
     * @return monthly appointments.
     */
    public static ObservableList<Appointments> getMonthlyAppointments() {
        ObservableList<Appointments> monthlyAppointments = FXCollections.observableArrayList();

        LocalDate now = LocalDateTime.now().toLocalDate();
        LocalDate oneMonth = now.plusDays(30);

        try {
            String sql = "SELECT * FROM appointments WHERE Start < ? AND Start > ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setDate(1, Date.valueOf(oneMonth));
            ps.setDate(2, Date.valueOf(now));
            ps.executeQuery();

            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments newAppointment = new Appointments(appointmentID, title, description, location, type,
                        startTime, endTime, customerID, userID, contactID);
                monthlyAppointments.add(newAppointment);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyAppointments;
    }

    /**
     * Gets appointments from database for a set week.
     *
     * @return weekly appointments.
     */
    public static ObservableList<Appointments> getWeeklyAppointments() {
        ObservableList<Appointments> weeklyAppointmentList = FXCollections.observableArrayList();

        LocalDate now = LocalDateTime.now().toLocalDate();
        LocalDate oneWeek = now.plusDays(7);

        try {
            String sql = "SELECT * FROM appointments WHERE Start < ? AND Start > ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setDate(1, Date.valueOf(oneWeek));
            ps.setDate(2, Date.valueOf(now));
            ps.executeQuery();

            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments newAppointment = new Appointments(appointmentID, title, description, location, type,
                        startTime, endTime, customerID, userID, contactID);
                weeklyAppointmentList.add(newAppointment);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return weeklyAppointmentList;
    }


    /**
     * Adds an appointment to the database.
     *
     * @param appointments the appointment to be added.
     */
    public static void addAppointment(Appointments appointments) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, appointments.getTitle());
            ps.setString(2,appointments.getDescription());
            ps.setString(3, appointments.getLocation());
            ps.setString(4, appointments.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointments.getStartTime()));
            ps.setTimestamp(6,Timestamp.valueOf(appointments.getEndTime()));
            ps.setInt(7, appointments.getCustomerID());
            ps.setInt(8, appointments.getUserID());
            ps.setInt(9, appointments.getContactID());
            System.out.println(ps.toString());
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Edits an existing appointment in the database.
     *
     * @param appointments the appointment to be edited.
     */
    public static void editAppointment(Appointments appointments) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?," +
                    " Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, appointments.getTitle());
            ps.setString(2,appointments.getDescription());
            ps.setString(3, appointments.getLocation());
            ps.setString(4, appointments.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointments.getStartTime()));
            ps.setTimestamp(6,Timestamp.valueOf(appointments.getEndTime()));
            ps.setInt(7, appointments.getCustomerID());
            ps.setInt(8, appointments.getUserID());
            ps.setInt(9, appointments.getContactID());
            ps.setInt(10, appointments.getAppointmentID());
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an existing appointment from the database.
     *
     * @param dbAppointmentID the appointments ID.
     */
    public static void deleteAppointment(int dbAppointmentID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, dbAppointmentID);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets all appointments that have a specific contact ID.
     *
     * @param contactId the contact ID.
     * @return a specific appointment with matching contactID.
     * @throws SQLException from DBConnection.
     */
    public static ObservableList<Appointments> getAllByContact(int contactId) throws SQLException {
        ObservableList<Appointments> allByContact = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments WHERE Contact_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments newAppointment = new Appointments(appointmentID, title, description, location, type,
                        startTime, endTime, customerID, userID, contactID);
                allByContact.add(newAppointment);
            }
            return allByContact;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all appointments with specific customerID.
     *
     * @param customerId the customer ID.
     * @return a specific appointment with matching customer ID.
     */
    public static ObservableList<Appointments> getAllByCustomer(int customerId) {
        ObservableList<Appointments> allByCustomer = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments newAppointment = new Appointments(appointmentID, title, description, location, type,
                        startTime, endTime, customerID, userID, contactID);
                allByCustomer.add(newAppointment);
            }
            return allByCustomer;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets appointments by Type and month.
     *
     * @return all appointments by type and month.
     */
    public static ObservableList<TypeMonthReport> getByTypeMonth() {
        ObservableList<TypeMonthReport> allAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Type, monthname(start), COUNT(*) FROM appointments GROUP BY Type, monthname(start) ORDER BY Type;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String apptType = rs.getString("Type");
                String month = rs.getString("monthname(start)");
                int total = rs.getInt("COUNT(*)");

                TypeMonthReport typeMonthReport = new TypeMonthReport(apptType, month, total);
                allAppointments.add(typeMonthReport);
            }
            return allAppointments;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
