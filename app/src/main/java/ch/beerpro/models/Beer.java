package ch.beerpro.models;

import java.util.Objects;

public class Beer {
    public String id;
    public String manufacturer;
    public String name;
    public String category;
    public String photo;
    public int avgRating;
    public int numRatings;

    public Beer() {
    } // Needed for Firebase
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

    @Override
    public String toString() {
        return "Beer{" + "id='" + id + '\'' + ", manufacturer='" + manufacturer + '\'' + ", name='" + name + '\'' +
                ", category='" + category + '\'' + ", avgRating=" + avgRating + ", numRatings=" + numRatings + '}';
    }
}
