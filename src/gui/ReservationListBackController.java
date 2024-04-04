/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Reservation;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ReservationService;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author Ahmed El Abed
 */
public class ReservationListBackController implements Initializable {

    private static ReservationListBackController instance;


    @FXML
    private TableColumn<?, ?> Cid;
    @FXML
    private TableColumn<?, ?> cNbTickets;
    @FXML
    private TableColumn<?, ?> cUserId;
    @FXML
    private TableColumn<?, ?> cPayment;
    @FXML
    private TableColumn<?, ?> cVoyage;

    private final MyDB myDB = MyDB.getInstance();
    @FXML
    private TableView<Reservation> tbReservations;


    @FXML
    void goToVoy(ActionEvent event) {
        try {
            // Load GestionVoyage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionVoyage.fxml"));
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;

        try {
            ListeReservation();
        } catch (SQLException ex) {
            Logger.getLogger(GestionVoyageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Reservation> reservationList;

    public void ListeReservation() throws SQLException {
        ReservationService hrc = new ReservationService();

        // Initialize table columns
        Cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cNbTickets.setCellValueFactory(new PropertyValueFactory<>("nbrtickets"));
        cUserId.setCellValueFactory(new PropertyValueFactory<>("iduser"));
        cPayment.setCellValueFactory(new PropertyValueFactory<>("paiement"));
        cVoyage.setCellValueFactory(new PropertyValueFactory<>("voyage_id"));

        boolean deleteColumnExists = false;
        boolean ModifyColumnExists = false;

        for (TableColumn column : tbReservations.getColumns()) {
            if (column.getText().equals("Action")) {
                deleteColumnExists = true;
                break;
            }
        }

        if (!deleteColumnExists) {
            TableColumn<Reservation, Void> deleteColumn = new TableColumn<>("Action");
            deleteColumn.setCellFactory(column -> {
                return new TableCell<Reservation, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            Reservation u = getTableView().getItems().get(getIndex());
                            ReservationService us = new ReservationService();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete reservation");
                            alert.setHeaderText("Are you sure you want to delete this reservation?");
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

            tbReservations.getColumns().add(deleteColumn);
        }

        if (!ModifyColumnExists) {
            TableColumn<Reservation, Void> modifyColumn = new TableColumn<>("Update");
            modifyColumn.setCellFactory(column -> {
                return new TableCell<Reservation, Void>() {
                    private final Button modifyButton = new Button("Modify");

                    {
                        modifyButton.setOnAction(event -> {
                            Reservation selectedReservation = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateReservation.fxml"));
                            Parent root;
                            try {
                                root = loader.load();
                                UpdateReservationController controller = loader.getController();
                                controller.initData(selectedReservation); // Initialize selected reservation
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

            tbReservations.getColumns().add(modifyColumn);
        }

        // Load voyages from the database
        List<Reservation> list = hrc.recuperer();
        ObservableList<Reservation> observableList = FXCollections.observableArrayList(list);
        tbReservations.setItems(observableList);
    }

    public void refreshTable() {
        try {
            reservationList = new ReservationService().recuperer();
            tbReservations.setItems(FXCollections.observableArrayList(reservationList));
        } catch (SQLException ex) {
            Logger.getLogger(GestionVoyageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ReservationListBackController getInstance() {
        return instance;
    }

}
