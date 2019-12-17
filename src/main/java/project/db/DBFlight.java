
package project.db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import project.gui.MainProject;
import project.logic.Flight;

public class DBFlight {
////retourneert de CO2 voor het gegeven flightnr
//public static int getCO2(String flnr) throws DBException {
//    Connection con = null;
//    try {
//        con = DBConnector.getConnection();
//        Statement stmt = con.createStatement();
//        
//        String sql = "SELECT CO2 FROM flights "
//                + "WHERE flightnr = '" + flnr + "'";
//        
//        ResultSet srs = stmt.executeQuery(sql);
//        int CO2;
//        if(srs.next()){
//            CO2 = srs.getInt("CO2");
//        } else{
//            DBConnector.closeConnection(con);
//            return 0;
//        }
//        DBConnector.closeConnection(con);
//        return CO2;
//        } s
//    catch(Exception ex){
//            ex.printStackTrace();
//            DBConnector.closeConnection(con);
//            throw new DBException(ex);
//            }
//    }

//retrouneert de duration voor het gegeven flnr
public static int getDuration(String flnr) throws DBException {
    Connection con = null;
    try {
        con = DBConnector.getConnection();
        Statement stmt = con.createStatement();
        
        String sql = "SELECT duration FROM flights "
                + "WHERE flightnr = '" + flnr + "'";
        
        ResultSet srs = stmt.executeQuery(sql);
        int duration;
        if(srs.next()){
            duration = srs.getInt("duration");
        } else{
            DBConnector.closeConnection(con);
            return 0;
        }
        DBConnector.closeConnection(con);
        return duration;
        } 
    catch(Exception ex){
            ex.printStackTrace();
            DBConnector.closeConnection(con);
            throw new DBException(ex);
            }
}

//retourneert de distance voor het gegeven flightnr
public static int getDistance(String flnr) throws DBException {
    Connection con = null;
    try {
        con = DBConnector.getConnection();
        Statement stmt = con.createStatement();
        
        String sql = "SELECT distance FROM flights "
                + "WHERE flightnr = '" + flnr + "'";
        
        ResultSet srs = stmt.executeQuery(sql);
        int distance;
        if(srs.next()){
            distance = srs.getInt("distance");
        } else{
            DBConnector.closeConnection(con);
            return 0;
        }
        DBConnector.closeConnection(con);
        return distance;
        } 
    catch(Exception ex){
            ex.printStackTrace();
            DBConnector.closeConnection(con);
            throw new DBException(ex);
            }
    }

public static ArrayList<Flight> getFlights(LocalDate date) throws DBException{
       
        try{
            Statement stmt = MainProject.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT flightnr,dateOfDep,timeOfDep,dateOfArr,timeOfArr, airportCodeDep, airportCodeArr, companyName, CO2,price, distance, duration,airplaneType, seats "
            + "FROM Flights "
            + "WHERE dateOfDep >= '" + date + "'"
            + "AND dateOfDep <= '" + date.plusDays(2) + "'";
            ResultSet srs = stmt.executeQuery(sql);
            ArrayList<Flight> flights = new ArrayList<Flight>();
            while(srs.next()){
                flights.add(getFlight1(srs.getString("flightnr"),srs.getObject(2,LocalDate.class)));
            }
            
            return flights;
        }
        catch(DBException dbe){
            dbe.printStackTrace();
            
            throw dbe;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            
            throw new DBException(ex);
        }
    }

public static int getMaxSeats(String flnr, LocalDate dod) throws DBException{
    try {
        Statement stmt = MainProject.con.createStatement();
                String sql = "SELECT maxSeats "
                + "FROM flights "
                + "WHERE flightnr = '" + flnr + "'"
                + " AND dateOfDep = '" + dod + "'";
                ResultSet srs = stmt.executeQuery(sql);
                int maxSeats = 0;
                while(srs.next()){
                    maxSeats = srs.getInt("maxSeats");
                }
                return maxSeats;
            } catch (Exception ex) {
                ex.printStackTrace();                
                throw new DBException(ex);
            }
        }
    
    
    public static void seatsPlusOne(String flnr, LocalDate dod) throws DBException{
    try {
        Statement stmt = MainProject.con.createStatement();
            int oudeSeats = DBFlight.getSeats(flnr, dod);
            int nieuweSeats = oudeSeats +1;
            String sql = "UPDATE flights "
            + "SET seats = '" + nieuweSeats 
            + "' WHERE flightnr = '" + flnr + "'"
            + " AND dateOfDep = '" + dod + "'";
            
            stmt.executeUpdate(sql);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
            throw new DBException(ex);
        }
    } 
    public static void main(String args[]) throws DBException, SQLException{
        LocalDate date = LocalDate.of(2019, 01, 01);
        seatsPlusOne("0001", date);
    }
    public static int getSeats(String flnr, LocalDate dod) throws DBException{

        try {
            Statement stmt = MainProject.con.createStatement();
                    String sql = "SELECT seats "
                    + "FROM flights "
                    + "WHERE flightnr = '" + flnr + "'"
                    + " AND dateOfDep = '" + dod + "'";
                    ResultSet srs = stmt.executeQuery(sql);
                    int seats = 0;
                    while(srs.next()){
                        seats = srs.getInt("seats");
                    }
                    return seats;
                } catch (Exception ex) {
                    ex.printStackTrace();

                    throw new DBException(ex);
                }
    }
    
