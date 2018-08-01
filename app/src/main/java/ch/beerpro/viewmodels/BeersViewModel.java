package ch.beerpro.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.dummy.DummyContent;
import ch.beerpro.helpers.FirestoreQueryLiveDataArray;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class BeersViewModel extends ViewModel {

    private final Query query = FirebaseFirestore.getInstance().collection("beers");

    private final FirestoreQueryLiveDataArray<DummyContent.Beer> liveData =
            new FirestoreQueryLiveDataArray<>(query, DummyContent.Beer.class);

    @NonNull
    public LiveData<List<DummyContent.Beer>> getBeers() {
        return liveData;
    }
}