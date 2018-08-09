package ch.beerpro.presentation.profile.myratings;

import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.domain.repositories.RatingsRepository;
import ch.beerpro.domain.repositories.WishesRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static androidx.lifecycle.Transformations.map;
import static ch.beerpro.domain.utils.LiveDataExtensions.combineLatest;

public class MyRatingsViewModel extends ViewModel {

    private static final String TAG = "MyBeersViewModel";

    private final MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private final LiveData<List<Rating>> myRatings;
    private final LiveData<List<Wish>> myWishlist;
    private final WishesRepository wishesRepository;

    public MyRatingsViewModel() {
        wishesRepository = new WishesRepository();
        myWishlist = Transformations.switchMap(currentUserId, WishesRepository::getWishesByUser);
        myRatings = Transformations.switchMap(currentUserId, RatingsRepository::getRatingsByUser);

        currentUserId.setValue(getCurrentUser().getUid());
    }

    FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public LiveData<List<Pair<Rating, Wish>>> getMyRatingsWithWishes() {
        return map(combineLatest(getMyRatings(), getMyWishlist()), input -> {
            List<Rating> ratings = input.first;

            // Optimization: also do this in a transformation
            List<Wish> wishes = input.second == null ? Collections.emptyList() : input.second;
            HashMap<String, Wish> wishesByItem = new HashMap<>();
            for (Wish wish : wishes) {
                wishesByItem.put(wish.getBeerId(), wish);
            }

            ArrayList<Pair<Rating, Wish>> result = new ArrayList<>();
            for (Rating rating : ratings) {
                Wish wish = wishesByItem.get(rating.getBeerId());
                result.add(Pair.create(rating, wish));
            }
            return result;
        });
    }

    public LiveData<List<Rating>> getMyRatings() {
        return myRatings;
    }

    public LiveData<List<Wish>> getMyWishlist() {
        return myWishlist;
    }

    public void toggleItemInWishlist(String beerId) {
        wishesRepository.toggleUserWishlistItem(getCurrentUser().getUid(), beerId);
    }
}