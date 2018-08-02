package ch.beerpro;

import android.util.Log;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProviders;
import ch.beerpro.search.SearchActivity;
import ch.beerpro.search.SearchActivityViewModel;

import java.util.Arrays;

public class HomeScreenActivitySearchFragment extends Fragment {

    private static final String TAG = "HomeScreenActSearch";

    public HomeScreenActivitySearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_search_screen, container, false);
        View beerSearchButton = rootView.findViewById(R.id.beerSearchButton);
        beerSearchButton.setOnClickListener(view -> startActivity(new Intent(getActivity(), SearchActivity.class)));


        SearchActivityViewModel model = ViewModelProviders.of(this).get(SearchActivityViewModel.class);
        model.getFilteredBeers().observe(this, beers -> {
            Log.i(TAG, Arrays.toString(beers.toArray()));
        });

        return rootView;
    }
}
