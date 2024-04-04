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
        int userId = Integer.parseInt(userIdTF.getText());
        int nbrTickets = Integer.parseInt(nbrTicketsTF.getText());
        int VoyID = Integer.parseInt(voyIdTF.getText());

        // Update the selected voyage object with new data
        selectedReservation.setIduser(userId);
        selectedReservation.setNbrtickets(nbrTickets);
        selectedReservation.setPaiement(paiement);
        selectedReservation.setVoyage_id(VoyID);

        // Call VoyageService to update the voyage in the database
        ReservationService reservationService = new ReservationService();
        try {
            reservationService.modifier(selectedReservation);
            // Optionally, display a success message
            System.out.println("Reservation updated successfully!");
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
