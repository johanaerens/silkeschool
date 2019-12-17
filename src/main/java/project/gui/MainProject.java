/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import project.db.DBConnector;
import project.db.DBException;

/**
 *
 * @author thomasoosterlinck
 */
public class MainProject extends Application {
    public static Connection con = null;
     
    @Override
    public void start(Stage stage) throws Exception {
        try {
            con = DBConnector.getConnection();
        } catch (DBException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Model set");
        Parent root = FXMLLoader.load(getClass().getResource("WelkomScene.fxml"));        
        Scene scene = new Scene(root);  
        stage.getIcons().add(new Image("/project/images/aroundTheGlobeGreen.png"));
        stage.setTitle("CleanFlight");
        stage.setScene(scene);
        stage.show();        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
