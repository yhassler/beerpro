package ch.beerpro.presentation.models;

import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Rating;
import lombok.Data;

import java.util.Date;

@Data
public class MyBeerFromRating implements MyBeerItem {
    private Rating rating;
    private Beer beer;

    public MyBeerFromRating(Rating rating, Beer beer) {
        this.rating = rating;
        this.beer = beer;
    }

    @Override
    public String getBeerId() {
        return rating.getBeerId();
    }

    @Override
    public Date getDate() {
        return rating.getCreationDate();
    }
}
