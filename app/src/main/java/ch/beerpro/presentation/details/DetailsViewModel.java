package ch.beerpro.presentation.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.domain.repositories.*;
import com.google.android.gms.tasks.Task;

import java.util.List;

import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.utils.LiveDataExtensions.combineLatest;

public class DetailsViewModel extends ViewModel implements CurrentUser {

    private final MutableLiveData<String> beerId = new MutableLiveData<>();
    private final LiveData<Beer> beer = switchMap(beerId, BeersRepository::getBeer);
    private final LiveData<List<Rating>> ratings = switchMap(beerId, RatingsRepository::getRatingsByBeer);

    private final MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private final LiveData<Wish> wish =
            switchMap(combineLatest(currentUserId, getBeer()), WishesRepository::getUserWishListFor);
    private final LikesRepository likesRepository;
    private final WishesRepository wishesRepository;

    public DetailsViewModel() {
        // TODO We should really be injecting these!
        likesRepository = new LikesRepository();
        wishesRepository = new WishesRepository();

        currentUserId.setValue(getCurrentUser().getUid());
    }


    public LiveData<Wish> getWish() {
        return wish;
    }

    public LiveData<List<Rating>> getRatings() {
        return ratings;
    }

    public LiveData<Beer> getBeer() {
        return beer;
    }

    public void setBeerId(String beerId) {
        this.beerId.setValue(beerId);
    }

    public void toggleLike(Rating rating) {
        likesRepository.toggleLike(rating);
    }

    public Task<Void> toggleItemInWishlist(String itemId) {
        return wishesRepository.toggleUserWishlistItem(getCurrentUser().getUid(), itemId);
    }
}