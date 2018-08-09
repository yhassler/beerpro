package ch.beerpro.presentation.profile.mybeers.models;

import ch.beerpro.domain.models.Beer;

import java.util.Date;

public interface MyBeer {
    String getBeerId();

    Beer getBeer();

    Date getDate();
}
