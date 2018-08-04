package ch.beerpro.single;

import ch.beerpro.models.*;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
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

public class SingleBeerViewModel extends ViewModel {

    private FirestoreQueryLiveDataArray<Rating> ratings;
    private EntityClassSnapshotParser<Rating> parser = new EntityClassSnapshotParser<>(Rating.class);

    private LiveData<Beer> beer;

    // TODO move to constructor, see
    // https://medium.com/@dpreussler/add-the-new-viewmodel-to-your-mvvm-36bfea86b159

    public LiveData<List<Rating>> getRatings(String id) {
        if (ratings == null) {
            ratings = new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Rating.COLLECTION)
                    .orderBy(Rating.FIELD_CREATION_DATE, Query.Direction.DESCENDING)
                    .whereEqualTo(Rating.FIELD_BEER_ID, id), Rating.class);
        }
        return ratings;
    }

    public LiveData<Beer> getBeer(String id) {
        if (beer == null) {
            DocumentReference document = FirebaseFirestore.getInstance().collection(Beer.COLLECTION).document(id);
            beer = new FirestoreQueryLiveData<>(document, Beer.class);
        }
        return beer;
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