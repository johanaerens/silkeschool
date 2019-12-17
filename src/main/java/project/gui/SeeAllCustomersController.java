package project.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.logic.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SeeAllCustomersController implements Initializable {

    private Customer customer;
    private ObservableList<Customer> customers;

    @FXML
    private TableView<Customer> tableView;
    @FXML
    private TableColumn<Customer, String> CustomerIDCol;
    @FXML
    private TableColumn<Customer, String> FirstNameCol;
    @FXML
    private TableColumn<Customer, String> LastNameCol;
    @FXML
    private TableColumn<Customer, String> DateOfBirthCol;
    @FXML
    private TableColumn<Customer, String> CountryCol;
    @FXML
    private TableColumn<Customer, String> PassPortNrCol;
    @FXML
    private Label SelectedCustomerLabel;
    @FXML
    private Button SelectAndBookBttn;

    String name;
    String firstName;
    String dateOfBirth;
    String country;
    String pasport;

    Customer klant = new Customer(name, firstName, dateOfBirth, country, pasport);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customer = Customer.getInstance();
        SelectedCustomerLabel.setText("");
        SelectAndBookBttn.setDisable(true);

        CustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        FirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        DateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        CountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        PassPortNrCol.setCellValueFactory(new PropertyValueFactory<>("pasportNR"));

        customers = FXCollections.observableArrayList(customer.getCustomers());
        tableView.setItems(customers);


        tableView.setRowFactory(tableview -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    // achtergrond normaal zetten
                    Customer rowData = row.getItem();
                    klant = rowData;
                    SelectedCustomerLabel.setText("Selected: " + rowData.getFirstName() + " " + rowData.getLastName());
                    SelectAndBookBttn.setDisable(false);
                }
            });
            return row;
        });

    }

    @FXML
    public void DoneButton(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPane.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void SelectAndBook(ActionEvent event) throws IOException {

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


}



