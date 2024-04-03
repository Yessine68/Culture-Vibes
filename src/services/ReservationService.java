package services;

import entities.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyDB;


/**
 *
 * @author Ahmed El Abed
 */
public class ReservationService implements IService<Reservation> {

    Connection cnx;

    public ReservationService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouter(Reservation t) throws SQLException {
        String req = "INSERT INTO reservation(voyage_id, nbrtickets, iduser, paiement) "
                + "VALUES(?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getVoyage_id());
        ps.setInt(2, t.getNbrtickets());
        ps.setInt(3, t.getIduser());
        ps.setString(4, t.getPaiement());
        ps.executeUpdate();
    }

    @Override
    public void modifier(Reservation t) throws SQLException {
        String req = "UPDATE reservation SET voyage_id = ?, nbrtickets = ?, iduser = ?, paiement = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getVoyage_id());
        ps.setInt(2, t.getNbrtickets());
        ps.setInt(3, t.getIduser());
        ps.setString(4, t.getPaiement());
        ps.setInt(5, t.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(Reservation t) throws SQLException {
        String req = "DELETE FROM reservation WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getId());
        ps.executeUpdate();
    }

    @Override
    public List<Reservation> recuperer() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Reservation r = new Reservation(
                    rs.getInt("id"),
                    rs.getInt("voyage_id"),
                    rs.getInt("nbrtickets"),
                    rs.getInt("iduser"),
                    rs.getString("paiement")
            );
            reservations.add(r);
        }
        return reservations;
    }

    public Reservation recupererById(int id) throws SQLException {
        Reservation reservation = null;
        String req = "SELECT * FROM reservation WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            reservation = new Reservation(
                    rs.getInt("id"),
                    rs.getInt("voyage_id"),
                    rs.getInt("nbrtickets"),
                    rs.getInt("iduser"),
                    rs.getString("paiement")
            );
        }
        return reservation;
    }
    
    
        public void aaaa(int id) throws SQLException {
        String query = "DELETE FROM reservation WHERE id = ?";
        PreparedStatement stmt = cnx.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
