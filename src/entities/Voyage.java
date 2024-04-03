package entities;

/**
 *
 * @author Ahmed El Abed
 */
public class Voyage {
    
    private int id, duration, budget, nbrplaces;
    private String title, description , location, voyageimage;

    public Voyage(int id, int duration, int budget, int nbrplaces, String title, String description, String location, String voyageimage) {
        this.id = id;
        this.duration = duration;
        this.budget = budget;
        this.nbrplaces = nbrplaces;
        this.title = title;
        this.description = description;
        this.location = location;
        this.voyageimage = voyageimage;
    }

    public Voyage(int duration, int budget, int nbrplaces, String title, String description, String location, String voyageimage) {
        this.duration = duration;
        this.budget = budget;
        this.nbrplaces = nbrplaces;
        this.title = title;
        this.description = description;
        this.location = location;
        this.voyageimage = voyageimage;
    }

    public Voyage() {
    }

 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getNbrplaces() {
        return nbrplaces;
    }

    public void setNbrplaces(int nbrplaces) {
        this.nbrplaces = nbrplaces;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVoyageimage() {
        return voyageimage;
    }

    public void setVoyageimage(String voyageimage) {
        this.voyageimage = voyageimage;
    }

    @Override
    public String toString() {
        return "Voyage{" + "id=" + id + ", duration=" + duration + ", budget=" + budget + ", nbrplaces=" + nbrplaces + ", title=" + title + ", description=" + description + ", location=" + location + ", voyageimage=" + voyageimage + '}';
    }


    
}
