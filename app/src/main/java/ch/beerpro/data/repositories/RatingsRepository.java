package ch.beerpro.data.repositories;

import androidx.lifecycle.LiveData;
import ch.beerpro.domain.utils.FirestoreQueryLiveDataArray;
import ch.beerpro.domain.models.Rating;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class RatingsRepository {

    private final FirestoreQueryLiveDataArray<Rating> allRatings = new FirestoreQueryLiveDataArray<>(
            FirebaseFirestore.getInstance().collection(Rating.COLLECTION)
                    .orderBy(Rating.FIELD_CREATION_DATE, Query.Direction.DESCENDING), Rating.class);

    public static LiveData<List<Rating>> getRatingsByUser(String userId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Rating.COLLECTION)
                .orderBy(Rating.FIELD_CREATION_DATE, Query.Direction.DESCENDING)
                .whereEqualTo(Rating.FIELD_USER_ID, userId), Rating.class);
    }

    public static LiveData<List<Rating>> getRatingsByBeer(String beerId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Rating.COLLECTION)
                .orderBy(Rating.FIELD_CREATION_DATE, Query.Direction.DESCENDING)
                .whereEqualTo(Rating.FIELD_BEER_ID, beerId), Rating.class);
    }

    public LiveData<List<Rating>> getAllRatings() {
        return allRatings;
    }
}
