package ch.beerpro;

import android.app.ActivityOptions;
import android.util.Log;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import ch.beerpro.helpers.ViewPagerAdapter;
import ch.beerpro.search.SearchActivity;
import ch.beerpro.search.SearchActivityViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;

public class HomeScreenActivitySearchFragment extends Fragment {

    private static final String TAG = "HomeScreenActSearch";
    private ViewPagerAdapter adapter;

    public HomeScreenActivitySearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_search_screen, container, false);
        View beerSearchButton = rootView.findViewById(R.id.beerSearchButton);
        beerSearchButton.setOnClickListener(view -> {

            Intent intent = new Intent(getActivity(), SearchActivity.class);
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), beerSearchButton, "search");

            startActivity(intent, options.toBundle());
        });

        ViewPager viewPager = rootView.findViewById(R.id.viewpager);
        TabLayout tabLayout = rootView.findViewById(R.id.tablayout);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new BeerCategoriesFragment(), "Bierart");
        adapter.addFragment(new BeerManufacturersFragment(), "Brauerei");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }
}
