package gui;

import entities.Voyage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.VoyageService;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Ahmed El Abed
 */
public class AjouterVoyageController implements Initializable {

    VoyageService vs = new VoyageService();
    private File selectedFile; // Field to store the selected file

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
        String title = titleTF.getText();
        String description = desciptionTF.getText();
        String duration = durationTF.getText();
        String budget = budgetTF.getText();
        String location = locationTF.getText();
        String nbPlaces = nbPlacesTF.getText();
        String voyageImage = ""; // Initialize with an empty string for now



        if (selectedFile == null) {
            System.out.println("No file selected.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a file");
            alert.showAndWait();
        }

        voyageImage = selectedFile.toURI().toString();
        System.out.println("Selected file: " + voyageImage);
        if (title.isEmpty() || description.isEmpty() || duration.isEmpty() || budget.isEmpty() || location.isEmpty() || nbPlaces.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else if(!isValidString(title)||!isValidString(description)||!isValidInt(duration)||!isValidInt(budget)||!isValidString(location)||!isValidInt(nbPlaces)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid information.");
            alert.showAndWait();
        } else {
            try {
                int durationInt = Integer.parseInt(duration);
                int budgetInt = Integer.parseInt(budget);
                int nbPlacesInt = Integer.parseInt(nbPlaces);
                Voyage voy = new Voyage(durationInt, budgetInt, nbPlacesInt, title, description, location, voyageImage);
                vs.ajouter(voy);
                System.out.println("Voyage ajouté avec succès.");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Validé");
                alert.setHeaderText(null);
                alert.setContentText("Voyage ajouté !");
                alert.showAndWait();
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du voyage: " + e.getMessage());
                // Handle SQLException
            }
        }

    }

    @FXML
    private void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show open file dialog
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            System.out.println("Selected file: " + imagePath);
            // Now you can save the imagePath to the database or perform any other operation
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
