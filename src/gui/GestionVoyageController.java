package gui;

import entities.Voyage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.VoyageService;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author Ahmed El Abed
 */
public class GestionVoyageController implements Initializable {

    private static GestionVoyageController instance;


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
    void goToReservation(ActionEvent event) {
        try {
            // Load GestionVoyage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservationListBack.fxml"));
            Parent root = loader.load();

            // Get the stage from the event source
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Create a new scene with GestionVoyage.fxml content and set it to the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void GoToAjout(ActionEvent event) {
        try {
            // Load GestionVoyage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterVoyage.fxml"));
            Parent root = loader.load();

            // Get the stage from the event source
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Create a new scene with GestionVoyage.fxml content and set it to the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final MyDB myDB = MyDB.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;

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
        boolean ModifyColumnExists = false;

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
                            alert.setTitle("Delete Voyage");
                            alert.setHeaderText("Are you sure you want to delete this Voyage?");
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

       if (!ModifyColumnExists) {
    TableColumn<Voyage, Void> modifyColumn = new TableColumn<>("Update");
    modifyColumn.setCellFactory(column -> {
        return new TableCell<Voyage, Void>() {
            private final Button modifyButton = new Button("Modify");

            {
                modifyButton.setOnAction(event -> {
                    Voyage selectedVoyage = getTableView().getItems().get(getIndex());
                    // Navigate to updateVoyage.fxml with the selected voyage
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateVoyage.fxml"));
                    Parent root;
                    try {
                        root = loader.load();
                        UpdateVoyageController controller = loader.getController();
                        controller.initData(selectedVoyage);
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(GestionVoyageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
                }
            }
        };
    });

    tbVoyages.getColumns().add(modifyColumn);
}


        // Load voyages from the database
        List<Voyage> list = hrc.recuperer();
        ObservableList<Voyage> observableList = FXCollections.observableArrayList(list);
        tbVoyages.setItems(observableList);
    }

    public void refreshTable() {
        try {
            voyagesList = new VoyageService().recuperer();
            tbVoyages.setItems(FXCollections.observableArrayList(voyagesList));
        } catch (SQLException ex) {
            Logger.getLogger(GestionVoyageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static GestionVoyageController getInstance() {
        return instance;
    }




}


