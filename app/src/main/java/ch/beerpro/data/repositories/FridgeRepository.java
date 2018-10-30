package ch.beerpro.data.repositories;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Entity;
import ch.beerpro.domain.models.FridgeContent;
import ch.beerpro.domain.utils.FirestoreQueryLiveData;
import ch.beerpro.domain.utils.FirestoreQueryLiveDataArray;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static androidx.lifecycle.Transformations.map;
import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.utils.LiveDataExtensions.combineLatest;

public class FridgeRepository {

    private static LiveData<List<FridgeContent>> getFridgeContentByUser(String userId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(FridgeContent.COLLECTION)
                .orderBy(FridgeContent.FIELD_ADDED_AT, Query.Direction.DESCENDING).whereEqualTo(FridgeContent.FIELD_USER_ID, userId),
                FridgeContent.class);
    }

    private static LiveData<FridgeContent> getFridgeContentFor(Pair<String, Beer> input) {
        String userId = input.first;
        Beer beer = input.second;
        DocumentReference document = FirebaseFirestore.getInstance().collection(FridgeContent.COLLECTION)
                .document(FridgeContent.generateId(userId, beer.getId()));
        return new FirestoreQueryLiveData<>(document, FridgeContent.class);
    }

    public Task<Void> toggleUserFridgeContentItem(String userId, String itemId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String FridgeContentId = FridgeContent.generateId(userId, itemId);

        DocumentReference fridgeEntryQuery = db.collection(FridgeContent.COLLECTION).document(FridgeContentId);

        return fridgeEntryQuery.get().continueWithTask(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                return fridgeEntryQuery.delete();
            } else if (task.isSuccessful()) {
                return fridgeEntryQuery.set(new FridgeContent(userId, itemId, new Date()));
            } else {
                throw task.getException();
            }
        });
    }

        public LiveData<List<Pair<FridgeContent, Beer>>> getMyFridgeContentWithBeers(LiveData<String> currentUserId,
                                                                                     LiveData<List<Beer>> allBeers) {
            return map(combineLatest(getMyFridgeContent(currentUserId), map(allBeers, Entity::entitiesById)), input -> {
                List<FridgeContent> fridgeContents = input.first;
                HashMap<String, Beer> beersById = input.second;

                ArrayList<Pair<FridgeContent, Beer>> result = new ArrayList<>();
                for (FridgeContent fridgeContent : fridgeContents) {
                    Beer beer = beersById.get(fridgeContent.getBeerId());
                    result.add(Pair.create(fridgeContent, beer));
                }
                return result;
            });
        }

        public LiveData<List<FridgeContent>> getMyFridgeContent(LiveData<String> currentUserId) {
            return switchMap(currentUserId, FridgeRepository::getFridgeContentByUser);
        }


        public LiveData<FridgeContent> getMyFridgeContentForBeer(LiveData<String> currentUserId, LiveData<Beer> beer) {


            return switchMap(combineLatest(currentUserId, beer), FridgeRepository::getFridgeContentFor);
        }
    }


