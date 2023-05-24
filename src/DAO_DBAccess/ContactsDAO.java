package DAO_DBAccess;

import Model.Contacts;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class that accesses the database Contacts table.
 */
public class ContactsDAO {

    /**
     * Gets all contacts from database.
     *
     * @return all contacts.
     */
    public static ObservableList<Contacts> getAllContacts() {

        ObservableList<Contacts> contactList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contacts newContact = new Contacts(contactID, contactName, contactEmail);
                contactList.add(newContact);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contactList;
    }

    /**
     * Gets all contacts with matching contactID.
     *
     * @param theContactID the contact ID.
     * @return matching contact.
     * @throws SQLException from DBConnection.
     */
    public static Contacts getContactNameByID(int theContactID) throws SQLException {

        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setInt(1, theContactID);
        ps.executeQuery();

        try {
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contacts newContact = new Contacts(contactID, contactName, contactEmail);
                return newContact;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
