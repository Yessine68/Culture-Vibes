/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Reservation;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ReservationService;

/**
 * FXML Controller class
 *
 * @author Ahmed El Abed
 */
public class UpdateReservationController implements Initializable {

    @FXML
    private TextField userIdTF;
    @FXML
    private TextField paiementTF;
    @FXML
    private TextField nbrTicketsTF;
    @FXML
    private TextField voyIdTF;

    private Reservation selectedReservation; // Store the selected reservation

    /**
     * Initializes the controller class.
     */
    public void initData(Reservation reservation) {
        selectedReservation = reservation;
        userIdTF.setText(String.valueOf(selectedReservation.getIduser()));
        paiementTF.setText(selectedReservation.getPaiement());
        nbrTicketsTF.setText(String.valueOf(selectedReservation.getNbrtickets()));
        voyIdTF.setText(String.valueOf(selectedReservation.getVoyage_id()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate fields with selected reservation data

    }

    // Method to initialize the selected reservation
    @FXML
    private void Update(ActionEvent event) {
        // Get updated data from text fields
        String paiement = paiementTF.getText();
        String userId = userIdTF.getText();
        String nbrTickets = nbrTicketsTF.getText();
        String VoyID = voyIdTF.getText();
        int userIdInt = Integer.parseInt(userIdTF.getText());
        int nbrTicketsInt = Integer.parseInt(nbrTicketsTF.getText());
        int VoyIDInt = Integer.parseInt(voyIdTF.getText());

        if(paiement.isEmpty()||userId.isEmpty()||nbrTickets.isEmpty()||VoyID.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }else if(!isValidString(paiement)||!isValidInt(userId)||!isValidInt(nbrTickets)||!isValidInt(VoyID)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid information.");
            alert.showAndWait();
        } else {
            // Update the selected voyage object with new data
            selectedReservation.setIduser(userIdInt);
            selectedReservation.setNbrtickets(nbrTicketsInt);
            selectedReservation.setPaiement(paiement);
            selectedReservation.setVoyage_id(VoyIDInt);

            // Call VoyageService to update the voyage in the database
            ReservationService reservationService = new ReservationService();
            try {
                reservationService.modifier(selectedReservation);
                // Optionally, display a success message
                System.out.println("Reservation updated successfully!");
                System.out.println("Reservation mis a avec succès.");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Validé");
                alert.setHeaderText(null);
                alert.setContentText("Voyage ajouté !");
                alert.showAndWait();
                Stage stage = (Stage) userIdTF.getScene().getWindow();
                stage.close();
                // Refresh the table in GestionVoyageController
                ReservationListBackController.getInstance().refreshTable();
            } catch (SQLException ex) {
                // Handle exception, e.g., display an error message
                System.out.println("Error updating Reservation: " + ex.getMessage());
            }
        }


    }

    private boolean isValidInt(String value) {
        // Check if the value is a valid integer
        return value.matches("-?\\d+");
    }

    private boolean isValidString(String name) {
        // Check if the name contains only letters and has length between 2 and 50
        return name.matches("^[a-zA-Z]{2,50}$");
    }

}
