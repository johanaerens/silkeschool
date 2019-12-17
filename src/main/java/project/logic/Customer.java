package project.logic;

import project.db.DBCustomer;
import project.db.DBException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer {
    private String dateOfBirth;
    private String firstName;
    private String lastName;
    private String pasportNRAndCountry;
    private String country;
    private String pasportNR;
    private String customerID;


    private static ArrayList<Customer> customers;
    private static ArrayList<String> allCountries;
    private static Customer customer = new Customer();


    public Customer(String voornaam, String achterNaam, String dateOfBirth, String landCode, String PaspoortNR) {
        this.firstName = voornaam;
        this.lastName = achterNaam;
        this.dateOfBirth = dateOfBirth;
        this.country = landCode;
        this.pasportNR = PaspoortNR;
        this.pasportNRAndCountry = landCode + PaspoortNR;
        this.customerID = voornaam + achterNaam + dateOfBirth;

    }

    public Customer() {
        try {
            customers = DBCustomer.getCustomers();
        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Customer getInstance() {
        return customer;
    }

    // aanmaken van een customer voor het gebruik van addCustomer, waar we alle gegevens nodig hebben.
    public Customer(String firstName, String lastName, String dateOfBirth, String pasportNRAndCountry) throws DBException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.pasportNRAndCountry = pasportNRAndCountry;
        customerID = firstName + lastName + dateOfBirth;
        country = DBCustomer.getCustomerCountry(customerID);
        pasportNR = DBCustomer.getCustomerPasportNR(customerID);

    }

    public Customer(String firstName, String lastName, String dateOfBirth) throws DBException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        customerID = firstName + lastName + dateOfBirth;
        country = DBCustomer.getCustomerCountry(customerID);
        pasportNR = DBCustomer.getCustomerPasportNR(customerID);
    }

    public void setPasportNRAndCountry(String pasportNRAndCountry) {
        this.pasportNRAndCountry = pasportNRAndCountry;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPasportNRAndCountry() {
        return pasportNRAndCountry;
    }

    // Deze methode wordt opgeroepen door de Databese
    public String getCountryGui() throws DBException {
        country = DBCustomer.getCustomerCountry(customerID);
        return country;
    }

    // Deze methode wordt opgeroepen door de Databese
    public String getPasportNRGui() throws DBException {
        pasportNR = DBCustomer.getCustomerPasportNR(customerID);
        return pasportNR;
    }

    public String getCountry() {
        return country;
    }

    public String getPasportNR() {
        return pasportNR;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean bestaatDeCustomer(Customer c) throws DBException {
        boolean zitErin = false;
        if (DBCustomer.containsCustomer(customerID))
            zitErin = true;
        return zitErin;
    }

    //zie project gailly... exact dezelfde methode, deze instantie moet dus aangemaakt worden in de gui (zie StudentAddController.java)
    public boolean addCustomer(Customer customer) {

        try {
            DBCustomer.saveCustomer(customer);


        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    // deze =::=methode wordt opgeroepen door GUI, indien de knop 'verwijderCustomer' wordt aangeklikt
    // Hierdoor roepen wij een methode op vanuit de DB, die de customer met die parameters verwijderd  

    public static void deleteCustomer(String firstName, String lastName, String dateOfBirth) {
        try {
            Customer c = new Customer(firstName, lastName, dateOfBirth);
            try {
                DBCustomer.deleteCustomer(c);

            } catch (DBException ex) {
                Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<Customer> getCustomers() {
        try {
            customers = DBCustomer.getCustomers();
        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customers;
    }


    //de eerste lijn koppelt de eerder gemaakte en momenteel nog lege ArrayList customers aan alle customers uit de database, vervolgens wordt deze arraylist geretourneerd via de return statement.

    public Boolean isCustomer(Customer customer) throws DBException {
        Customer tevergelijkencustomer;
        tevergelijkencustomer = getCustomer(customer.getFirstName(), customer.getLastName(), customer.getDateOfBirth());
        if (tevergelijkencustomer == null)
            return false;
        else
            return tevergelijkencustomer == customer;
    }

    public void changeCustomerFirstName(String newfirstName) throws DBException, SQLException {
        DBCustomer.changeFirstName(this.customerID, newfirstName);
        firstName = newfirstName;
        String nieuweID = newfirstName + this.lastName + this.dateOfBirth;
        DBCustomer.changeCustomerID(this.customerID, nieuweID);
        customerID = nieuweID;
    }


    public void changeCustomerLastName(String nieuweLastname) throws DBException, SQLException {
        DBCustomer.changeLastName(this.customerID, nieuweLastname);
        lastName = nieuweLastname;
        String nieuweID = this.firstName + nieuweLastname + this.dateOfBirth;
        DBCustomer.changeCustomerID(this.customerID, nieuweID);
        customerID = nieuweID;
    }

    public void changeCountry(String nieuwLand) throws DBException, SQLException {
        DBCustomer.changeCountry(this.customerID, nieuwLand);
        country = nieuwLand;
    }

    public void changePaspoort(String nieuwePaspoort) throws DBException, SQLException {
        DBCustomer.changePaspoortNR(this.customerID, nieuwePaspoort);
        pasportNR = nieuwePaspoort;

    }

    public static Customer getCustomer(String firstName, String lastName, String dateOfBirth) throws DBException {
        Customer dezeCustomer = new Customer(firstName, lastName, dateOfBirth);
        dezeCustomer = DBCustomer.getCustomer(dezeCustomer);
        return dezeCustomer;
    }

    @Override
    public String toString() {
        String output;
        output = "FIRST NAME: " + firstName + " ||  LAST NAME: " + lastName + "\nDATE OF BIRTH: " + dateOfBirth
                + " ||  COUNTRY OF BIRTH: " + country + "\nPASSPORTNUMBER: " + pasportNR;
        return output;
    }

    public static ArrayList<String> getAllCountries() {
        allCountries = new ArrayList<String>();
        try {
            allCountries = DBCustomer.getAllCountries();
        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allCountries;
    }

    public void addACost(OfficialBooking b) throws DBException, SQLException {
        double prijsVanDeBoeking;
        prijsVanDeBoeking = b.getTotalPrice();
        String customerID = b.getCustomerID();
        DBCustomer.addACost(customerID, prijsVanDeBoeking);
    }

    public void aftrekkenVanDeKost(OfficialBooking b) throws DBException, SQLException {
        double prijsVanDeBoeking;
        prijsVanDeBoeking = b.getTotalPrice();
        String customerID = b.getCustomerID();
        DBCustomer.aftrekkenKost(customerID, prijsVanDeBoeking);
    }

}