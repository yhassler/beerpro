package ch.beerpro.presentation.home;

import android.util.Pair;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Entity;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.domain.repositories.*;
import ch.beerpro.presentation.helpers.EntityClassSnapshotParser;
import ch.beerpro.presentation.models.MyBeerItem;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.*;

import static androidx.lifecycle.Transformations.map;
import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.helpers.LiveDataExtensions.combineLatest;

public class HomeScreenViewModel extends ViewModel {

    private static final String TAG = "HomeScreenViewModel";

    private final static EntityClassSnapshotParser<Beer> parser = new EntityClassSnapshotParser<>(Beer.class);


    private final static Function<List<Beer>, List<String>> mapBeersToCategories = (List<Beer> beers) -> {
        Set<String> filtered = new HashSet<>();
        for (Beer beer : beers) {
            filtered.add(beer.getCategory());
        }
        String[] strings = filtered.toArray(new String[0]);
        return Arrays.asList(strings).subList(0, 8);
    };
    private final static Function<List<Beer>, List<String>> mapBeersToManufacturers = (List<Beer> beers) -> {
        Set<String> filtered = new HashSet<>();
        for (Beer beer : beers) {
            filtered.add(beer.getManufacturer());
        }
        String[] strings = filtered.toArray(new String[0]);
        Arrays.sort(strings);
        return Arrays.asList(strings);
    };

    private final LikesRepository likesRepository;


    private final LiveData<List<String>> beerCategories;

    private final LiveData<List<String>> beerManufacturers;

    private final LiveData<List<Wish>> myWishlist;

    private final MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private final WishesRepository wishesRepository;
    private final RatingsRepository ratingsRepository;
    private final LiveData<List<Rating>> myRatings;
    private final LiveData<List<MyBeerItem>> myBeers;
    private final LiveData<Integer> myBeersUniqueCount;
    private final BeersRepository beersRepository;

    public HomeScreenViewModel() {
        // TODO We should really be injecting these!
        beersRepository = new BeersRepository();
        likesRepository = new LikesRepository();
        wishesRepository = new WishesRepository();
        ratingsRepository = new RatingsRepository();

        LiveData<List<Beer>> allBeers = beersRepository.getAllBeers();
        beerCategories = map(allBeers, mapBeersToCategories);
        beerManufacturers = map(allBeers, mapBeersToManufacturers);
        myWishlist = switchMap(currentUserId, WishesRepository::getWishesByUser);
        myRatings = switchMap(currentUserId, RatingsRepository::getRatingsByUser);

        myBeers = map(combineLatest(myWishlist, myRatings, map(allBeers, Entity::entitiesById)),
                MyBeersRepository::getMyBeers);

        myBeersUniqueCount = map(myBeers, beers -> {
            Set<String> ids = new HashSet<>();
            for (MyBeerItem beer : beers) {
                ids.add(beer.getBeerId());
            }
            return ids.size();
        });

        currentUserId.setValue(getCurrentUser().getUid());
    }

    FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }


    public LiveData<List<MyBeerItem>> getMyBeers() {
        return myBeers;
    }

    public LiveData<List<Rating>> getMyRatings() {
        return myRatings;
    }

    public LiveData<List<Pair<Rating, Wish>>> getAllRatingsWithWishes() {
        return map(combineLatest(getAllRatings(), map(getMyWishlist(), Entity::entitiesById)), input -> {
            List<Rating> ratings = input.first;
            HashMap<String, Wish> wishesByItem = input.second;

            ArrayList<Pair<Rating, Wish>> result = new ArrayList<>();
            for (Rating rating : ratings) {
                Wish wish = wishesByItem.get(rating.getBeerId());
                result.add(Pair.create(rating, wish));
            }
            return result;
        });
    }

    public LiveData<List<Rating>> getAllRatings() {
        return ratingsRepository.getAllRatings();
    }

    public LiveData<List<Wish>> getMyWishlist() {
        return myWishlist;
    }

    public LiveData<List<String>> getBeerCategories() {
        return beerCategories;
    }

    public LiveData<List<String>> getBeerManufacturers() {
        return beerManufacturers;
    }

    public void toggleLike(Rating rating) {
        likesRepository.toggleLike(rating);
    }

    public Task<Void> toggleItemInWishlist(String itemId) {
        return wishesRepository.toggleUserWishlistItem(getCurrentUser().getUid(), itemId);
    }

    public LiveData<Integer> getMyBeersUniqueCount() {
        return myBeersUniqueCount;
    }
}