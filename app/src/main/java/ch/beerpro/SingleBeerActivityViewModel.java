package ch.beerpro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.helpers.EntityClassSnapshotParser;
import ch.beerpro.helpers.FirestoreQueryLiveData;
import ch.beerpro.models.Beer;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SingleBeerActivityViewModel extends ViewModel {

    private LiveData<Beer> beer;

    public LiveData<Beer> getBeer(String id) {
        if (beer == null) {
            DocumentReference document = FirebaseFirestore.getInstance().collection("beers").document(id);
            beer = new FirestoreQueryLiveData<>(document, Beer.class);
        }
        return beer;
    }
}