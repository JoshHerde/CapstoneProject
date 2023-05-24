package Model;

/**
 * Class used to make the Customers object.
 */
public class Customers {

    private int customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private String division;
    private int divisionID;
    private int countryID;

    /**
     * Constructor for the Customers object.
     *
     * @param customerID customer ID.
     * @param name customer name.
     * @param address customer address.
     * @param postalCode customer postal code.
     * @param phone customer phone.
     * @param country customer country.
     * @param division customer division.
     * @param divisionID customer division ID.
     * @param countryID customer country ID.
     */
    public Customers(int customerID, String name, String address, String postalCode, String phone, String country, String division, int divisionID, int countryID) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
        this.division = division;
        this.divisionID = divisionID;
        this.countryID = countryID;
    }

    /**
     * Constructor for some Customers DAO without Customer ID.
     *
     * @param name customer name.
     * @param address customer address.
     * @param postalCode customer postal code.
     * @param phone customer phone.
     * @param divisionID customer division ID.
     */
    public Customers(String name, String address, String postalCode, String phone, int divisionID) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    /**
     * No argument constructor.
     */
    public Customers() {

    }


    /**
     * The getter for customer ID.
     *
     * @return the customerID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * The setter for customer ID.
     *
     * @param customerID the customerID.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }


    /**
     * The getter for customer name.
     *
     * @return the customerName.
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for customer name.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * The getter for customer address.
     *
     * @return the address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * The setter for customer address.
     *
     * @param address the address.
     */
    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * The getter for customer postal code.
     *
     * @return the postalCode.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * The setter for customer postal code.
     *
     * @param postalCode the postal code.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * The getter for customer phone.
     *
     * @return the customer phone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * The setter for customer phone.
     *
     * @param phone the phone.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }


    /**
     * The getter for customer country.
     *
     * @return the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * The setter for customer country.
     *
     * @param country the country.
     */
    public void setCountry(String country) {
        this.country = country;
    }


    /**
     * The getter for customer division.
     *
     * @return the division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * The setter for customer division.
     *
     * @param division the division.
     */
    public void setDivision(String division) {
        this.division = division;
    }


    /**
     * The getter for customer division ID.
     *
     * @return the countryID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * The setter for customer division ID.
     *
     * @param divisionID the division ID.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }


    /**
     * The getter for customer country ID.
     *
     * @return the countryID.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Override for combo box.
     *
     * @return name.
     */
    @Override
    public String toString() {
        return name;
    }
}
