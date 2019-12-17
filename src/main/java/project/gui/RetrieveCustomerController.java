/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.db.DBException;
import project.logic.Customer;

/**
 * FXML Controller class
 *
 * @author thomasoosterlinck
 */
public class RetrieveCustomerController implements Initializable {
     
    @FXML
    private TextField nameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private DatePicker bdayPicker;
    @FXML
    private Label labelFout;
    @FXML
    private Label customerFoundlabel;
    @FXML
    private Label dataLabel;
    @FXML
    private Label showDataLabel;
    
    private Customer customer;
    Customer klant;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customer = Customer.getInstance();        
    }  
    
    @FXML
    private void CheckButton(ActionEvent event) {
        try {
            labelFout.setText("");
            customerFoundlabel.setText("");
            String name = nameField.getText();
            String firstName = firstNameField.getText();
            String dateOfBirth = bdayPicker.getValue().toString();
            klant = new Customer(firstName, name, dateOfBirth);
            
            if(klant.bestaatDeCustomer(klant)){
                customerFoundlabel.setText("Customer found!");  
                dataLabel.setText("Data customer " + firstName + " " + name);
                showDataLabel.setText(Customer.getCustomer(firstName, name, dateOfBirth).toString());                
            }
            else
                labelFout.setText("Error! Customer  not found, go back");
        } catch (DBException ex) {
            Logger.getLogger(ModifyCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    @FXML
    private void HomeButton(ActionEvent event) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPane.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
 
}
