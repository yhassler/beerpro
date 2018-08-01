package ch.beerpro;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ch.beerpro.search.SearchActivity;

public class HomeScreenActivitySearchFragment extends Fragment {

    public HomeScreenActivitySearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_search_screen, container, false);
        View beerSearchButton = rootView.findViewById(R.id.beerSearchButton);
        beerSearchButton.setOnClickListener(view -> startActivity(new Intent(getActivity(), SearchActivity.class)));

        return rootView;
    }

}
