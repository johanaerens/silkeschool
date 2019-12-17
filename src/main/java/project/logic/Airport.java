package project.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.db.DBAirport;
import project.db.DBCustomer;
import project.db.DBException;
import project.logic.Customer;

public class Airport {
    private double Timezone;
    private String AirportCode;
    private String AirportName;
    private String APCode, APName;
    private static Airport airport = new Airport();
    public ArrayList<String> airports = new ArrayList<String>();
 
    public Airport (String AirportName,double Timezone, String AirportCode)
    {  
        this.AirportName = AirportName;
        this.Timezone= Timezone;
        this.AirportCode= AirportCode;
    }
 
    public Airport (double Timezone, String AirportCode)
    {
        this.Timezone= Timezone;
        this.AirportCode= AirportCode;
    }

    public Airport() {
        try {
            airports = DBAirport.getAirportNames();
        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Airport getInstance(){
        return airport;
    }
    
    public ArrayList<String> getAirportNames(){
        try {
            airports = DBAirport.getAirportNames();       
        } catch (DBException ex){  
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE,null,ex);            
        }
           return airports;
    } 
 
    public Boolean equals(Airport otherAirport)
    {
        if (AirportCode.equals(otherAirport.AirportCode)) {
        return true;
        } else {
        return false;
        }
    }
 
    public void MoreInfoAboutAirport()
    {
        System.out.println("De airport die u wilt weergeven ligt in de timezone" + Timezone);
        System.out.println("De airportcode van deze Airport is "+ AirportCode);
    }
 
    public double getTimezone() {
        return Timezone;
    }
    public String getAirportCode() {
        return AirportCode;
    }
    public String getAirportCodeDepName(String AirportName) {
        try {
            APCode = DBAirport.getAirportCode(AirportName);
        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return APCode;
    }
    public String getAirportNameDepCode(String AirportCode) {
        try {
            APName = DBAirport.getAirportName(AirportCode);
        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return APName;
    }
    public String getAirportName() {
        return AirportName;
    }
    public void setTimezone(double Timezone) {
        this.Timezone = Timezone;
    }
    public void setAirportCode(String AirportCode) {
        this.AirportCode = AirportCode;
    }
    public void setAirportName(String AirportName) {
        this.AirportName = AirportName;
    }
 
}