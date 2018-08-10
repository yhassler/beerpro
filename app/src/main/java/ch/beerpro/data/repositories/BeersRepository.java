package ch.beerpro.data.repositories;

import androidx.lifecycle.LiveData;
import ch.beerpro.domain.utils.FirestoreQueryLiveData;
import ch.beerpro.domain.utils.FirestoreQueryLiveDataArray;
import ch.beerpro.domain.models.Beer;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class BeersRepository {

    private final FirestoreQueryLiveDataArray<Beer> allBeers =
            new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Beer.COLLECTION), Beer.class);

    public static LiveData<Beer> getBeer(String beerId) {
        return new FirestoreQueryLiveData<>(
                FirebaseFirestore.getInstance().collection(Beer.COLLECTION).document(beerId), Beer.class);
    }


    public LiveData<List<Beer>> getAllBeers() {
        return allBeers;
    }
}
