/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Ahmed El Abed
 */
public class UpdateVoyageController implements Initializable {

    @FXML
    private TextField titleTF;
    @FXML
    private TextField desciptionTF;
    @FXML
    private TextField durationTF;
    @FXML
    private TextField budgetTF;
    @FXML
    private TextField locationTF;
    @FXML
    private TextField nbPlacesTF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Submit(ActionEvent event) {
    }

    @FXML
    private void chooseFile(ActionEvent event) {
    }
    
}
