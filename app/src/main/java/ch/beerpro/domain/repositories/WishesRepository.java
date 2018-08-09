package ch.beerpro.domain.repositories;

import android.util.Pair;
import androidx.lifecycle.LiveData;
import ch.beerpro.domain.utils.FirestoreQueryLiveData;
import ch.beerpro.domain.utils.FirestoreQueryLiveDataArray;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Wish;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;
import java.util.List;

public class WishesRepository {


    public static LiveData<List<Wish>> getWishesByUser(String userId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Wish.COLLECTION)
                .orderBy(Wish.FIELD_ADDED_AT, Query.Direction.DESCENDING).whereEqualTo(Wish.FIELD_USER_ID, userId),
                Wish.class);
    }

    public static LiveData<Wish> getUserWishListFor(Pair<String, Beer> input) {
        String userId = input.first;
        Beer beer = input.second;
        DocumentReference document = FirebaseFirestore.getInstance().collection(Wish.COLLECTION)
                .document(Wish.generateId(userId, beer.getId()));
        return new FirestoreQueryLiveData<>(document, Wish.class);
    }

    public Task<Void> toggleUserWishlistItem(String userId, String itemId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String wishId = Wish.generateId(userId, itemId);

        DocumentReference wishEntryQuery = db.collection(Wish.COLLECTION).document(wishId);

        return wishEntryQuery.get().continueWithTask(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                return wishEntryQuery.delete();
            } else if (task.isSuccessful()) {
                return wishEntryQuery.set(new Wish(userId, itemId, new Date()));
            } else {
                throw task.getException();
            }
        });
    }
}
