package ch.beerpro.presentation.profile.mybeers.models;

import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Wish;
import lombok.Data;

import java.util.Date;

@Data
public class MyBeerFromWishlist implements MyBeer {
    private Wish wish;
    private Beer beer;

    public MyBeerFromWishlist(Wish wish, Beer beer) {
        this.wish = wish;
        this.beer = beer;
    }

    @Override
    public String getBeerId() {
        return wish.getBeerId();
    }

    @Override
    public Date getDate() {
        return wish.getAddedAt();
    }
}
