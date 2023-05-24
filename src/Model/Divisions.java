package Model;

/**
 * Class used to make the Divisions object.
 */
public class Divisions {

    private int divisionID;
    private String divisionName;
    private int countryID;

    /**
     * Constructor for the Divisions object
     *
     * @param divisionID the division ID.
     * @param divisionName the division name.
     * @param countryID the country ID.
     */
    public Divisions(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * The no argument constructor.
     */
    public Divisions() {
    }


    /**
     * The getter for division ID.
     *
     * @return the divisionID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * The setter for division ID.
     *
     * @param divisionID the division ID.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }


    /**
     * The getter for division name.
     *
     * @return the divisionName.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * The setter for division name.
     *
     * @param divisionName the divisionName.
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }


    /**
     * The getter for country ID.
     *
     * @return the countryID.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * The setter for country ID.
     *
     * @param countryID the countryID.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Override for combo box.
     *
     * @return the divisionName.
     */
    @Override
    public String toString() {
        return divisionName;
    }
}
