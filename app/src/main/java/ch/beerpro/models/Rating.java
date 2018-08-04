package ch.beerpro.models;

import com.google.firebase.firestore.Exclude;

import java.util.*;

public class Rating implements Entity {
    public static final String COLLECTION = "ratings";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_LIKES = "likes";
    public static final String FIELD_CREATION_DATE = "creationDate";

    @Exclude
    private String id;
    private String beerId;
    private String beerName;
    private String userId;
    private String userName;
    private String userPhoto;
    private String photo;
    private float rating;
    private String comment;
    private Map<String, Boolean> likes;
    private Date creationDate;

    public Rating(String beerId, String beerName, String userId, String userName, String userPhoto, String photo,
                  float rating, String comment, Date creationDate) {
        this.beerId = beerId;
        this.beerName = beerName;
        this.userId = userId;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.photo = photo;
        this.rating = rating;
        this.comment = comment;
        this.likes = new HashMap<>();
        this.creationDate = creationDate;
    }

    public Rating() {

    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Rating rating1 = (Rating) o;
        return Float.compare(rating1.rating, rating) == 0 && Objects.equals(id, rating1.id) &&
                Objects.equals(beerId, rating1.beerId) && Objects.equals(beerName, rating1.beerName) &&
                Objects.equals(userId, rating1.userId) && Objects.equals(userName, rating1.userName) &&
                Objects.equals(userPhoto, rating1.userPhoto) && Objects.equals(photo, rating1.photo) &&
                Objects.equals(comment, rating1.comment) && Objects.equals(likes, rating1.likes) &&
                Objects.equals(creationDate, rating1.creationDate);
    }

    @Override
    public int hashCode() {

        return Objects
                .hash(id, beerId, beerName, userId, userName, userPhoto, photo, rating, comment, likes, creationDate);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Map<String, Boolean> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, Boolean> likes) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
