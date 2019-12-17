package project.logic;

import project.db.DBException;
import project.db.DBFlight;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Booking {

    private String dateOfDep;
    private String codeOfDep;
    private String codeOfArr;
    private String bookingID;
    private ArrayList<Flight> overstappen;
    private int jaar;
    private int maand;
    private int dag;
    private LocalDate datumDB;
    private Customer klant;
    private double totalDuration;
    private String customerID;
    private LocalDate dateOfDeparture, dateOfDeparture2, dateOfDeparture3;

    private LocalDate dateOfArrival;
    private LocalDate dateOfBooking;
    private String flightnr;
    private double totalPrice;


    public static ArrayList<Flight> flights;
    public static ArrayList<Booking> possibleBookings;
    public static ArrayList<Flight> geboektevluchten;


    private double totaleuitstoot;
    private int totalTransfers;
    private double totalDistance;


    // De codeOfDep = waar vertrekt onze klant 
    // de codeOfArr = waar wil onze klant naar toe 
    // dit wil nog niet zeggen dat de boeking is opgeslaan in de database!             

    public Booking(String FlightNR, String codeOfDep, String codeOfArr, double totaleuitstoot, double duration, double price, double totalDistance, int totalNRTransfers, LocalDate dateOfDeparture, LocalDate dateOfArrival, LocalDate dateOfDeparture2, LocalDate dateOfDeparture3) {
        this.flightnr = FlightNR;
        this.codeOfDep = codeOfDep;
        this.codeOfArr = codeOfArr;
        this.totaleuitstoot = totaleuitstoot;
        this.totalDuration = duration;
        this.totalPrice = price;
        this.totalDistance = totalDistance;
        this.totalTransfers = totalNRTransfers;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.dateOfDeparture2 = dateOfDeparture2;
        this.dateOfDeparture3 = dateOfDeparture3;
    }

    public Booking(String codeOfDep, String codeOfArr, String dateOfDep) {
        this.codeOfDep = codeOfDep;
        this.codeOfArr = codeOfArr;
        this.dateOfDep = dateOfDep;
        bookingID = codeOfDep + codeOfArr + dateOfDep + klant.getCustomerID();
        jaar = Integer.parseInt(dateOfDep.substring(0, 4));
        maand = Integer.parseInt(dateOfDep.substring(5, 7));
        dag = Integer.parseInt(dateOfDep.substring(8));
        datumDB = LocalDate.of(jaar, maand, dag);
        this.klant = null;
    }

    public Booking(String bookingID, String customerID, LocalDate dateOfDeparture, LocalDate dateOfArrival, int totalTransfers, LocalDate dateOfBooking, String flightNR, double CO2Booking) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.totalTransfers = totalTransfers;
        this.dateOfBooking = dateOfBooking;
        this.flightnr = flightnr;
        this.totaleuitstoot = totaleuitstoot;
    }

    public Booking(String codeOfDep, String codeOfArr, LocalDate datumDB) {
        this.codeOfDep = codeOfDep;
        this.codeOfArr = codeOfArr;
        this.datumDB = datumDB;
    }

    public String getDateOfDep() {
        return dateOfDep;
    }

    public String getCodeOfDep() {
        return codeOfDep;
    }

    public String getCodeOfArr() {
        return codeOfArr;
    }

    public ArrayList<Flight> getOverstappen() {
        return overstappen;
    }

    public int getJaar() {
        return jaar;
    }

    public int getMaand() {
        return maand;
    }

    public int getDag() {
        return dag;
    }

    public LocalDate getDatumDB() {
        return datumDB;
    }

    public Customer getKlant() {
        return klant;
    }

    public double getTotalDuration() {
        return totalDuration;
    }

    public String getBookingID() {
        return bookingID;
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

    public double getTotaleuitstoot() {
        return totaleuitstoot;
    }


    public int getTotalTransfers() {
        return totalTransfers;
    }

    public String getFlightNR() {
        return flightnr;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public LocalDate getDateOfDeparture2() {
        return dateOfDeparture2;
    }

    public LocalDate getDateOfDeparture3() {
        return dateOfDeparture3;
    }

    private boolean vertrekGelijkAanAankomst() {
        boolean gelijk = false;
        if (codeOfDep.equals(codeOfArr))
            gelijk = true;
        return gelijk;

    }

    public ArrayList<Flight> getFlights(LocalDate date) throws SQLException {
        flights = new ArrayList();
        try {
            flights = DBFlight.getFlights(date);
        } catch (DBException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flights;
    }

    public ArrayList<Booking> getPossibleBookings(LocalDate date) throws SQLException, DBException {
        ArrayList<Flight> vluchten = new ArrayList<Flight>();
        vluchten = getFlights(date);
        possibleBookings = new ArrayList<Booking>();
        for (int i = 0; i < vluchten.size(); i++) {
            String vluchtNR;
            String codeDep;
            String codeArr;
            double uitstoot;
            double Duration;
            double Price;
            double Distance;
            int totalNRTransfers = 0;
            LocalDate dateOfDep, dateOfDep2, dateOfDep3;
            LocalDate dateOfArr;

            Flight vluchti = vluchten.get(i);
            if (vluchti.getCodeOfDep().equals(this.codeOfDep) && (vluchti.getSeats(vluchti.getFlightNR(), vluchti.getDateOfDep()) < vluchti.getMaxSeats(vluchti.getFlightNR(), vluchti.getDateOfDep()))) {

                if (vluchti.getCodeOfArr().equals(this.codeOfArr)) {

                    vluchtNR = vluchti.getFlightNR();
                    codeDep = vluchti.getCodeOfDep();
                    codeArr = vluchti.getCodeOfArr();
                    uitstoot = vluchti.getUitstoot();
                    Duration = vluchti.getDuration();
                    Price = vluchti.getPrice();
                    Distance = vluchti.getDistance();
                    dateOfDep = vluchti.getDateOfDep();
                    dateOfDep2 = vluchti.getDateOfDep();
                    dateOfDep3 = vluchti.getDateOfDep();
                    dateOfArr = vluchti.getDateOfArr();
                    Booking booking = new Booking(vluchtNR, codeDep, codeArr, uitstoot, Duration, Price, Distance, totalNRTransfers, dateOfDep, dateOfArr, dateOfDep2, dateOfDep3);
                    possibleBookings.add(booking);
                    System.out.println(vluchti.getDateOfDep());
                    System.out.println(vluchti.getMaxSeats(vluchti.getFlightNR(), vluchti.getDateOfDep()));
                    System.out.println(vluchti.getSeats(vluchti.getFlightNR(), vluchti.getDateOfDep()));
                } else {
                    for (int j = 0; j < vluchten.size(); j++) {
                        Flight vluchtj = vluchten.get(j);
                        if ((vluchti.getCodeOfArr().equals(vluchtj.getCodeOfDep())) && (vluchtj.getSeats(vluchtj.getFlightNR(), vluchtj.getDateOfDep()) < (vluchtj.getMaxSeats(vluchtj.getFlightNR(), vluchtj.getDateOfDep())))) {

                            if (vluchtj.getCodeOfArr().equals(this.codeOfArr) && (vluchtj.getDateOfDep().isAfter(vluchti.getDateOfArr()) || (vluchtj.getDateOfDep().isEqual(vluchti.getDateOfArr()) && vluchtj.getTimeOfDep().isAfter(vluchti.getTimeOfArr())))) {
                                totalNRTransfers = 1;
                                vluchtNR = vluchti.getFlightNR() + " " + vluchtj.getFlightNR();
                                codeDep = vluchti.getCodeOfDep();
                                codeArr = vluchtj.getCodeOfArr();
                                uitstoot = vluchti.getUitstoot() + vluchtj.getUitstoot();
                                Duration = vluchti.getDuration() + vluchtj.getDuration();
                                Price = vluchti.getPrice() + vluchtj.getPrice();
                                dateOfDep = vluchti.getDateOfDep();
                                dateOfDep2 = vluchti.getDateOfDep();
                                dateOfDep3 = vluchti.getDateOfDep();
                                dateOfArr = vluchtj.getDateOfArr();
                                Distance = vluchti.getDistance() + vluchtj.getDistance();
                                Booking booking = new Booking(vluchtNR, codeDep, codeArr, uitstoot, Duration, Price, Distance, totalNRTransfers, dateOfDep, dateOfArr, dateOfDep2, dateOfDep3);
                                possibleBookings.add(booking);
                            } else if (vluchtj.getDateOfDep().isAfter(vluchti.getDateOfArr()) || (vluchtj.getDateOfDep().isEqual(vluchti.getDateOfArr()) && vluchtj.getTimeOfDep().isAfter(vluchti.getTimeOfArr()))) {
                                for (int k = 0; k < vluchten.size(); k++) {
                                    Flight vluchtk = vluchten.get(k);
                                    if ((vluchtk.getSeats(vluchtk.getFlightNR(), vluchtk.getDateOfDep())) < (vluchtk.getMaxSeats(vluchtk.getFlightNR(), vluchtk.getDateOfDep()))) {
                                        if (vluchtj.getCodeOfArr().equals(vluchtk.getCodeOfDep()) && vluchtk.getCodeOfArr().equals(this.codeOfArr) && (vluchtk.getDateOfDep().isAfter(vluchtj.getDateOfArr()) || (vluchtk.getDateOfDep().isEqual(vluchtj.getDateOfArr()) && vluchtk.getTimeOfDep().isAfter(vluchtj.getTimeOfArr())))) {
                                            totalNRTransfers = 2;
                                            vluchtNR = vluchti.getFlightNR() + " " + vluchtj.getFlightNR() + " " + vluchtk.getFlightNR();
                                            codeDep = vluchti.getCodeOfDep();
                                            codeArr = vluchtk.getCodeOfArr();
                                            uitstoot = vluchti.getUitstoot() + vluchtj.getUitstoot() + vluchtk.getUitstoot();
                                            Duration = vluchti.getDuration() + vluchtj.getDuration() + vluchtk.getDuration();
                                            Price = vluchti.getPrice() + vluchtj.getPrice() + vluchtk.getPrice();
                                            Distance = vluchti.getDistance() + vluchtj.getDistance() + vluchtk.getDistance();
                                            dateOfDep = vluchti.getDateOfDep();
                                            dateOfDep2 = vluchtj.getDateOfDep();
                                            dateOfDep3 = vluchtk.getDateOfDep();
                                            dateOfArr = vluchtk.getDateOfArr();
                                            Booking booking = new Booking(vluchtNR, codeDep, codeArr, uitstoot, Duration, Price, Distance, totalNRTransfers, dateOfDep, dateOfArr, dateOfDep2, dateOfDep3);
                                            possibleBookings.add(booking);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }
        return possibleBookings;

    }

    public static ArrayList<Flight> getSeperateFlights(Booking booking) throws DBException {
        String vluchtnr1 = "0", vluchtnr2 = "0", vluchtnr3 = "0";
        Flight vlucht1, vlucht2, vlucht3;
        String vluchten;
        vluchten = booking.getFlightNR();
        geboektevluchten = new ArrayList<Flight>(3);
        vluchtnr1 = vluchten.substring(0, 6);
        vlucht1 = new Flight(vluchtnr1, booking.getDateOfDeparture());
        vlucht1 = vlucht1.getFlight1(vluchtnr1, booking.getDateOfDeparture());
        geboektevluchten.add(vlucht1);
        if (booking.getTotalTransfers() > 0) {
            vluchtnr2 = vluchten.substring(7, 13);
            vlucht2 = new Flight(vluchtnr2, booking.getDateOfDeparture2());
            geboektevluchten.add(vlucht2);
            vlucht2 = vlucht2.getFlight1(vluchtnr2, booking.getDateOfDeparture2());
            if (booking.getTotalTransfers() == 2) {
                vluchtnr3 = vluchten.substring(14, 20);
                vlucht3 = new Flight(vluchtnr3, booking.getDateOfDeparture3());
                vlucht3 = vlucht3.getFlight1(vluchtnr3, booking.getDateOfDeparture3());
                geboektevluchten.add(vlucht3);
            }
        }
        return geboektevluchten;
    }

    public static void AddSeats(Booking booking) throws DBException {
        String vluchtnr1, vluchtnr2, vluchtnr3;
        Flight vlucht1, vlucht2, vlucht3;
        String vluchten;
        vluchten = booking.getFlightNR();

        vluchtnr1 = vluchten.substring(0, 6);
        vlucht1 = new Flight(vluchtnr1, booking.getDateOfDeparture());
        vlucht1 = vlucht1.getFlight1(vluchtnr1, booking.getDateOfDeparture());
        vlucht1.SeatsPlusOne(vlucht1.getFlightNR(), vlucht1.getDateOfDep());

        if (booking.getTotalTransfers() > 0) {
            vluchtnr2 = vluchten.substring(7, 13);
            vlucht2 = new Flight(vluchtnr2, booking.getDateOfDeparture2());

            vlucht2 = vlucht2.getFlight1(vluchtnr2, booking.getDateOfDeparture2());
            vlucht2.SeatsPlusOne(vlucht2.getFlightNR(), vlucht2.getDateOfDep());
            if (booking.getTotalTransfers() == 2) {
                vluchtnr3 = vluchten.substring(14, 20);
                vlucht3 = new Flight(vluchtnr3, booking.getDateOfDeparture3());
                vlucht3 = vlucht3.getFlight1(vluchtnr3, booking.getDateOfDeparture3());
                vlucht3.SeatsPlusOne(vlucht3.getFlightNR(), vlucht3.getDateOfDep());

            }
        }
    }

}
