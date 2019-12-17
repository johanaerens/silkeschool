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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.db.DBException;
import project.logic.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author thomasoosterlinck
 */
public class ModifyCustomerController implements Initializable {
    private Customer customer;

    @FXML
    private TextField nameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private DatePicker bdayPicker;
    @FXML
    private Label labelFout;
    @FXML
    private Label labelFound;
    @FXML
    private TextField ChangeName;
    @FXML
    private TextField ChangeFirstName;
    @FXML
    private TextField ChangeCountry;
    @FXML
    private TextField ChangePassport;
    @FXML
    private Button CheckBtn;
    @FXML
    private Button ModifyBookBtn;
    @FXML
    private Button ModifyBtn;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customer = Customer.getInstance();
        CheckBtn.setDisable(true);
        ModifyBtn.setDisable(true);
        ModifyBookBtn.setDisable(true);
    }

    Customer klant;

    @FXML
    private void CheckCustomer(ActionEvent event) throws IOException {

        try {
            labelFound.setText("");
            labelFout.setText("");
            String name = nameField.getText();
            String firstName = firstNameField.getText();
            String dateOfBirth = bdayPicker.getValue().toString();
            klant = new Customer(firstName, name, dateOfBirth);

            if (klant.bestaatDeCustomer(klant)) {
                labelFound.setText("Customer found!");
            } else
                labelFout.setText("Fout! Customer niet gevonden, go back");
        } catch (DBException ex) {
            Logger.getLogger(ModifyCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Modify(ActionEvent event) throws DBException, SQLException {
        try {
            String name = ChangeName.getText();
            String firstName = ChangeFirstName.getText();
            String changeCountry = ChangeCountry.getText();
            String passport = ChangePassport.getText();


            if (!(name.equals(""))) {
                klant.changeCustomerLastName(name);
            }
            if (!(firstName.equals("")))
                klant.changeCustomerFirstName(firstName);
            if (!(changeCountry.equals("")))
                klant.changeCountry(changeCountry);
            if (!(passport.equals("")))
                klant.changePaspoort(passport);

            Parent tableViewParent = FXMLLoader.load(getClass().getResource("Gelukt.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(ModifyCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void GoHome(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPane.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();

    }

    @FXML
    private void ModifyAndBook(ActionEvent event) throws IOException {
        String name = nameField.getText();
        String firstName = firstNameField.getText();
        String dateOfBirth = bdayPicker.getValue().toString();
        String country = klant.getCountry();
        String pasport = klant.getPasportNR();
        Customer klant = new Customer(name, firstName, dateOfBirth, country, pasport);
        klant.addCustomer(klant);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BookingButton.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);
        BookingButtonController controller = loader.getController();

//controller.initCustomerData(klant);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();

    }

    @FXML
    private void checkInputsKey(KeyEvent event) {

        if (nameField.getText().isEmpty() || firstNameField.getText().isEmpty() || bdayPicker.getValue() == null) {
            CheckBtn.setDisable(true);
        } else {
            CheckBtn.setDisable(false);
        }

    }

    @FXML
    private void checkInputsMouse(MouseEvent event) {

        if (nameField.getText().isEmpty() || firstNameField.getText().isEmpty() || bdayPicker.getValue() == null) {
            CheckBtn.setDisable(true);
        } else {
            CheckBtn.setDisable(false);
        }

    }

    @FXML
    private void checkModifyKey(KeyEvent event) {

        if (!ChangeName.getText().isEmpty() || !ChangeFirstName.getText().isEmpty() || !ChangeCountry.getText().isEmpty() || !ChangePassport.getText().isEmpty()) {
            CheckBtn.setDisable(false);
            ModifyBtn.setDisable(false);
            ModifyBookBtn.setDisable(false);
        } else {
            CheckBtn.setDisable(true);
            ModifyBtn.setDisable(true);
            ModifyBookBtn.setDisable(true);
        }

    }


}
