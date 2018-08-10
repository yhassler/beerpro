package ch.beerpro.presentation.profile.mybeers;

import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.data.repositories.*;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Entity;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.presentation.profile.mybeers.models.MyBeer;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.lifecycle.Transformations.map;
import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.utils.LiveDataExtensions.combineLatest;
import static ch.beerpro.domain.utils.LiveDataExtensions.zip;

public class MyBeersViewModel extends ViewModel implements CurrentUser {

    private static final String TAG = "MyBeersViewModel";
    private final MutableLiveData<String> searchTerm = new MutableLiveData<>();

    private final MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private final WishesRepository wishesRepository;
    private final LiveData<List<MyBeer>> myFilteredBeers;

    public MyBeersViewModel() {

        BeersRepository beersRepository = new BeersRepository();
        wishesRepository = new WishesRepository();

        LiveData<List<Beer>> allBeers = beersRepository.getAllBeers();
        LiveData<List<Wish>> myWishlist = switchMap(currentUserId, WishesRepository::getWishesByUser);
        LiveData<List<Rating>> myRatings = switchMap(currentUserId, RatingsRepository::getRatingsByUser);

        LiveData<List<MyBeer>> myBeers = map(combineLatest(myWishlist, myRatings, map(allBeers, Entity::entitiesById)),
                MyBeersRepository::getMyBeers);

        myFilteredBeers = map(zip(searchTerm, myBeers), MyBeersViewModel::filter);

        currentUserId.setValue(getCurrentUser().getUid());
    }

    private static List<MyBeer> filter(Pair<String, List<MyBeer>> input) {
        String searchTerm1 = input.first;
        List<MyBeer> myBeers = input.second;
        if (Strings.isNullOrEmpty(searchTerm1)) {
            return myBeers;
        }
        if (myBeers == null) {
            return Collections.emptyList();
        }
        ArrayList<MyBeer> filtered = new ArrayList<>();
        for (MyBeer beer : myBeers) {
            if (beer.getBeer().getName().toLowerCase().contains(searchTerm1.toLowerCase())) {
                filtered.add(beer);
            }
        }
        return filtered;
    }

    public LiveData<List<MyBeer>> getMyFilteredBeers() {
        return myFilteredBeers;
    }

    public void toggleItemInWishlist(String beerId) {
        wishesRepository.toggleUserWishlistItem(getCurrentUser().getUid(), beerId);
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm.setValue(searchTerm);
    }
}