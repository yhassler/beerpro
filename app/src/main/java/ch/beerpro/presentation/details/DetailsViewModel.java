package ch.beerpro.presentation.details;

import androidx.lifecycle.*;
import ch.beerpro.domain.models.*;
import ch.beerpro.presentation.helpers.EntityClassSnapshotParser;
import ch.beerpro.domain.helpers.FirestoreQueryLiveData;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.repositories.LikesRepository;
import ch.beerpro.domain.repositories.RatingsRepository;
import ch.beerpro.domain.repositories.WishesRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.*;

import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.helpers.LiveDataExtensions.combineLatest;

public class DetailsViewModel extends ViewModel {

    private static final EntityClassSnapshotParser<Rating> parser = new EntityClassSnapshotParser<>(Rating.class);
    private final MutableLiveData<String> beerId = new MutableLiveData<>();
    private final LiveData<Beer> beer = switchMap(beerId, beerId -> {
        DocumentReference document = FirebaseFirestore.getInstance().collection(Beer.COLLECTION).document(beerId);
        return new FirestoreQueryLiveData<>(document, Beer.class);
    });
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

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void toggleLike(Rating rating) {
        likesRepository.toggleLike(rating);
    }

    public Task<Void> toggleItemInWishlist(String itemId) {
        return wishesRepository.toggleUserWishlistItem(getCurrentUser().getUid(), itemId);
    }
}