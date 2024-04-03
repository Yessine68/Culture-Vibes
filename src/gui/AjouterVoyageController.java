package gui;

import entities.Voyage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import services.VoyageService;

/**
 * FXML Controller class
 *
 * @author Ahmed El Abed
 */
public class AjouterVoyageController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Submit(ActionEvent event) throws SQLException, IOException {
        String title = titleTF.getText();
        String description = desciptionTF.getText();
        String duration = durationTF.getText();
        String budget = budgetTF.getText();
        String location = locationTF.getText();
        String nbPlaces = nbPlacesTF.getText();
        String voyageimage = "test.png";
        int durationInt = Integer.parseInt(duration);
        int budgetInt = Integer.parseInt(budget);
        int nbPlacesInt = Integer.parseInt(nbPlaces);

        Voyage voy = new Voyage(durationInt, budgetInt, nbPlacesInt, title, description, location, voyageimage);

        vs.ajouter(voy);
        System.out.println("Ajouté");
        /*        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Login.fxml"));
        Parent parent = loader.load();
        LoginController controller = loader.getController();
        controller.setUser(voy);
        usernameTF.getScene().setRoot(parent); */
    }

    @FXML
    private void chooseFile(ActionEvent event) {
    }

}
