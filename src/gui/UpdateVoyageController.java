/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Voyage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.VoyageService;

/**
 * FXML Controller class
 *
 * @author Ahmed El Abed
 */
public class UpdateVoyageController implements Initializable {

    private File selectedFile; // Field to store the selected file
    VoyageService vs = new VoyageService();


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

    private Voyage selectedVoyage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initData(Voyage voyage) {
        selectedVoyage = voyage;
        // Populate the text fields with voyage data
        titleTF.setText(selectedVoyage.getTitle());
        desciptionTF.setText(selectedVoyage.getDescription());
        durationTF.setText(String.valueOf(selectedVoyage.getDuration()));
        budgetTF.setText(String.valueOf(selectedVoyage.getBudget()));
        locationTF.setText(selectedVoyage.getLocation());
        nbPlacesTF.setText(String.valueOf(selectedVoyage.getNbrplaces()));
        // System.out.println(selectedVoyage.getId());
    }

    @FXML
    private void Update(ActionEvent event) {
        // Get updated data from text fields
        String title = titleTF.getText();
        String description = desciptionTF.getText();
        int duration = Integer.parseInt(durationTF.getText());
        int budget = Integer.parseInt(budgetTF.getText());
        String location = locationTF.getText();
        int nbPlaces = Integer.parseInt(nbPlacesTF.getText());

        // Update the selected voyage object with new data
        selectedVoyage.setTitle(title);
        selectedVoyage.setDescription(description);
        selectedVoyage.setDuration(duration);
        selectedVoyage.setBudget(budget);
        selectedVoyage.setLocation(location);
        selectedVoyage.setNbrplaces(nbPlaces);
        if (selectedFile == null) {
            System.out.println("No file selected.");
            return;
        }
        // Call VoyageService to update the voyage in the database
        VoyageService voyageService = new VoyageService();
        try {
            voyageService.modifier(selectedVoyage);
            // Close the window after successful update
            Stage stage = (Stage) titleTF.getScene().getWindow();
            stage.close();
            // Refresh the table in GestionVoyageController
            GestionVoyageController.getInstance().refreshTable();

        } catch (SQLException ex) {
            // Handle exception, e.g., display an error message
            System.out.println("Error updating voyage: " + ex.getMessage());
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
