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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.db.DBConnector;
import project.db.DBException;
import project.logic.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author thomasoosterlinck
 */
public class MainController implements Initializable {

    private Customer customer;

    @FXML
    private AnchorPane AnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customer = Customer.getInstance();
    }

    @FXML
    void StartProgram(ActionEvent event) throws IOException, DBException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPane.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void BookingButton(ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("BookingButton.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void CustomerButton(ActionEvent event) {
        try {
            AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("CustomerButton.fxml"));
            AnchorPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void ReportButton(ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("RetrieveCustomerReport.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void SalesReportButton(ActionEvent event) {
    }

    @FXML
    private void Exit(ActionEvent event) throws DBException {
        DBConnector.closeConnection(MainProject.con);
        System.exit(0);
    }
}      
    

