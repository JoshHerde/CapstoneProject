package Model;

/**
 * Class used to make the Countries object.
 */
public class Countries {

    private int countryID;
    private String countryName;


    /**
     * Constructor for the Countries object.
     *
     * @param countryID country ID.
     * @param countryName country name.
     */
    public Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
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
     * The getter for country name.
     *
     * @return the countryName.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * The setter for country name.
     *
     * @param countryName the countryName.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Override for combo box.
     *
     * @return countryName.
     */
    @Override
    public String toString() {
        return (countryName);
    }
}
