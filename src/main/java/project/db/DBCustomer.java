package project.db;

import project.gui.MainProject;
import project.logic.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBCustomer {

    //krijgt landcode en retourneert het land
    public static String getCountry(String landcode) throws DBException {
        try {
            Statement stmt = MainProject.con.createStatement();
            String sql = "SELECT name FROM countries "
                    + "WHERE  twoChar = '" + landcode + "'";
            ResultSet srs = stmt.executeQuery(sql);
            String country;
            if (srs.next()) {
                country = srs.getString("name");
            } else {
                return null;
            }
            return country;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    //retourneert een Customerobject obv firstName, lastName en dateOfBirth
    public static Customer getCustomer(Customer c) throws DBException {
        try {
            Statement stmt = MainProject.con.createStatement();
            String sql = "SELECT firstName, lastName, dateOfBirth, country, pasportNR "
                    + "FROM Customers "
                    + "WHERE customerID = '" + c.getCustomerID() + "'";
            ResultSet srs = stmt.executeQuery(sql);
            String firstName, lastName, dateOfBirthString, pasportNRAndCountry;
            String dateOfBirth;
            if (srs.next()) {
                firstName = srs.getString("firstName");
                lastName = srs.getString("lastName");
                //getObject(kolomindex, klassetype)
                dateOfBirth = srs.getObject(3, String.class);
                pasportNRAndCountry = srs.getString("country") + srs.getString("pasportNR");
            } else {
                return null;
            }
            Customer customer = new Customer(firstName, lastName, dateOfBirth, pasportNRAndCountry);
            return customer;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    //add een customer wanneer nog niet in de database
    //modified customer indien wel al in de database
    public static void saveCustomer(Customer c) throws DBException {
        try {
            Statement stmt = MainProject.con.createStatement();
            String sql = "SELECT customerID "
                    + "FROM Customers "
                    + "WHERE customerID = '" + c.getCustomerID() + "'";
            ResultSet srs = stmt.executeQuery(sql);
            if (srs.next()) {
                String sql2 = "SET customerID = '" + c.getCustomerID()
                        + "' firstName = '" + c.getFirstName()
                        + "' lastName = '" + c.getLastName()
                        + "' dateOfBirth = '" + c.getDateOfBirth()
                        + "' country = '" + c.getCountry()
                        + "' pasportNR = '" + c.getPasportNR() + "'";
                stmt.executeUpdate(sql2);
            } else {
                String sql3 = "INSERT into Customers "
                        + "(customerID, firstName, lastName, dateOfBirth, country, pasportNR) "
                        + "VALUE ('" + c.getCustomerID()
                        + "', '" + c.getFirstName()
                        + "', '" + c.getLastName()
                        + "', '" + c.getDateOfBirth()
                        + "', '" + c.getCountry()
                        + "', '" + c.getPasportNR() + "'" + ")";
                stmt.executeUpdate(sql3);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    //delete een customer
    public static void deleteCustomer(Customer c) throws DBException {
        try {
            Statement stmt = MainProject.con.createStatement();
            String sql = "DELETE FROM customers "
                    + "WHERE customerID = '" + c.getCustomerID() + "'";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    //retourtneert een customer obv een customerID
    //--> wordt gebruikt in de getCustomers() om de String die geretourneerd wordt van srs.getString("customerID") verder te verwerken
    public static Customer getCustomer(String custID) throws DBException {
        try {
            Statement stmt = MainProject.con.createStatement();
            String sql = "SELECT customerID, firstName, lastName, dateOfBirth, country, pasportNR FROM customers "
                    + "WHERE customerID = '" + custID + "'";
            ResultSet srs = stmt.executeQuery(sql);
            String firstName, lastName, dateOfBirthString, pasportNRAndCountry;
            String dateOfBirth;
            if (srs.next()) {
                firstName = srs.getString("firstName");
                lastName = srs.getString("lastName");
                //getObject(kolomindex, klassetype)
                dateOfBirth = srs.getObject(4, String.class);
                pasportNRAndCountry = srs.getString("country") + srs.getString("pasportNR");
                Customer customer = new Customer(firstName, lastName, dateOfBirth, pasportNRAndCountry);
                return customer;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    public static ArrayList<Customer> getCustomers() throws DBException {
        try {
            Statement stmt = MainProject.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT customerID, country, pasportnr, firstName, lastName, dateOfBirth "
                    + "FROM Customers ";
            ResultSet srs = stmt.executeQuery(sql);
            ArrayList<Customer> customers = new ArrayList<Customer>();
            while (srs.next()) {
                customers.add(getCustomer(srs.getString("customerID")));
            }
            return customers;
        } catch (DBException dbe) {
            dbe.printStackTrace();
            throw dbe;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    public static Customer getCustomerGui(String custID) throws DBException {
        try {
            Statement stmt = MainProject.con.createStatement();
            String sql = "SELECT customerID, firstName, lastName, dateOfBirth, country, pasportNR FROM customers "
                    + "WHERE customerID = '" + custID + "'";
            ResultSet srs = stmt.executeQuery(sql);
            String firstName, lastName, dateOfBirth;
            if (srs.next()) {
                firstName = srs.getString("firstName");
                lastName = srs.getString("lastName");
                //getObject(kolomindex, klassetype)
                dateOfBirth = srs.getObject(4, String.class);
                Customer customer = new Customer(firstName, lastName, dateOfBirth);
                return customer;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            throw new DBException(ex);
        }
    }

    public static boolean containsCustomer(String custid) throws DBException {
        boolean gevonden = false;
        try {
            Statement stmt = MainProject.con.createStatement();
            String sql = "SELECT customerid "
                    + "FROM customers "
                    + "WHERE customerID = '" + custid + "'";
            ResultSet srs = stmt.executeQuery(sql);
            if (srs.next()) {
                gevonden = true;
            } else {
                gevonden = false;
            }
            return gevonden;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    public static void changeFirstName(String custID, String nieuweVoornaam) throws DBException {
        try {
            Statement stmt = MainProject.con.createStatement();
            String sql = "UPDATE customers "
                    + "SET firstName = '" + nieuweVoornaam + "'"
                    + "WHERE customerID = '" + custID + "'";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    public static void changeCustomerID(String oudeCustID, String nieuweCustID) throws DBException, SQLException {
        Statement stmt = MainProject.con.createStatement();
        String sql = "UPDATE customers "
                + "SET customerID = '" + nieuweCustID + "'"
                + "WHERE customerID = '" + oudeCustID + "'";
        stmt.executeUpdate(sql);
    }

    public static void changeLastName(String custID, String nieuweFamilienaam) throws DBException, SQLException {
        Statement stmt = MainProject.con.createStatement();
        String sql = "UPDATE customers "
                + "SET lastName = '" + nieuweFamilienaam + "'"
                + "WHERE customerID = '" + custID + "'";
        stmt.executeUpdate(sql);
    }

    public static void changeCountry(String custID, String newCountry) throws DBException, SQLException {
        Statement stmt = MainProject.con.createStatement();
        String sql = "UPDATE customers "
                + "SET country = '" + newCountry + "'"
                + "WHERE customerID = '" + custID + "'";
        stmt.executeUpdate(sql);
    }

    public static void changePaspoortNR(String custID, String newPaspoortNR) throws DBException, SQLException {
        Statement stmt = MainProject.con.createStatement();
        String sql = "UPDATE customers "
                + "SET pasportnr = '" + newPaspoortNR + "'"
                + "WHERE customerID = '" + custID + "'";
        stmt.executeUpdate(sql);
    }

    public static String getCustomerCountry(String custID) throws DBException {
        try {
            Statement stmt = MainProject.con.createStatement();
            String sql = "SELECT country "
                    + "FROM Customers "
                    + "WHERE customerID = '" + custID + "'";
            ResultSet srs = stmt.executeQuery(sql);
            String country;
            if (srs.next()) {
                country = srs.getString("country");
            } else {
                return "hello";
            }
            return country;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    public static String getCustomerPasportNR(String custID) throws DBException {
        try {
            Statement stmt = MainProject.con.createStatement();
            String sql = "SELECT pasportNR "
                    + "FROM Customers "
                    + "WHERE customerID = '" + custID + "'";
            ResultSet srs = stmt.executeQuery(sql);
            String country;
            if (srs.next()) {
                country = srs.getString("pasportNR");
            } else {

                return null;
            }

            return country;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    public static ArrayList<String> getAllCountries() throws DBException {
        try {
            Statement stmt = MainProject.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT name "
                    + "FROM countries ";

            ResultSet srs = stmt.executeQuery(sql);
            ArrayList<String> namenCountries = new ArrayList<String>();

            while (srs.next()) {
                namenCountries.add(srs.getString("name"));
            }
            return namenCountries;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    public static void toevoegenKost(String custID, double openstaandBedrag) throws DBException, SQLException {
        Statement stmt = MainProject.con.createStatement();
        String sql = "UPDATE customers "
                + "SET opstaandBedrag = '" + openstaandBedrag + "'"
                + "WHERE customerID = '" + custID + "'";
        stmt.executeUpdate(sql);
    }

    public static void aftrekkenKost(String custID, double afTeTrekkenKost) throws DBException, SQLException {
        Statement stmt = MainProject.con.createStatement();
        String sql = "SELECT openstaandBedrag "
                + "FROM customers "
                + "WHERE customerID = '" + custID + "'";

        ResultSet srs = stmt.executeQuery(sql);
        double oudOpenstaandBedrag = srs.getInt("openstaandBedrag");
        double nieuwOpenstaandBedrag = oudOpenstaandBedrag - afTeTrekkenKost;
        String sql2 = "UPDATE customers "
                + "SET opstaandBedrag = '" + nieuwOpenstaandBedrag + "'"
                + "WHERE customerID = '" + custID + "'";
        stmt.executeUpdate(sql2);
    }
}