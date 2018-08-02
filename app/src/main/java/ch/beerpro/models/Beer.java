package ch.beerpro.models;

import com.google.firebase.firestore.Exclude;

import java.util.Objects;

//@Data
//@NoArgsConstructor
public class Beer implements Entity {

    public static final String FIELD_NAME = "name";

    @Exclude
    private String id;
    private String manufacturer;
    private String name;
    private String category;
    private String photo;
    private int avgRating;
    private int numRatings;

    public Beer() {
    }

    public Beer(String id, String manufacturer, String name, String category, String photo, int avgRating,
                int numRatings) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.name = name;
        this.category = category;
        this.photo = photo;
        this.avgRating = avgRating;
        this.numRatings = numRatings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Beer beer = (Beer) o;
        return avgRating == beer.avgRating && numRatings == beer.numRatings && Objects.equals(id, beer.id) &&
                Objects.equals(manufacturer, beer.manufacturer) && Objects.equals(name, beer.name) &&
                Objects.equals(category, beer.category) && Objects.equals(photo, beer.photo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, manufacturer, name, category, photo, avgRating, numRatings);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }
}
