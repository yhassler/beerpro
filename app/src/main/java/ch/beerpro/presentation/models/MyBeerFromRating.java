package ch.beerpro.presentation.models;

import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Rating;

public class MyBeerFromRating implements MyBeerItem {
    private Rating rating;

    public MyBeerFromRating(Rating rating, Beer beer) {
        this.rating = rating;
    }

    @Override
    public String getBeerId() {
        return rating.getBeerId();
    }
}
