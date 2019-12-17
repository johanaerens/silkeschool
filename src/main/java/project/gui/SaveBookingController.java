/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import project.logic.Booking;
import project.logic.Customer;
import project.logic.OfficialBooking;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Gert-Jan
 */

public class SaveBookingController implements Initializable {

    @FXML
    private Label transfersLabel;
    @FXML
    private Label customerLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label arrivalLabel;
    @FXML
    private Label CO2Label;
    @FXML
    private Label departureLabel;
    @FXML
    private ImageView imageDep;
    @FXML
    private ImageView imageArr;
    @FXML
    private Label timeDepLabel;
    @FXML
    private Label timeArrLabel;
    @FXML
    private Label flightDate;

    private Booking loadedBooking;
    private Customer loadedCustomer;


    public void initCustomerBookingSaveBooking(Customer customer, Booking booking) throws SQLException {
        loadedCustomer = customer;
        loadedBooking = booking;

        customerLabel.setText(loadedCustomer.getFirstName() + " " + loadedCustomer.getLastName());
        flightDate.setText(loadedBooking.getDateOfDep());
        departureLabel.setText(loadedBooking.getCodeOfDep());
        arrivalLabel.setText(loadedBooking.getCodeOfArr());
        timeDepLabel.setText(loadedBooking.getDateOfDep());


        System.out.println(loadedBooking.getTotaleuitstoot());

        if (loadedBooking.getTotalTransfers() == 0) {
            transfersLabel.setText("No transfers");
        } else {
            transfersLabel.setText(loadedBooking.getTotalTransfers() + " transfers");
        }
        priceLabel.setText(String.valueOf(loadedBooking.getTotalPrice()) + " €");
        CO2Label.setText(String.valueOf(loadedBooking.getTotaleuitstoot()) + " g CO2");

        OfficialBooking b = new OfficialBooking(loadedCustomer, loadedBooking);

        System.out.println(b);
        System.out.println(b.getTotaleuitstoot());

        b.addOfficialBooking(b);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }


    @FXML
    private void Home(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPane.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void ConfirmBooking(ActionEvent event) throws IOException, SQLException {


        Parent tableViewParent = FXMLLoader.load(getClass().getResource("BookingGelukt.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();

        System.out.println("joepie");


    }

}
