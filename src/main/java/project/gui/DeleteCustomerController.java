/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import project.logic.Customer;
import static project.logic.Customer.deleteCustomer;

/**
 * FXML Controller class
 *
 * @author thomasoosterlinck
 */
public class DeleteCustomerController implements Initializable {    
    private Customer customer;
    
    @FXML
    private TextField nameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private DatePicker bdayPicker;
    @FXML
    private Label labelFout;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customer = Customer.getInstance();        
    } 
    
    @FXML
    private void DoneButton(ActionEvent event) throws IOException {
        
        String name = nameField.getText();
        String firstName = firstNameField.getText();
        String dateOfBirth = bdayPicker.getValue().toString(); 
        deleteCustomer(firstName,name,dateOfBirth);  
        
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Gelukt.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();       
    }    
    
    @FXML
    private void Home(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPane.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }  
}
