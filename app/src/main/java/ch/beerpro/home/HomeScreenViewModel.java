package ch.beerpro.home;

import android.util.Log;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import ch.beerpro.helpers.EntityClassSnapshotParser;
import ch.beerpro.helpers.FirestoreQueryLiveDataArray;
import ch.beerpro.models.Beer;
import ch.beerpro.models.Like;
import ch.beerpro.models.Rating;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;

import java.util.*;

public class HomeScreenViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";
    private final FirestoreQueryLiveDataArray<Beer> allBeers = new FirestoreQueryLiveDataArray<>(
            FirebaseFirestore.getInstance().collection(Beer.COLLECTION).orderBy(Beer.FIELD_NAME), Beer.class);
    private final FirestoreQueryLiveDataArray<Rating> allRatings = new FirestoreQueryLiveDataArray<>(
            FirebaseFirestore.getInstance().collection(Rating.COLLECTION)
                    .orderBy(Rating.FIELD_CREATION_DATE, Query.Direction.DESCENDING), Rating.class);
    private final LiveData<List<String>> beerCategories;
    private final LiveData<List<String>> beerManufacturers;
    private EntityClassSnapshotParser<Rating> parser = new EntityClassSnapshotParser<>(Rating.class);

    public HomeScreenViewModel() {
        beerCategories = Transformations.map(allBeers, projectBeersToCagetories());
        beerManufacturers = Transformations.map(allBeers, projectBeersToManufacturers());
    }

    public FirestoreQueryLiveDataArray<Rating> getAllRatings() {
        return allRatings;
    }

    private Function<List<Beer>, List<String>> projectBeersToCagetories() {
        return (List<Beer> beers) -> {
            Set<String> filtered = new HashSet<>();
            for (Beer beer : beers) {
                filtered.add(beer.getCategory());

            }
            String[] strings = filtered.toArray(new String[0]);
            return Arrays.asList(strings).subList(0, 8);
        };
    }


    private Function<List<Beer>, List<String>> projectBeersToManufacturers() {
        return (List<Beer> beers) -> {
            Set<String> filtered = new HashSet<>();
            for (Beer beer : beers) {
                filtered.add(beer.getManufacturer());

            }
            String[] strings = filtered.toArray(new String[0]);
            Arrays.sort(strings);
            return Arrays.asList(strings);
        };
    }


    public LiveData<List<String>> getBeerCategories() {
        return beerCategories;
    }

    public LiveData<List<String>> getBeerManufacturers() {
        return beerManufacturers;
    }

    public void toggleLike(Rating rating) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference ratingRef = db.collection(Rating.COLLECTION).document(rating.getId());

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            Rating currentRating = parser.parseSnapshot(transaction.get(ratingRef));
            Map<String, Boolean> likes = currentRating.getLikes();
            String currentUserUid = currentUser.getUid();
            if (likes.containsKey(currentUserUid)) {
                likes.remove(currentUserUid);
            } else {
                likes.put(currentUserUid, true);
            }
            transaction.update(ratingRef, Rating.FIELD_LIKES, likes);
            return null;
        });
    }

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}