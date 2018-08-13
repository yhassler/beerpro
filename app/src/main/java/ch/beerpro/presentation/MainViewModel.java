package ch.beerpro.presentation;

import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.data.repositories.*;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.MyBeer;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MainViewModel extends ViewModel implements CurrentUser {

    private static final String TAG = "MainViewModel";

    private final RatingsRepository ratingsRepository;
    private final LikesRepository likesRepository;
    private final LiveData<List<Wish>> myWishlist;
    private final WishlistRepository wishlistRepository;
    private final LiveData<List<Rating>> myRatings;
    private final LiveData<List<MyBeer>> myBeers;
    private final BeersRepository beersRepository;

    public MainViewModel() {
        // TODO We should really be injecting these!
        beersRepository = new BeersRepository();
        likesRepository = new LikesRepository();
        wishlistRepository = new WishlistRepository();
        ratingsRepository = new RatingsRepository();
        MyBeersRepository myBeersRepository = new MyBeersRepository();

        LiveData<List<Beer>> allBeers = beersRepository.getAllBeers();

        MutableLiveData<String> currentUserId = new MutableLiveData<>();
        myWishlist = wishlistRepository.getMyWishlist(currentUserId);
        myRatings = ratingsRepository.getMyRatings(currentUserId);
        myBeers = myBeersRepository.getMyBeers(allBeers, myWishlist, myRatings);

        /*
         * Set the current user id, which is used as input for the getMyWishlist and getMyRatings calls above.
         * */
        currentUserId.setValue(getCurrentUser().getUid());
    }

    public LiveData<List<MyBeer>> getMyBeers() {
        return myBeers;
    }

    public LiveData<List<Rating>> getMyRatings() {
        return myRatings;
    }

    public LiveData<List<Wish>> getMyWishlist() {
        return myWishlist;
    }

    public LiveData<List<String>> getBeerCategories() {
        return beersRepository.getBeerCategories();
    }

    public LiveData<List<String>> getBeerManufacturers() {
        return beersRepository.getBeerManufacturers();
    }

    public void toggleLike(Rating rating) {
        likesRepository.toggleLike(rating);
    }

    public Task<Void> toggleItemInWishlist(String itemId) {
        return wishlistRepository.toggleUserWishlistItem(getCurrentUser().getUid(), itemId);
    }

    public LiveData<List<Pair<Rating, Wish>>> getAllRatingsWithWishes() {
        return ratingsRepository.getAllRatingsWithWishes(myWishlist);
    }
}