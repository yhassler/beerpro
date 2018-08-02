package ch.beerpro.search;

import android.util.Pair;
import androidx.arch.core.util.Function;
import androidx.lifecycle.*;
import ch.beerpro.models.Beer;
import ch.beerpro.helpers.FirestoreQueryLiveDataArray;
import ch.beerpro.helpers.LiveDataExtensions;
import com.google.common.base.Strings;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivityViewModel extends ViewModel {

    private static final String TAG = "SearchActivityViewModel";
    private final MutableLiveData<String> searchTerm = new MutableLiveData<>();
    private final FirestoreQueryLiveDataArray<Beer> allBeers =
            new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection("beers").orderBy(Beer.FIELD_NAME), Beer
                    .class);

    private final LiveData<List<Beer>> filteredBeers;

    public SearchActivityViewModel() {
        filteredBeers = Transformations.map(LiveDataExtensions.combineLatest(searchTerm, allBeers), filterBeers());
    }

    private Function<Pair<String, List<Beer>>, List<Beer>> filterBeers() {
        return (Pair<String, List<Beer>> input) -> {
            String searchTerm = input.first;
            List<Beer> allBeers = input.second;
            if (Strings.isNullOrEmpty(searchTerm)) {
                return allBeers;
            }
            if (allBeers == null) {
                return Collections.emptyList();
            }
            ArrayList<Beer> filtered = new ArrayList<>();
            for (Beer beer : allBeers) {
                if (beer.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                    filtered.add(beer);
                }
            }
            return filtered;
        };
    }

    public void setCurrentSearchTerm(String searchTerm) {
        this.searchTerm.setValue(searchTerm);
    }

    public LiveData<List<Beer>> getFilteredBeers() {
        return filteredBeers;
    }

    public LiveData<List<Beer>> getAllBeers() {
        return allBeers;
    }
}