package ch.beerpro.presentation.models;

import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Wish;

public class MyBeerFromWishlist implements MyBeerItem {
    private Wish wish;

    public MyBeerFromWishlist(Wish wish, Beer beer) {
        this.wish = wish;
    }

    @Override
    public String getBeerId() {
        return wish.getBeerId();
    }
}
