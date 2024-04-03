package entities;

/**
 *
 * @author Ahmed El Abed
 */
public class Reservation {
    private int id, voyage_id, nbrtickets, iduser;
    private String paiement;

    public Reservation(int id, int voyage_id, int nbrtickets, int iduser, String paiement) {
        this.id = id;
        this.voyage_id = voyage_id;
        this.nbrtickets = nbrtickets;
        this.iduser = iduser;
        this.paiement = paiement;
    }

    public Reservation(int voyage_id, int nbrtickets, int iduser, String paiement) {
        this.voyage_id = voyage_id;
        this.nbrtickets = nbrtickets;
        this.iduser = iduser;
        this.paiement = paiement;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoyage_id() {
        return voyage_id;
    }

    public void setVoyage_id(int voyage_id) {
        this.voyage_id = voyage_id;
    }

    public int getNbrtickets() {
        return nbrtickets;
    }

    public void setNbrtickets(int nbrtickets) {
        this.nbrtickets = nbrtickets;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getPaiement() {
        return paiement;
    }

    public void setPaiement(String paiement) {
        this.paiement = paiement;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", voyage_id=" + voyage_id + ", nbrtickets=" + nbrtickets + ", iduser=" + iduser + ", paiement=" + paiement + '}';
    }
    
    
}
