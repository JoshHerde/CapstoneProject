package Model;

import java.time.LocalDateTime;

/**
 * Class used to make the Appointment object.
 */
public class Appointments {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Constructor for the Appointment object.
     *
     * @param appointmentID Appointment ID.
     * @param title Appointment title.
     * @param description Appointment description.
     * @param location Appointment location.
     * @param type Appointment type.
     * @param startTime Appointment start time.
     * @param endTime Appointment end time.
     * @param customerID Customer ID for appointment.
     * @param userID User ID for appointment.
     * @param contactID Contact ID for appointment.
     */
   public Appointments(int appointmentID, String title, String description, String location, String type,
                        LocalDateTime startTime, LocalDateTime endTime, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Constructor for some Appointment DAO without appointment ID.
     *
     * @param title Appointment title.
     * @param description Appointment description.
     * @param location Appointment location.
     * @param type Appointment type.
     * @param startTime Appointment start time.
     * @param endTime Appointment end time.
     * @param customerID Customer ID for appointment.
     * @param userID User ID for appointment.
     * @param contactID Contact ID for appointment.
     */
    public Appointments(String title, String description, String location, String type,
                         LocalDateTime startTime, LocalDateTime endTime, int customerID, int userID, int contactID) {

        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * No argument constructor.
     */
    public Appointments() {
    }


    /**
     * The getter for appointmentID.
     *
     * @return the appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * The setter for appointmentID.
     *
     * @param appointmentID the appointmentID.
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }


    /**
     * The getter for appointment title.
     *
     * @return the appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * The setter for appointment title.
     *
     * @param title the appointment title.
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * The getter for appointment description.
     *
     * @return the appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * The setter for appointment description.
     *
     * @param description the appointment description.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * The getter for appointment location.
     *
     * @return the appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * The setter for appointment location.
     *
     * @param location the appointment location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The getter for appointment type.
     *
     * @return the appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * The setter for appointment type.
     *
     * @param type the appointment type.
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * The getter for appointment start time.
     *
     * @return the appointment start time.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * The setter for appointment start time.
     *
     * @param startTime the appointment start time.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }


    /**
     * The getter for appointment end time.
     *
     * @return the appointment end time.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * The setter for appointment end time.
     *
     * @param endTime the appointment end time.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }



    /**
     * The getter for appointment customerID.
     *
     * @return the appointment customerID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * The setter for appointment customerID.
     *
     * @param customerID the appointment customerID.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }


    /**
     * The getter for appointment userID.
     *
     * @return the appointment userID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * The setter for appointment userID.
     *
     * @param userID the appointment userID.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**
     * The getter for appointment contactID.
     *
     * @return the appointment contactID.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * The setter for appointment contactID.
     *
     * @param contactID the appointment contactID.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
