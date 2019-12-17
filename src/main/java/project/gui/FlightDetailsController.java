
package project.gui;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import project.db.DBException;
import project.logic.Airport;
import project.logic.Booking;
import project.logic.Customer;
import project.logic.Flight;

/**
 *
 * @author Gert-Jan
 */
public class FlightDetailsController {
    
    @FXML
    private Label titleLabel;
    @FXML
    private Label Label00;
    @FXML
    private Label Label01;
    @FXML
    private Label Label02;
    @FXML
    private Label Label03;
    @FXML
    private Label Label04;
    @FXML
    private Label Label05;
    @FXML
    private Label Label06;
    @FXML
    private Label Label10;
    @FXML
    private Label Label11;
    @FXML
    private Label Label12;
    @FXML
    private Label Label13;
    @FXML
    private Label Label14;
    @FXML
    private Label Label15;
    @FXML
    private Label Label20;
    @FXML
    private Label Label21;
    @FXML
    private Label Label22;
    @FXML
    private Label Label23;
    @FXML
    private Label Label24;
    @FXML
    private Label Label25;
    @FXML
    private Label Label26;
    @FXML
    private Label Label30;
    @FXML
    private Label Label31;
    @FXML
    private Label Label32;
    @FXML
    private Label Label33;
    @FXML
    private Label Label34;
    @FXML
    private Label Label35;
    @FXML
    private Label Label36;
    @FXML
    private Label Label40;
    @FXML
    private Label Label41;
    @FXML
    private Label Label42;
    @FXML
    private Label Label43;
    @FXML
    private Label Label44;
    @FXML
    private Label Label45;
    @FXML
    private Label Label46;
    @FXML
    private Label Label50;
    @FXML
    private Label Label51;
    @FXML
    private Label Label52;
    @FXML
    private Label Label53;
    @FXML
    private Label Label54;
    @FXML
    private Label Label55;
    @FXML
    private Label Label56;
    @FXML
    private Button button;
    
    private ArrayList<Flight> allFlights;
    String vluchtnr, loadedCodeOfDep, loadedCodeOfArr;
    double CO2, duration, price, totalDistance;
    int totalNRTransfers;
    LocalDate dateOfDeparture, dateOfArrival, dateOfDeparture2, dateOfDeparture3;
    Customer loadedCustomer;
    Booking loadedBooking;
    @FXML
    private Button CloseBtn;
    @FXML
    private Label Label001;

    
    public void initCustomerBooking(Customer customer, Booking booking) throws DBException {
        loadedCustomer = customer;
        loadedBooking = booking;
        
        Airport airport = new Airport();
        
        allFlights = Booking.getSeperateFlights(loadedBooking);
        loadedCodeOfDep = loadedBooking.getCodeOfDep();
        loadedCodeOfArr = loadedBooking.getCodeOfArr();
        totalNRTransfers = loadedBooking.getTotalTransfers();
        
        //set title
        String titleString;
        titleString = "Detailed Booking for your flight from "+ loadedCodeOfDep + " to " + loadedCodeOfArr + " on ";
        titleLabel.setText(titleString);
        
        //.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.UK)))
        Label05.setText("Customer: " + loadedCustomer.getFirstName() + loadedCustomer.getLastName() );
        Label35.setText("Booking: " + loadedBooking.getDateOfArrival() );
        
        
        Flight vlucht1 = allFlights.get(0);
        Label00.setText(vlucht1.getFlightNR());
        Label10.setText(vlucht1.getTimeOfDep().toString());
        Label20.setText(vlucht1.getTimeOfArr().toString());
        Label30.setText(String.valueOf(vlucht1.getDuration()));
        Label40.setText(String.valueOf(vlucht1.getPrice()) + " €");
        Label50.setText(String.valueOf(vlucht1.getUitstoot()) + " g");
        Label11.setText(airport.getAirportNameDepCode(vlucht1.getCodeOfDep()) + " (" + vlucht1.getCodeOfDep() +")");
        Label21.setText(airport.getAirportNameDepCode(vlucht1.getCodeOfArr()) + " (" + vlucht1.getCodeOfArr() +")");
        //geen duration tussen vluchten: Label31 voorlopig leeg

        
        
        if (totalNRTransfers == 0) {
            // sommatie onderaan van alle vluchten
            
        } else if (totalNRTransfers == 1) {
            
            Flight vlucht2 = allFlights.get(1);
            //String duration1 = Duration.between(vlucht2.getTimeOfArr(), vlucht1.getTimeOfArr() ).toString();  
            Label31.setText("Time spent in " + vlucht1.getCodeOfArr() + " " + "duration1");
            Label02.setText(vlucht2.getFlightNR());
            Label12.setText(vlucht2.getTimeOfDep().toString());
            Label22.setText(vlucht2.getTimeOfArr().toString());
            Label32.setText(String.valueOf(vlucht2.getDuration()));
            Label42.setText(String.valueOf(vlucht2.getPrice()) + " €");
            Label52.setText(String.valueOf(vlucht2.getUitstoot()) + " g");
            Label12.setText(airport.getAirportNameDepCode(vlucht2.getCodeOfDep()) + " (" + vlucht2.getCodeOfDep() +")");
            Label22.setText(airport.getAirportNameDepCode(vlucht2.getCodeOfArr()) + " (" + vlucht2.getCodeOfArr() +")");        

            
            
        } else if (totalNRTransfers == 2) {
//            Flight vlucht2 = allFlights.get(1);
//            System.out.println(vlucht2);
//            Flight vlucht3 = allFlights.get(2);
//            System.out.println(vlucht3);
//            //String duration1 = Duration.between(vlucht2.getTimeOfArr(), vlucht1.getTimeOfArr() ).toString();  
//            Label31.setText("Time spent in " + vlucht1.getCodeOfArr() + "  \n" + "duration1");
//            Label02.setText(vlucht2.getFlightNR());
//            Label12.setText(vlucht2.getTimeOfDep().toString());
//            Label22.setText(vlucht2.getTimeOfArr().toString());
//            Label32.setText(String.valueOf(vlucht2.getDuration()));
//            Label42.setText(String.valueOf(vlucht2.getPrice()) + " €");
//            Label52.setText(String.valueOf(vlucht2.getCO2()) + " g");
//            Label12.setText(airport.getAirportNameDepCode(vlucht2.getCodeOfDep()) + " (" + vlucht2.getCodeOfDep() +")");
//            Label22.setText(airport.getAirportNameDepCode(vlucht2.getCodeOfArr()) + " (" + vlucht2.getCodeOfArr() +")");
//            
        } else {
            System.out.println("Impossible number of transfers!");
        }
    }   

    @FXML
    public void Close(ActionEvent event) throws IOException{
        Stage stage = (Stage) CloseBtn.getScene().getWindow();
        stage.close();
    }
}
