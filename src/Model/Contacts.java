package Model;

/**
 * Class used to make the Contacts object.
 */
public class Contacts {

    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Constructor for the Contacts object.
     * @param contactID contact ID.
     * @param contactName contact name.
     * @param contactEmail contact email.
     */
    public Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * The getter for contact ID.
     *
     * @return the contactID.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * The setter for contact ID.
     *
     * @param contactID the contactID.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


    /**
     * The getter for contact name.
     *
     * @return the contactName.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * The setter for contact name.
     *
     * @param contactName the contactName.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    /**
     * The getter for contact email.
     *
     * @return the contactEmail.
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * The setter for contact email.
     *
     * @param contactEmail the contactEmail.
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }


    /**
     * Override for combo box.
     *
     * @return contactName.
     */
    @Override
    public String toString() {
        return contactName;
    }
}

