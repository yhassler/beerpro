package ch.beerpro.presentation.home;

import android.app.ActivityOptions;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.beerpro.R;
import ch.beerpro.presentation.helpers.ViewPagerAdapter;
import ch.beerpro.presentation.search.SearchActivity;
import com.google.android.material.tabs.TabLayout;

public class HomeScreenSearchFragment extends Fragment {

    private static final String TAG = "HomeScreenActSearch";
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)

    TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    public HomeScreenSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_search_screen, container, false);
        ButterKnife.bind(this, rootView);

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new BeerCategoriesFragment(), "Bierart");
        adapter.addFragment(new BeerManufacturersFragment(), "Brauerei");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    @OnClick(R.id.beerSearchButton)
    public void openSearchActivity(View beerSearchButton) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(getActivity(), beerSearchButton, "search");

        startActivity(intent, options.toBundle());
    }
}
