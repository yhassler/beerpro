package ch.beerpro;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import ch.beerpro.helpers.FirestoreQueryLiveDataArray;
import ch.beerpro.models.Beer;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.*;

public class HomeScreenViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";
    private final FirestoreQueryLiveDataArray<Beer> allBeers = new FirestoreQueryLiveDataArray<>(
            FirebaseFirestore.getInstance().collection("beers").orderBy(Beer.FIELD_NAME), Beer.class);

    private final LiveData<List<String>> beerCategories;
    private final LiveData<List<String>> beerManufacturers;

    public HomeScreenViewModel() {
        beerCategories = Transformations.map(allBeers, projectBeersToCagetories());
        beerManufacturers = Transformations.map(allBeers, projectBeersToManufacturers());
    }

    private Function<List<Beer>, List<String>> projectBeersToCagetories() {
        return (List<Beer> beers) -> {
            Set<String> filtered = new HashSet<>();
            for (Beer beer : beers) {
                filtered.add(beer.getCategory());

            }
            String[] strings = filtered.toArray(new String[0]);
            return Arrays.asList(strings).subList(0, 8);
        };
    }


    private Function<List<Beer>, List<String>> projectBeersToManufacturers() {
        return (List<Beer> beers) -> {
            Set<String> filtered = new HashSet<>();
            for (Beer beer : beers) {
                filtered.add(beer.getManufacturer());

            }
            String[] strings = filtered.toArray(new String[0]);
            Arrays.sort(strings);
            return Arrays.asList(strings);
        };
    }


    public LiveData<List<String>> getBeerCategories() {
        return beerCategories;
    }

    public LiveData<List<String>> getBeerManufacturers() {
        return beerManufacturers;
    }
}