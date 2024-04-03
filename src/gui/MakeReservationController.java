package gui;

import entities.Voyage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.VoyageService;

/**
 * FXML Controller class
 *
 * @author Ahmed El Abed
 */

public class MakeReservationController implements Initializable {

    @FXML
    private ImageView VoyImageView;
    @FXML
    private Label Title;

    private final VoyageService voyageService = new VoyageService();
    @FXML
    private Label daysLB;
    @FXML
    private Label locationLB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Fetch the voyage with ID 15
            Voyage voyage = voyageService.recupererById(16);
            if (voyage != null) {
                // Set the title
                Title.setText(voyage.getTitle());
                
                // Load and set the image
                String imagePath = voyage.getVoyageimage();
                Image image = new Image(imagePath);
                VoyImageView.setImage(image);
            } else {
                // Handle case where no voyage with ID 15 is found
                Title.setText("No voyage found");
            }
        } catch (SQLException ex) {
            // Handle SQL exception
            ex.printStackTrace();
        }
    }

    @FXML
    private void MakeReservation(ActionEvent event) {
    }
}
