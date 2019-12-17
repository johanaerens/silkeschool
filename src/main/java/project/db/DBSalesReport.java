/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.db;

/**
 * @author Gunter
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Pieter
 */
public class DBSalesReport {
    public static double getTotalPricePerMonth(int maand, int jaar) throws DBException {
        Connection con = null;
        try {
            con = DBConnector.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT sum(price) AS totalprijs "
                    + "FROM bookings "
                    + "WHERE dateOfBooking like '" + jaar + "_" + maand + "___' ";

            ResultSet srs = stmt.executeQuery(sql);

            double totprijs = 0;
            if (srs.next())
                totprijs = srs.getDouble("totalprijs");
            return totprijs;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    public static double getTotalPricePerYear(int jaar) throws DBException {
        Connection con = null;
        try {
            con = DBConnector.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT sum(price) AS totalprijs "
                    + "FROM bookings "
                    + "WHERE dateOfBooking like '" + jaar + "______'";

            ResultSet srs = stmt.executeQuery(sql);

            double totprijs = 0;
            if (srs.next())
                totprijs = srs.getDouble("totalprijs");
            return totprijs;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }


    public static void main(String[] ars) throws DBException {
        double prijs = getTotalPricePerYear(2019);
        System.out.println(prijs);
    }
}