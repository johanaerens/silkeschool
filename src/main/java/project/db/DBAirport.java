/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.db;

import project.gui.MainProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Gunter
 */
public class DBAirport {


    //geeft alle airportnames in een arraylist
    //voor gui
    public static ArrayList<String> getAirportNames() throws DBException {
        Connection con = null;
        try {
            con = DBConnector.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT airportName "
                    + "FROM airports ";

            ResultSet srs = stmt.executeQuery(sql);
            ArrayList<String> luchthavens = new ArrayList<String>();

            while (srs.next()) {
                luchthavens.add(srs.getString("airportName"));
            }
            return luchthavens;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    //returned de airportcode obv een aiportname
    //voor gui
    public static String getAirportCode(String an) throws DBException {

        try {
            String airportcode = null;
            Statement stmt = MainProject.con.createStatement();

            String sql = "SELECT airportCode "
                    + "FROM airports WHERE airportName = '" + an + "'";

            ResultSet srs = stmt.executeQuery(sql);

            while (srs.next()) {
                airportcode = srs.getString("airportCode");
            }
            return airportcode;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    public static String getAirportName(String acode) throws DBException {

        try {
            String airportname = null;
            Statement stmt = MainProject.con.createStatement();

            String sql = "SELECT airportName "
                    + "FROM airports WHERE airportCode = '" + acode + "'";

            ResultSet srs = stmt.executeQuery(sql);

            while (srs.next()) {
                airportname = srs.getString("airportName");
            }
            return airportname;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }


    //moet een arrayList retourneren van alle airports die gebruikt worden bij de boekingen
    //salesReport
    public static ArrayList<String> getAllAirportsBookings() throws DBException {
        Connection con = null;
        try {
            con = DBConnector.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT airportCodeDep, airportCodeArr "
                    + "FROM bookings ";


            ResultSet srs = stmt.executeQuery(sql);
            ArrayList<String> luchthavens = new ArrayList<String>();

            while (srs.next()) {
                luchthavens.add(srs.getString("airportCodeDep"));
                luchthavens.add(srs.getString("airportCodeArr"));
            }

            DBConnector.closeConnection(con);
            return luchthavens;
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


    public static ArrayList<String> getAirportsDepBookings() throws DBException {
        Connection con = null;
        try {
            con = DBConnector.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT airportCodeDep "
                    + "FROM bookings ";


            ResultSet srs = stmt.executeQuery(sql);
            ArrayList<String> luchthavens = new ArrayList<String>();

            while (srs.next()) {
                luchthavens.add(srs.getString("airportCodeDep"));
            }

            DBConnector.closeConnection(con);
            return luchthavens;
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


    public static ArrayList<String> getAirportsArrBookings() throws DBException {
        Connection con = null;
        try {
            con = DBConnector.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT airportCodeArr "
                    + "FROM bookings ";


            ResultSet srs = stmt.executeQuery(sql);
            ArrayList<String> luchthavens = new ArrayList<String>();

            while (srs.next()) {
                luchthavens.add(srs.getString("airportCodeArr"));
            }

            DBConnector.closeConnection(con);
            return luchthavens;
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

    public static void main(String args[]) throws DBException, SQLException {

        ArrayList<String> airports = new ArrayList<String>();

        airports = getAirportNames();

        System.out.println(airports);
    }

}