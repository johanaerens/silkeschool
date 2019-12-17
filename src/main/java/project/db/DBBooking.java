/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.db;

import project.logic.OfficialBooking;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import project.logic.OfficialBooking;

/**
 * @author Gunter
 */
public class DBBooking {


    //deze methode is nodig om de methode getBookings( int custID) uit te voeren
//    private static Booking getBooking(String bookid) throws DBException {
//        Connection con = null;
//        try{
//            con = DBConnector.getConnection();
//            Statement stmt = con.createStatement();
//            
//            String sql = "SELECT bookingID, customerID, dateOfDep, dateOfArr, totalTransfers, dateOfBooking, flightnrs, CO2Booking "
//                    + "FROM Bookings "
//                    + "WHERE bookingId = " + bookid;
//            
//            ResultSet srs = stmt.executeQuery(sql);
//            String bookingID, customerID, flightnr;
//            LocalDate dateOfDep, dateOfArr, dateOfBooking;
//            
//            int totalTransfers;
//            double CO2Booking, price, totalDistance;
//            
//            if (srs.next()) {
//                bookingID = srs.getString("bookingID");
//                customerID = srs.getString("customerID");
//                dateOfDep = srs.getObject(3, LocalDate.class);
//                dateOfArr = srs.getObject(4, LocalDate.class);
//                dateOfBooking = srs.getObject(6, LocalDate.class);
//                totalTransfers = srs.getInt("totalTransfers");
//                totalDistance = srs.getInt("distance");
//                flightnr = srs.getString("flightnrs");
//                CO2Booking = srs.getDouble("CO2BOOking");
//                price = srs.getDouble("price");
//            } else {
//                DBConnector.closeConnection(con);
//                return null;
//            }
//            
//            // dinsdag:
//            // constructor klopt niet: maar 1 vluchtnummer in constructor
//            // zou dan ook niet werken voor 3 vluchten
//            // verschillende methodes voor getBooking adhv totalTransfers?
//            // price ontbreekt
//            // Booking booking = new Booking(bookingID, customerID, dateOfDep, dateOfArr,
//            //        totalTransfers, dateOfBooking, flight1, flight2, CO2Booking);
//            Booking booking = new Booking(bookingID, customerID, dateOfDep, dateOfArr,
//                    totalTransfers, dateOfBooking, totalDistance, price, flightnr, CO2Booking);
//            DBConnector.closeConnection(con);
//            return booking;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            DBConnector.closeConnection(con);
//            throw new DBException(ex);
//        }
//    }
    // dinsdag:
    // wat is OfficialBooking?
    // ook in imports maar geen klasse
    // methodes niet uit logic.Booking maar uit logic.Flight

    //public static void saveBooking(OfficialBooking b) throws DBException{
    public static void saveBooking(OfficialBooking b) throws DBException, SQLException {
        Connection con = null;
        try {
            con = DBConnector.getConnection();
            Statement stmt = con.createStatement();


            //INSERT
            String sql = "INSERT into Bookings "
                    + "(bookingID, customerID, dateOfDep, dateOfArr, totalTransfers, dateOfBooking, flightNrs, CO2Booking, airportCodeDep, "
                    + "airportCodeArr, price, distance, duration) "
                    + "VALUES ('" + b.getBookingID() + "'"
                    + ", '" + b.getCustomerID() + "'"
                    + ", '" + b.getDateOfDeparture() + "'"
                    + ", '" + b.getDateOfArrival() + "'"
                    + ", '" + b.getTotalTransfers() + "'"
                    + ", '" + b.getDateOfBooking() + "'"
                    + ", '" + b.getFlightNRs() + "'"
                    + ", '" + b.getTotaleuitstoot() + "'"
                    + ", '" + b.getCodeOfDep() + "'"
                    + ", '" + b.getCodeOfArr() + "'"
                    + ", '" + b.getTotalPrice() + "'"
                    + ", '" + b.getTotalDistance() + "'"
                    + ", '" + b.getTotalDuration() + "') ";
            stmt.executeUpdate(sql);

            DBConnector.closeConnection(con);
        } catch (Exception ex) {
            ex.printStackTrace();
            DBConnector.closeConnection(con);
            throw new DBException(ex);
        }
    }


    //retourneert een arrayList van alle bookings voor een bepaalde customer waarvan customerID gegeven
//    public static ArrayList<Booking> getBookings(String customerID) throws DBException {
//        Connection con = null;
//        try {
//            con = DBConnector.getConnection();
//            Statement stmt = con.createStatement();
//            
//            String sql = "SELECT bookingID "
//                    + "FROM Bookings"
//                    + "WHERE customerID = '" + customerID + "'";
//            ResultSet srs = stmt.executeQuery(sql);
//            ArrayList<Booking> boekingen = new ArrayList<Booking>();
//            while (srs.next())
//                boekingen.add(getBooking(srs.getString("bookingID")));
//            DBConnector.closeConnection(con);
//            return boekingen;
//        } catch (DBException dbe) {
//            dbe.printStackTrace();
//            DBConnector.closeConnection(con);
//            throw dbe;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            DBConnector.closeConnection(con);
//            throw new DBException(ex);
//        }
//    }

    // retourneert een arrayList van LocalDates die de data zijn wanneer de boekingen gemaakt zijn
    // nodig voor salesReport?? --> waar????
    public static ArrayList<String> getDatesBookings() throws DBException {
        Connection con = null;
        try {
            con = DBConnector.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT dateOfBooking "
                    + "FROM bookings ";

            ResultSet srs = stmt.executeQuery(sql);
            ArrayList<String> datumBoekingen = new ArrayList<String>();

            while (srs.next()) {
                datumBoekingen.add(srs.getString("dateOfBooking"));
            }
            DBConnector.closeConnection(con);
            return datumBoekingen;
        } catch (DBException dbe) {
            dbe.printStackTrace();
            DBConnector.closeConnection(con);
            throw dbe;
        } catch (Exception ex) {
            ex.printStackTrace();
            DBConnector.closeConnection(con);
            throw new DBException(ex);
        }
    }

    //nodig voor salesReport
    public static ArrayList<String> getDatesDep() throws DBException {
        Connection con = null;
        try {
            con = DBConnector.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT dateOfDep "
                    + "FROM bookings ";

            ResultSet srs = stmt.executeQuery(sql);
            ArrayList<String> datumBoekingen = new ArrayList<String>();

            while (srs.next()) {
                datumBoekingen.add(srs.getString("dateOfDep"));
            }
            DBConnector.closeConnection(con);
            return datumBoekingen;
        } catch (DBException dbe) {
            dbe.printStackTrace();
            DBConnector.closeConnection(con);
            throw dbe;
        } catch (Exception ex) {
            ex.printStackTrace();
            DBConnector.closeConnection(con);
            throw new DBException(ex);
        }
    }


}