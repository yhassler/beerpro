package ch.beerpro.presentation.mybeers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Entity;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.domain.repositories.*;
import ch.beerpro.presentation.models.MyBeerItem;

import java.util.List;

import static androidx.lifecycle.Transformations.map;
import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.helpers.LiveDataExtensions.combineLatest;

public class MyBeersViewModel extends ViewModel implements CurrentUser {

    private static final String TAG = "MyBeersViewModel";

    private final MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private final LiveData<List<MyBeerItem>> myBeers;
    private final WishesRepository wishesRepository;

    public MyBeersViewModel() {

        BeersRepository beersRepository = new BeersRepository();
        wishesRepository = new WishesRepository();

        LiveData<List<Beer>> allBeers = beersRepository.getAllBeers();
        LiveData<List<Wish>> myWishlist = switchMap(currentUserId, WishesRepository::getWishesByUser);
        LiveData<List<Rating>> myRatings = switchMap(currentUserId, RatingsRepository::getRatingsByUser);

        myBeers = map(combineLatest(myWishlist, myRatings, map(allBeers, Entity::entitiesById)),
                MyBeersRepository::getMyBeers);


        currentUserId.setValue(getCurrentUser().getUid());
    }

    public LiveData<List<MyBeerItem>> getMyBeers() {
        return myBeers;
    }

    public void toggleItemInWishlist(String beerId) {
        wishesRepository.toggleUserWishlistItem(getCurrentUser().getUid(), beerId);
    }
}