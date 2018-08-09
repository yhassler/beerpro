package ch.beerpro.presentation.explore.search;

import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.repositories.BeersRepository;
import ch.beerpro.presentation.profile.mybeers.MyBeersViewModel;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.lifecycle.Transformations.map;
import static ch.beerpro.domain.utils.LiveDataExtensions.zip;

public class SearchViewModel extends MyBeersViewModel {

    private static final String TAG = "SearchViewModel";
    private final MutableLiveData<String> searchTerm = new MutableLiveData<>();

    private final LiveData<List<Beer>> filteredBeers;
    private final BeersRepository beersRepository;

    public SearchViewModel() {
        beersRepository = new BeersRepository();
        filteredBeers = map(zip(searchTerm, getAllBeers()), SearchViewModel::filter);
    }

    public LiveData<List<Beer>> getAllBeers() {
        return beersRepository.getAllBeers();
    }

    private static List<Beer> filter(Pair<String, List<Beer>> input) {
        String searchTerm1 = input.first;
        List<Beer> allBeers = input.second;
        if (Strings.isNullOrEmpty(searchTerm1)) {
            return allBeers;
        }
        if (allBeers == null) {
            return Collections.emptyList();
        }
        ArrayList<Beer> filtered = new ArrayList<>();
        for (Beer beer : allBeers) {
            if (beer.getName().toLowerCase().contains(searchTerm1.toLowerCase())) {
                filtered.add(beer);
            }
        }
        return filtered;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm.setValue(searchTerm);
    }

    public LiveData<List<Beer>> getFilteredBeers() {
        return filteredBeers;
    }
}