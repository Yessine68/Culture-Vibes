package gui;

import entities.Voyage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
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

        private List<Voyage> voyagesList;

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
        
        boolean deleteColumnExists = false;
        boolean blockColumnExists = false;

        for (TableColumn column : tbVoyages.getColumns()) {
            if (column.getText().equals("Action")) {
                deleteColumnExists = true;
                break;
            }
        }
        
                if (!deleteColumnExists) {
            TableColumn<Voyage, Void> deleteColumn = new TableColumn<>("Action");
            deleteColumn.setCellFactory(column -> {
                return new TableCell<Voyage, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            Voyage u = getTableView().getItems().get(getIndex());
                            VoyageService us = new VoyageService();
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Delete Account");
                            alert.setHeaderText("Are you sure you want to delete this account?");
                            alert.setContentText("This action cannot be undone.");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    us.aaaa(u.getId());

                                    refreshTable();
                                } catch (SQLException ex) {
                                    Logger.getLogger(GestionVoyageController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                // deletion failed, display an error message
                                /*Alert errorAlert = new Alert(AlertType.ERROR);
                                errorAlert.setTitle("Deletion Error");
                                errorAlert.setHeaderText(null);
                                errorAlert.setContentText("Unable to delete User".");
                                errorAlert.showAndWait();*/
                                alert.close();
                            }

                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            });

            tbVoyages.getColumns().add(deleteColumn);
        }

        // Load voyages from the database
        List<Voyage> list = hrc.recuperer();
        ObservableList<Voyage> observableList = FXCollections.observableArrayList(list);
        tbVoyages.setItems(observableList);
    }
    
        private void refreshTable() {
        try {
            voyagesList = new VoyageService().recuperer();
            tbVoyages.setItems(FXCollections.observableArrayList(voyagesList));
        } catch (SQLException ex) {
            Logger.getLogger(GestionVoyageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
