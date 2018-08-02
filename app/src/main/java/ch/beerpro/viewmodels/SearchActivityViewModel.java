package ch.beerpro.viewmodels;

import android.util.Log;
import android.util.Pair;
import androidx.lifecycle.*;
import ch.beerpro.dummy.Beer;
import ch.beerpro.helpers.FirestoreQueryLiveDataArray;
import ch.beerpro.helpers.LiveDataExt;
import com.google.common.base.Strings;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivityViewModel extends ViewModel {

    private static final String TAG = "SearchActivityViewModel";
    private final MutableLiveData<String> searchTerm = new MutableLiveData<>();
    private final FirestoreQueryLiveDataArray<Beer> allBeers =
            new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection("beers"),
                    Beer.class);

    private final LiveData<List<Beer>> filteredBeers;


    public SearchActivityViewModel() {
        Log.i(TAG, "created");

        filteredBeers = Transformations
                .map(LiveDataExt.combineLatest(searchTerm, allBeers), (Pair<String, List<Beer>> input) -> {
                    Log.i(TAG, "data changed! " + input.toString());

                    String searchTerm = input.first;
                    List<Beer> allBeers = input.second;
                    if (Strings.isNullOrEmpty(searchTerm)) {
                        return allBeers;
                    }
                    if(allBeers == null) {
                        return Collections.emptyList();
                    }
                    ArrayList<Beer> filtered = new ArrayList<>();
                    for (Beer beer : allBeers) {
                        if (beer.name.toLowerCase().contains(searchTerm.toLowerCase())) {
                            filtered.add(beer);
                        }
                    }
                    return filtered;
                });
    }

    public LiveData<String> getCurrentSearchTerm() {
        return searchTerm;
    }

    public void setCurrentSearchTerm(String searchTerm) {
        this.searchTerm.setValue(searchTerm);
    }

    public LiveData<List<Beer>> getFilteredBeers() {
        return filteredBeers;
    }
}