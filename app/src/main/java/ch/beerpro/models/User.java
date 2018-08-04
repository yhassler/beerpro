package ch.beerpro.models;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.Objects;

public class User implements Entity {
    public static final String COLLECTION = "users";
    public static final String FIELD_ID = "id";

    @Exclude
    private String id;
    private String name;
    private String photo;

    public User() {
    }

    public User(String id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(photo, user.photo) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, photo, name);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
