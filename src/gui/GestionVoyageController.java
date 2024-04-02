package gui;

import entities.Voyage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.VoyageService;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author Ahmed El Abed
 */
public class GestionVoyageController implements Initializable {

    @FXML
    private TableView<Voyage> tbVoyages;
    @FXML
    private TableColumn<?, ?> Cid;
    @FXML
    private TableColumn<?, ?> cTitle;
    @FXML
    private TableColumn<?, ?> cDescription;
    @FXML
    private TableColumn<?, ?> cDuration;
    @FXML
    private TableColumn<?, ?> cBudget;
    @FXML
    private TableColumn<?, ?> cLocation;
    @FXML
    private TableColumn<?, ?> cNbPlaces;
    @FXML
    private TableColumn<?, ?> cAction;

    private final MyDB myDB = MyDB.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ListeVoyages();
        } catch (SQLException ex) {
            Logger.getLogger(GestionVoyageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ListeVoyages() throws SQLException {
        VoyageService hrc = new VoyageService();

        // Initialize table columns
        Cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        cDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        cDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        cBudget.setCellValueFactory(new PropertyValueFactory<>("budget"));
        cLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        cNbPlaces.setCellValueFactory(new PropertyValueFactory<>("nbrplaces"));

        // Load voyages from the database
        List<Voyage> list = hrc.recuperer();
        ObservableList<Voyage> observableList = FXCollections.observableArrayList(list);
        tbVoyages.setItems(observableList);
    }
}
