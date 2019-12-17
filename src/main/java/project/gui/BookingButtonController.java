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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.db.DBException;
import project.logic.Airport;
import project.logic.Booking;
import project.logic.Customer;
import project.logic.Flight;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Gert-Jan
 */
public class BookingButtonController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private RadioButton rbPrice;
    @FXML
    private ToggleGroup radioButtonGroup;
    @FXML
    private RadioButton rbDuration;
    @FXML
    private RadioButton rbCO2;
    @FXML
    private RadioButton rbTransfers;
    @FXML
    private ChoiceBox<String> depAirportBox;
    @FXML
    private ChoiceBox<String> arrAirportBox;
    @FXML
    private TableView<Booking> table;
    @FXML
    private TableColumn<Booking, String> DepartureDateCol;
    @FXML
    private TableColumn<Booking, String> ArrivalDateCol;
    @FXML
    private TableColumn<Booking, String> PriceCol;
    @FXML
    private TableColumn<Booking, String> CO2Col;
    @FXML
    private TableColumn<Booking, String> DurationCol;
    @FXML
    private TableColumn<Booking, String> TransfersCol;
    @FXML
    private Label loadCustomerLabel;
    @FXML
    private Button FlightDetailsButton;
    @FXML
    private Button ProceedBtn;
    @FXML
    private Button ShowPossFlightsBtn;

    private ObservableList<Booking> possibleFlights;
    private ObservableList<String> depAirportsList;
    private ObservableList<String> arrAirportsList;
    private Booking boeking, loadedBooking;
    private Customer loadedCustomer, klant;
    private String vluchtnr, loadedCodeOfDep, loadedCodeOfArr;
    private ArrayList<Flight> vluchtnrs;
    double CO2, duration, price, totalDistance;
    int totalNRTransfers;
    private LocalDate dateOfDeparture, dateOfArrival, dateOfDeparture2, dateOfDeparture3;
    String depAirportCode, arrAirportCode;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbPrice.setSelected(true);
        FlightDetailsButton.setDisable(true);
        ProceedBtn.setDisable(true);
        ShowPossFlightsBtn.setDisable(true);

        Airport airport = new Airport();
        depAirportsList = FXCollections.observableArrayList(airport.getAirportNames());
        arrAirportsList = FXCollections.observableArrayList(airport.getAirportNames());
        depAirportBox.setItems(depAirportsList);
        arrAirportBox.setItems(arrAirportsList);

        DepartureDateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfDeparture"));
        ArrivalDateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfArrival"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        CO2Col.setCellValueFactory(new PropertyValueFactory<>("totaleuitstoot"));
        DurationCol.setCellValueFactory(new PropertyValueFactory<>("totalDuration"));
        TransfersCol.setCellValueFactory(new PropertyValueFactory<>("totalTransfers"));

        table.setRowFactory(tbl -> {
            TableRow<Booking> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Booking rowData = row.getItem();
                    boeking = rowData;
                    loadCustomerLabel.setText("Selected: FlightNr " + rowData.getFlightNR());
                    FlightDetailsButton.setDisable(false);
                    ProceedBtn.setDisable(false);
                }
            });
            return row;
        });
    }

    public void initCustomer(Customer customer) {
        loadedCustomer = customer;
        loadCustomerLabel.setText("Customer: " + loadedCustomer.getFirstName() + loadedCustomer.getLastName());
    }

    @FXML
    private void ProceedBooking(ActionEvent event) throws IOException, SQLException {
        vluchtnr = boeking.getFlightNR();
        loadedCodeOfDep = boeking.getCodeOfDep();
        loadedCodeOfArr = boeking.getCodeOfArr();
        CO2 = boeking.getTotaleuitstoot();
        duration = boeking.getTotalDuration();
        price = boeking.getTotalPrice();
        totalDistance = boeking.getTotalDistance();
        totalNRTransfers = boeking.getTotalTransfers();
        dateOfDeparture = boeking.getDateOfDeparture();
        dateOfArrival = boeking.getDateOfArrival();
        dateOfDeparture2 = boeking.getDateOfDeparture2();
        dateOfDeparture3 = boeking.getDateOfDeparture3();

        Booking loadedBooking = new Booking(vluchtnr, loadedCodeOfDep, loadedCodeOfArr, CO2,
                duration, price, totalDistance, totalNRTransfers, dateOfDeparture, dateOfArrival,
                dateOfDeparture2, dateOfDeparture3);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SaveBooking.fxml"));
        Parent tableViewParent = loader.load();

        SaveBookingController savebookingcontroller = loader.getController();
        savebookingcontroller.initCustomerBookingSaveBooking(loadedCustomer, loadedBooking);

        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();

    }

    @FXML
    private void FlightDetails(ActionEvent event) throws IOException, SQLException, DBException {
        vluchtnr = boeking.getFlightNR();
        loadedCodeOfDep = boeking.getCodeOfDep();
        loadedCodeOfArr = boeking.getCodeOfArr();
        CO2 = boeking.getTotaleuitstoot();
        duration = boeking.getTotalDuration();
        price = boeking.getTotalPrice();
        totalDistance = boeking.getTotalDistance();
        totalNRTransfers = boeking.getTotalTransfers();
        dateOfDeparture = boeking.getDateOfDeparture();
        dateOfArrival = boeking.getDateOfArrival();
        dateOfDeparture2 = boeking.getDateOfDeparture2();
        dateOfDeparture3 = boeking.getDateOfDeparture3();

        Booking loadedBooking = new Booking(vluchtnr, loadedCodeOfDep, loadedCodeOfArr, CO2,
                duration, price, totalDistance, totalNRTransfers, dateOfDeparture, dateOfArrival,
                dateOfDeparture2, dateOfDeparture3);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FlightDetails.fxml"));
        Parent tableViewParent = loader.load();

        FlightDetailsController flightdetailscontroller = loader.getController();
        flightdetailscontroller.initCustomerBooking(loadedCustomer, loadedBooking);

        Scene tableViewScene = new Scene(tableViewParent);
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/project/images/aroundTheGlobeGreen.png"));
        stage.setScene(tableViewScene);
        stage.show();
    }


    @FXML
    private void showPossibleFlights(ActionEvent event) throws SQLException, DBException {
        Airport airport = new Airport();
        String depAirportName = depAirportBox.getValue();
        String arrAirportName = arrAirportBox.getValue();
        LocalDate date = datePicker.getValue();
        String DateOfDep = date.toString();
        depAirportCode = airport.getAirportCodeDepName(depAirportName);
        arrAirportCode = airport.getAirportCodeDepName(arrAirportName);
        Booking boeking = new Booking(depAirportCode, arrAirportCode, date);
        table.getItems().clear();
        possibleFlights = FXCollections.observableArrayList(boeking.getPossibleBookings(date));
        table.setItems(possibleFlights);

        // automatisch sorteren table
        // kan altijd via table: arrow in kolomveld
        if (rbPrice.isSelected() == true) {
            table.getSortOrder().add(PriceCol);
        } else if (rbTransfers.isSelected() == true) {
            table.getSortOrder().add(TransfersCol);
        } else if (rbCO2.isSelected() == true) {
            table.getSortOrder().add(CO2Col);
        } else {
            table.getSortOrder().add(DurationCol);
        }
    }

    @FXML
    private void checkInputsKey(KeyEvent event) {
        if (depAirportBox.getValue() == null || arrAirportBox.getValue() == null || datePicker.getValue() == null) {
            ShowPossFlightsBtn.setDisable(true);
        } else {
            ShowPossFlightsBtn.setDisable(false);
        }

    }

    @FXML
    private void checkInputsMouse(MouseEvent event) {
        if (depAirportBox.getValue() == null || arrAirportBox.getValue() == null || datePicker.getValue() == null) {
            ShowPossFlightsBtn.setDisable(true);
        } else {
            ShowPossFlightsBtn.setDisable(false);
        }
    }

    @FXML
    public void GoHome(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainPane.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