    //maakt een object flight aan o.b.v. een meegegeven flightnr
    
    public static Flight getFlight(String flnr) throws DBException{
        try{
            Statement stmt = MainProject.con.createStatement();
            String sql = "SELECT flightnr, dateOfDep, timeOfDep, dateOfArr, timeOfArr, airportCodeDep, airportCodeArr, companyName, CO2, price, distance, duration "
                    + "FROM flights "
                    + "WHERE flightnr = '" + flnr + "'"; 
            
            ResultSet srs = stmt.executeQuery(sql);
            
            String flightnr,airportCodeDep, airportCodeArr, companyName;
            double CO2, price;
            LocalDate dateOfDep,dateOfArr;
            LocalTime timeOfDep,timeOfArr;
            int distance, duration;
            if(srs.next()){
                flightnr = srs.getString("flightnr");
                dateOfDep = srs.getObject(2, LocalDate.class);
                timeOfDep = srs.getObject(3, LocalTime.class);
                dateOfArr = srs.getObject(4, LocalDate.class);
                timeOfArr = srs.getObject(5, LocalTime.class);
                airportCodeDep = srs.getString("airportCodeDep");
                airportCodeArr = srs.getString("airportCodeArr");
                companyName = srs.getString("companyName");
                CO2 = srs.getDouble("CO2");
                price = srs.getDouble("price");
                distance = srs.getInt("distance");
                duration = srs.getInt("duration");
                
            Flight vlucht = new Flight(flightnr, dateOfDep,timeOfDep, dateOfArr, timeOfArr ,airportCodeDep,airportCodeArr,CO2,price,duration,distance);
            
            return vlucht;
            }
            else{ 
                
                return null;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            
            throw new DBException(ex);
            }
    }
    
    public static Flight getFlight1(String flnr, LocalDate dateofDep) throws DBException{
        
        try{
            Statement stmt = MainProject.con.createStatement();
            String sql = "SELECT flightnr, dateOfDep, timeOfDep, dateOfArr, timeOfArr, airportCodeDep, airportCodeArr, companyName, CO2, price, distance, duration, airplaneType "
                    + "FROM flights "
                    + "WHERE flightnr = '" + flnr + "'";
                    
            
            ResultSet srs = stmt.executeQuery(sql);
            
            String flightnr, airportCodeDep, airportCodeArr, companyName, airplaneType;
            LocalDate dateOfDep, dateOfArr;
            LocalTime timeOfDep, timeOfArr;
            double CO2, price;
            int distance, duration;
            if(srs.next()){
                flightnr = srs.getString("flightnr");
                dateOfDep = srs.getObject(2, LocalDate.class);
                timeOfDep = srs.getObject(3, LocalTime.class);
                dateOfArr = srs.getObject(4, LocalDate.class);
                timeOfArr = srs.getObject(5, LocalTime.class);
                airportCodeDep = srs.getString("airportCodeDep");
                airportCodeArr = srs.getString("airportCodeArr");
                companyName = srs.getString("companyName");
                CO2 = srs.getDouble("CO2");
                price = srs.getDouble("price");
                distance = srs.getInt("distance");
                duration = srs.getInt("duration");
                airplaneType = srs.getString("airplaneType");
                
            Flight vlucht = new Flight(flightnr, dateOfDep, timeOfDep, dateOfArr, timeOfArr, airportCodeDep, airportCodeArr, companyName, CO2, price, distance, duration, airplaneType);
            
            return vlucht;
            }
            else{ 
                
                return null;
            }
            
        }
        catch(Exception ex){
            ex.printStackTrace();
            
            throw new DBException(ex);
            }
    }
}