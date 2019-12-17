/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.db;

import project.gui.MainProject;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Gunter
 */
public class DBAirplane {

    public static int getMaxSeats(String at) throws DBException {

        try {

            Statement stmt = MainProject.con.createStatement();

            String sql = "SELECT maxSeats "
                    + "FROM airplanes "
                    + "WHERE airplaneType = '" + at + "'";

            ResultSet srs = stmt.executeQuery(sql);
            int maxSeats = 0;

            while (srs.next()) {
                maxSeats = srs.getInt("maxSeats");
            }
            return maxSeats;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

}