/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import project.logic.Customer;

import java.io.IOException;

/**
 * @author Gert-Jan
 */
public class CustomerReportController {

    @FXML
    private Label CustomerLabel;

    private Customer loadedCustomer;

    public void initCustomer(Customer customer) {
        loadedCustomer = customer;
        CustomerLabel.setText(loadedCustomer.getFirstName() + " " + loadedCustomer.getLastName());
    }

    @FXML
    private void Home(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPane.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

}
