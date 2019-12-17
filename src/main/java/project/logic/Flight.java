package project.logic;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.db.DBException;
import project.db.DBFlight;

public class Flight {
    private String vluchtNR;
    private LocalDate datum;
    private String flightNR;
    private LocalDate dateOfDep;
    private LocalTime timeOfDep;
    private LocalDate dateOfArr;
    private LocalTime timeOfArr;
    private String airportCodeDep;
    private String airportCodeArr;
    private String companyName;
    
    private double price;
    private double distance;
    private double duration;
    private String airPlaneType;
    private double uitstoot;
    
    public Flight(String nummer, LocalDate dateOfDep,LocalTime timeOfDep,
            LocalDate dateOfArr,LocalTime timeOfArr,String codeOfDep,
            String codeOfArr,double uitstoot,double price,double duration,double distance)
    {    
        this.vluchtNR = nummer;
        this.dateOfArr = dateOfArr;
        this.dateOfDep = dateOfDep;
        this.airportCodeDep= codeOfDep;
        this.airportCodeArr = codeOfArr;
        this.uitstoot = uitstoot;
        this.price=price;
        this.duration = duration;
        this.distance = distance;
        this.timeOfDep=timeOfDep;
        this.timeOfArr=timeOfArr; 
    }
    
    public Flight (String flightNR,LocalDate dateOfDep,LocalTime timeOfDep,
            LocalDate dateOfArr, LocalTime timeOfArr,String airportCodeDep,
            String airportCodeArr,String companyName,double uitstoot,double price,
            double distance,double duration,String airplaneType)
    {
        this.flightNR=flightNR;
        this.dateOfDep=dateOfDep;
        this.timeOfDep=timeOfDep;
        this.dateOfArr=dateOfArr;
        this.timeOfArr=timeOfArr;
        this.airportCodeDep=airportCodeDep;
        this.airportCodeArr=airportCodeArr;
        this.companyName=companyName;
        this.uitstoot = uitstoot;
        this.price=price;
        this.distance=distance;
        this.duration=duration;
        this.airPlaneType=airPlaneType;       
    }
    
    public Flight(String flightNR,LocalDate dateOfDep){
    this.flightNR=flightNR;
    this.dateOfDep = dateOfDep;
    }
    
    public Flight(String flightNR){
        this.flightNR=flightNR;
    }    
    public String getVluchtNR() {
        return vluchtNR;
    }
    public LocalDate getDatum() {
        return datum;
    }
    public String getFlightNR() {
        return flightNR;
    }
    public LocalDate getDateOfDep() {
        return dateOfDep;
    }
    public LocalTime getTimeOfDep() {
        return timeOfDep;
    }
    public LocalDate getDateOfArr() {
        return dateOfArr;
    }
    public LocalTime getTimeOfArr() {
        return timeOfArr;
    }
    public String getCodeOfDep() {
        return airportCodeDep;
    }
    public String getCodeOfArr() {
        return airportCodeArr;
    }
    public String getCompanyName() {
        return companyName;
    }

    public double getUitstoot() {
        return uitstoot;
    }

    public double getPrice() {
        return price;
    }
    public double getDistance() {
        return distance;
    }
    public double getDuration() {
        return duration;
    }
    public String getAirPlaneType() {
        return airPlaneType;
    }
    
    public static int getSeats (String vluchtNR,LocalDate dateOfDep){      
           
         int aantalSeats=0;
          try {
              aantalSeats = DBFlight.getSeats(vluchtNR,dateOfDep);
              
          } catch (DBException ex) {
              Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
          }
         return aantalSeats;
      }
      
    public static int getMaxSeats (String vluchtNR,LocalDate dateOfDep){      
       int maxAantalSeats=0;  

        try {
          maxAantalSeats = DBFlight.getMaxSeats(vluchtNR,dateOfDep);

        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }

       return maxAantalSeats;
    }

    public static void SeatsPlusOne (String vluchtNR,LocalDate dateOfDep){      

        try {
            DBFlight.seatsPlusOne(vluchtNR,dateOfDep);

        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
       
     public static Flight getFlight1 (String flightNR,LocalDate dateOfDep) throws DBException{ 
        Flight vlucht = new Flight(flightNR, dateOfDep);     
        
        vlucht = DBFlight.getFlight1(flightNR, dateOfDep);        
        return vlucht;        
   }
        
}   

    
