package services;

import entities.Voyage;
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
public class VoyageService implements IService<Voyage> {

    Connection cnx;

    public VoyageService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouter(Voyage t) throws SQLException {
        String req = "INSERT INTO voyage(duration, budget, nbrplaces, title, description, location, voyageimage) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getDuration());
        ps.setInt(2, t.getBudget());
        ps.setInt(3, t.getNbrplaces());
        ps.setString(4, t.getTitle());
        ps.setString(5, t.getDescription());
        ps.setString(6, t.getLocation());
        ps.setString(7, t.getVoyageimage());
        ps.executeUpdate();
    }

    @Override
    public void modifier(Voyage t) throws SQLException {
        String req = "UPDATE voyage SET duration = ?, budget = ?, nbrplaces = ?, title = ?, description = ?, location = ?, voyageimage = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getDuration());
        ps.setInt(2, t.getBudget());
        ps.setInt(3, t.getNbrplaces());
        ps.setString(4, t.getTitle());
        ps.setString(5, t.getDescription());
        ps.setString(6, t.getLocation());
        ps.setString(7, t.getVoyageimage());
        ps.setInt(8, t.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(Voyage t) throws SQLException {
        String req = "DELETE FROM voyage WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getId());
        ps.executeUpdate();
    }

    @Override
    public List<Voyage> recuperer() throws SQLException {
        List<Voyage> voyages = new ArrayList<>();
        String req = "SELECT * FROM voyage";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Voyage v = new Voyage(
                    rs.getInt("id"),
                    rs.getInt("duration"),
                    rs.getInt("budget"),
                    rs.getInt("nbrplaces"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("voyageimage")
            );
            voyages.add(v);
        }
        return voyages;
    }

    public Voyage recupererById(int id) throws SQLException {
        Voyage voyage = null;
        String req = "SELECT * FROM voyage WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            voyage = new Voyage(
                    rs.getInt("id"),
                    rs.getInt("duration"),
                    rs.getInt("budget"),
                    rs.getInt("nbrplaces"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("voyageimage")
            );
        }
        return voyage;
    }
}

