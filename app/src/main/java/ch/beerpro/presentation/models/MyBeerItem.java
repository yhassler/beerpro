package ch.beerpro.presentation.models;

import ch.beerpro.domain.models.Beer;

import java.util.Date;

public interface MyBeerItem {
    String getBeerId();

    Beer getBeer();

    Date getDate();
}
