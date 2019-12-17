/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import project.logic.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author thomasoosterlinck
 */
public class AddNewCustomerController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private DatePicker bdayPicker;
    @FXML
    private TextField passportField;
    @FXML
    private ComboBox<String> CountryBox;
    @FXML
    private Button AddAndBookBtn;
    @FXML
    private Button addCustomer;

    private Customer customer;
    private ObservableList<String> countryList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Customer.getInstance();
        addCustomer.setDisable(true);
        AddAndBookBtn.setDisable(true);
        countryList = FXCollections.observableArrayList(Customer.getAllCountries());
        CountryBox.setItems(countryList);
    }

    public static class HideableItem<T> {
        private final ObjectProperty<T> object = new SimpleObjectProperty<>();
        private final BooleanProperty hidden = new SimpleBooleanProperty();

        private HideableItem(T object) {
            setObject(object);
        }

        private ObjectProperty<T> objectProperty() {
            return this.object;
        }

        private T getObject() {
            return this.objectProperty().get();
        }

        private void setObject(T object) {
            this.objectProperty().set(object);
        }

        private BooleanProperty hiddenProperty() {
            return this.hidden;
        }

        private boolean isHidden() {
            return this.hiddenProperty().get();
        }

        private void setHidden(boolean hidden) {
            this.hiddenProperty().set(hidden);
        }

        @Override
        public String toString() {
            return getObject() == null ? null : getObject().toString();
        }
    }

    @FXML
    private void HomeScreen(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPane.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void AddCustomer(ActionEvent event) {
        try {
            String name = nameField.getText();
            String firstName = firstNameField.getText();
            String dateOfBirth = bdayPicker.getValue().toString();
            String country = CountryBox.getValue();
            String pasport = passportField.getText();
            Customer klant = new Customer(firstName, name, dateOfBirth, country, pasport);
            klant.addCustomer(klant);

            Parent tableViewParent = FXMLLoader.load(getClass().getResource("Gelukt.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(AddNewCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void AddAndBook(ActionEvent event) throws IOException {
        String firstName = firstNameField.getText();
        String name = nameField.getText();
        String dateOfBirth = bdayPicker.getValue().toString();
        String country = CountryBox.getValue();
        String pasport = passportField.getText();
        Customer klant = new Customer(name, firstName, dateOfBirth, country, pasport);
        klant.addCustomer(klant);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BookingButton.fxml"));
        Parent tableViewParent = loader.load();
        BookingButtonController bookingbuttoncontroller = loader.getController();
        bookingbuttoncontroller.initCustomer(klant);

        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();

    }

    @FXML
    private void checkInputs(KeyEvent event) {

        if (nameField.getText().isEmpty() || firstNameField.getText().isEmpty() ||
                bdayPicker.getValue() == null || passportField.getText().isEmpty() ||
                CountryBox.getValue().isEmpty()) {
            addCustomer.setDisable(true);
            AddAndBookBtn.setDisable(true);
        } else {
            addCustomer.setDisable(false);
            AddAndBookBtn.setDisable(false);
        }

    }

}
