package DAO_DBAccess;

import Model.Users;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * DAO class that accesses the database User table.
 */
public class UsersDAO {

    /**
     * Gets all users from database.
     *
     * @return all users.
     */
    public static ObservableList<Users> getAllUsers() {

        ObservableList<Users> usersList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                Users users = new Users(userID, userName, password);
                usersList.add(users);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usersList;
    }

    /**
     * Gets users from database with matching username and password.
     *
     * @param username the username.
     * @param password the password.
     * @return matching user.
     */
    public static Users checkForUser(String username, String password) {

        try {
            String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Users currentUser = new Users();
                currentUser.setUserID(rs.getInt("User_ID"));
                currentUser.setUserName(rs.getString("User_Name"));
                currentUser.setPassword(rs.getString("Password"));
                return currentUser;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets user from database with matching userID.
     *
     * @param theUserID the user ID.
     * @return the matching user.
     * @throws SQLException from DBConnection.
     */
    public static Users getUserByID(int theUserID) throws SQLException {

        Users users = new Users();

        try {

            String sql = "SELECT * FROM users WHERE User_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, theUserID);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            rs.next();

            users.setUserID(rs.getInt("User_ID"));
            users.setUserName(rs.getString("User_Name"));
            users.setPassword(rs.getString("Password"));

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
