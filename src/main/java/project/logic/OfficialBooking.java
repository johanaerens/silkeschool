package project.logic;

import project.db.DBBooking;
import project.db.DBException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OfficialBooking {
    private String CustomerID;
    private LocalDate dateOfDeparture;
    private LocalDate dateOfArrival;
    private int TotalTransfers;
    private String FlightNRs;

    private double TotalPrice;
    private double TotalDistance;
    private double TotalDuration;
    private String BookingID;
    private LocalDate dateOfBooking;
    private String codeOfDep;
    private String codeOfArr;
    private double totaleuitstoot;

    public OfficialBooking(Customer c, Booking b) {
        this.CustomerID = c.getCustomerID();
        this.FlightNRs = b.getFlightNR();
        this.dateOfDeparture = b.getDateOfDeparture();
        this.dateOfArrival = b.getDateOfArrival();
        this.TotalTransfers = b.getTotalTransfers();
        this.TotalPrice = b.getTotalPrice();
        this.TotalDistance = b.getTotalDistance();
        this.TotalDuration = b.getTotalDuration();
        this.totaleuitstoot = b.getTotaleuitstoot();
        this.dateOfBooking = LocalDate.now();
        this.BookingID = b.getCodeOfDep() + b.getCodeOfArr() + b.getDateOfDeparture() + c.getCustomerID();
        this.codeOfArr = b.getCodeOfArr();
        this.codeOfDep = b.getCodeOfDep();

    }

    public static void AddSeats(ArrayList<Flight> geboektevluchten) {
        geboektevluchten.get(0).SeatsPlusOne(geboektevluchten.get(0).getFlightNR(), geboektevluchten.get(0).getDateOfDep());
        if (geboektevluchten.size() > 1) {
            geboektevluchten.get(1).SeatsPlusOne(geboektevluchten.get(0).getFlightNR(), geboektevluchten.get(0).getDateOfDep());
            if (geboektevluchten.size() == 2)
                geboektevluchten.get(2).SeatsPlusOne(geboektevluchten.get(0).getFlightNR(), geboektevluchten.get(0).getDateOfDep());
        }
    }

    // zet de boeking in de database 
    public boolean addOfficialBooking(OfficialBooking b) throws SQLException {
        try {
            DBBooking.saveBooking(b);
        } catch (DBException ex) {
            Logger.getLogger(OfficialBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public LocalDate getDateOfArrival() {
        return dateOfArrival;
    }

    public LocalDate getDateOfBooking() {
        return dateOfBooking;
    }

    public int getTotalTransfers() {
        return TotalTransfers;
    }

    public String getFlightNRs() {
        return FlightNRs;
    }

    public double getTotaleuitstoot() {
        return totaleuitstoot;
    }


    public double getTotalPrice() {
        return TotalPrice;
    }

    public double getTotalDistance() {
        return TotalDistance;
    }

    public double getTotalDuration() {
        return TotalDuration;
    }

    public String getBookingID() {
        return BookingID;
    }

    public String getCodeOfDep() {
        return codeOfDep;
    }

    public String getCodeOfArr() {
        return codeOfArr;
    }


}