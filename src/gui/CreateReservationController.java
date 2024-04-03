package gui;

import entities.Reservation;
import entities.Voyage; // Import the Voyage class
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import services.ReservationService;
import services.VoyageService; // Import the VoyageService class

public class CreateReservationController implements Initializable {

    ReservationService rs = new ReservationService();
    VoyageService vs = new VoyageService(); // Create an instance of VoyageService

    @FXML
    private TextField nbrPlacesTF;
    @FXML
    private TextField paimentTF;
    @FXML
    private TextField userTF;
    @FXML
    private ComboBox<Voyage> voyCB; // Change ComboBox type to Voyage

    private ObservableList<Voyage> voyagesList; // Define an ObservableList to hold voyages

    /**
     * Initializes the controller class.
     */
    @Override
public void initialize(URL url, ResourceBundle rb) {
    try {
        voyagesList = FXCollections.observableArrayList(vs.recuperer()); // Fetch voyages and populate list
        voyCB.setItems(voyagesList); // Set the items for the combo box
        voyCB.setValue(null); // Set the default selected value to null

        // Set the cell factory to display voyage title
        voyCB.setCellFactory(cell -> new VoyageCellFactory());

        // Set the string converter to display the title in the combo box
        voyCB.setConverter(new VoyageStringConverter());
    } catch (SQLException ex) {
        Logger.getLogger(CreateReservationController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    @FXML
    private void Submit(ActionEvent event) throws SQLException {
        String nbrPlaces = nbrPlacesTF.getText();
        String paiment = paimentTF.getText();
        String user = userTF.getText();
        int user_id = 1;
        // Get the selected voyage from the combo box
        Voyage selectedVoyage = voyCB.getSelectionModel().getSelectedItem();
        if (selectedVoyage != null) {
            int voyageId = selectedVoyage.getId(); // Access voyage ID
            int nbrPlacesInt = Integer.parseInt(nbrPlaces);

            // Create reservation with voyage ID
            Reservation res = new Reservation(voyageId, nbrPlacesInt, user_id, paiment);
            rs.ajouter(res);
            System.out.println("Ajouté");
        } else {
            // Handle case where no voyage is selected
            System.out.println("Please select a voyage");
        }
    }

    // Define a custom cell factory to display voyage title
    private static class VoyageCellFactory extends javafx.scene.control.ListCell<Voyage> {

        @Override
        protected void updateItem(Voyage voyage, boolean empty) {
            super.updateItem(voyage, empty);
            if (voyage != null) {
                setText(voyage.getTitle()); // Set the cell text to voyage title
            } else {
                setText(null);
            }
        }
    }
    private static class VoyageStringConverter extends StringConverter<Voyage> {

    @Override
    public String toString(Voyage voyage) {
        return voyage != null ? voyage.getTitle() : null; // Convert voyage to its title
    }

    @Override
    public Voyage fromString(String string) {
        // Not needed for this example
        return null;
    }
}
}
