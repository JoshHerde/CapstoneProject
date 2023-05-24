package Model;

/**
 * Class used to make the Users object.
 */
public class Users {

    private int userID;
    private String userName;
    private String password;

    /**
     * Constructor for the Users object.
     *
     * @param userID the user ID.
     * @param userName the user name.
     * @param password the user password.
     */
    public Users(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * The no argument constructor.
     */
    public Users() {

    }


    /**
     * The getter for user ID.
     *
     * @return the userID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * The setter for user ID.
     *
     * @param userID the userID.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * The getter for user name.
     *
     * @return the userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * The setter for user name.
     *
     * @param userName the userName.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * The getter for password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * The setter for password.
     *
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Override for combo box.
     *
     * @return userName.
     */
    @Override
    public String toString() {
        return userName;
    }

}
