package ch.beerpro.models;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.Objects;

public class Rating implements Entity {
    public static final String COLLECTION = "ratings";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_LIKES = "likes";
    public static final String FIELD_CREATION_DATE = "creationDate";

    @Exclude
    private String id;
    private String beerId;
    private String userId;
    private String photo;
    private float rating;
    private String comment;
    private int likes;
    private Date creationDate;

    public Rating(String id, String beerId, String userId, String photo, float rating, String comment, int likes,
                  Date creationDate) {
        this.id = id;
        this.beerId = beerId;
        this.userId = userId;
        this.photo = photo;
        this.rating = rating;
        this.comment = comment;
        this.likes = likes;
        this.creationDate = creationDate;
    }

    public Rating() {

    }

    @Override

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Rating rating1 = (Rating) o;
        return Float.compare(rating1.rating, rating) == 0 && likes == rating1.likes && Objects.equals(id, rating1.id) &&
                Objects.equals(beerId, rating1.beerId) && Objects.equals(userId, rating1.userId) &&
                Objects.equals(photo, rating1.photo) && Objects.equals(comment, rating1.comment) &&
                Objects.equals(creationDate, rating1.creationDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, beerId, userId, photo, rating, comment, likes, creationDate);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getBeerId() {
        return beerId;
    }

    public void setBeerId(String beerId) {
        this.beerId = beerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
