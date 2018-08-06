package ch.beerpro.single;

import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import ch.beerpro.models.*;
import ch.beerpro.helpers.EntityClassSnapshotParser;
import ch.beerpro.helpers.FirestoreQueryLiveData;
import ch.beerpro.helpers.FirestoreQueryLiveDataArray;
import ch.beerpro.models.Beer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;

import java.util.List;
import java.util.Map;

import static androidx.lifecycle.Transformations.switchMap;

public class SingleBeerViewModel extends ViewModel {

    private static final EntityClassSnapshotParser<Rating> parser = new EntityClassSnapshotParser<>(Rating.class);
    private final MutableLiveData<String> beerId = new MutableLiveData<>();
    private final LiveData<Beer> beer = switchMap(beerId, beerId -> {
        DocumentReference document = FirebaseFirestore.getInstance().collection(Beer.COLLECTION).document(beerId);
        return new FirestoreQueryLiveData<>(document, Beer.class);
    });
    private final LiveData<List<Rating>> ratings = switchMap(beerId, beerId -> new FirestoreQueryLiveDataArray<>(
            FirebaseFirestore.getInstance().collection(Rating.COLLECTION)
                    .orderBy(Rating.FIELD_CREATION_DATE, Query.Direction.DESCENDING)
                    .whereEqualTo(Rating.FIELD_BEER_ID, beerId), Rating.class));


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

        // TODO remove duplication with HomeScreenViewModel

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

}