package DAO_DBAccess;


import Model.Customers;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class that accesses the database Customer table.
 */
public class CustomersDAO {

    /**
     * Gets all customers from database.
     *
     * @return all customers.
     */
    public static ObservableList<Customers> getAllCustomers() {

        ObservableList<Customers> customersList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, countries.Country, first_level_divisions.Division,customers.Division_ID, countries.Country_ID FROM customers INNER JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            while(rs.next()) {
                Customers newCustomer = new Customers(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getString("Country"),
                    rs.getString("Division"),
                    rs.getInt("Division_ID"),
                    rs.getInt("Country_ID"));

                customersList.add(newCustomer);
            }
            return customersList;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * Adds a customer to the database.
     *
     * @param customer the customer to be added.
     * @throws SQLException from DBConnection.
     */
    public static void addCustomer(Customers customer) throws SQLException {
        try {

            String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                    "VALUES(?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setInt(5, customer.getDivisionID());

            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits an existing customer in the database.
     *
     * @param customer the customer to be edited.
     * @throws SQLException from DBConnection.
     */
    public static void editCustomer(Customers customer) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, customer.getDivisionID());
        ps.setInt(6, customer.getCustomerID());

        ps.executeUpdate();

    }

    /**
     * Deleted and existing customer from the database.
     *
     * @param dbCustomerID  the customer to be deleted.
     */
    public static void deleteCustomer(int dbCustomerID) {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, dbCustomerID);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets a specific customer with matching customerID.
     * @param theCustomerID the customer ID.
     * @return the specific customer with matching ID.
     * @throws SQLException from DBConnection.
     */
    public static Customers getCustomerByID(int theCustomerID) throws SQLException {

        Customers customers = new Customers();

        try {

            String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, theCustomerID);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            rs.next();

            customers.setCustomerID(rs.getInt("Customer_ID"));
            customers.setName(rs.getString("Customer_Name"));
            customers.setAddress(rs.getString("Address"));
            customers.setPostalCode(rs.getString("Postal_Code"));
            customers.setPhone(rs.getString("Phone"));
            customers.setDivisionID(rs.getInt("Division_ID"));
        }
            catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
