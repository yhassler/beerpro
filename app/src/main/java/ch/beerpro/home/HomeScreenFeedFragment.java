package ch.beerpro.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.R;
import ch.beerpro.models.Rating;
import ch.beerpro.single.OnRatingLikedListener;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenFeedFragment extends Fragment
        implements OnRatingLikedListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FeedFragment";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private FeedRecyclerViewAdapter adapter;
    private HomeScreenViewModel model;

    public HomeScreenFeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_feed_screen, container, false);
        ButterKnife.bind(this, rootView);

        model = ViewModelProviders.of(this).get(HomeScreenViewModel.class);
        model.getAllRatings().observe(this, this::updateRatings);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FeedRecyclerViewAdapter(this, model.getCurrentUser());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), layoutManager.getOrientation()));

        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        return rootView;
    }

    private void updateRatings(List<Rating> ratings) {
        adapter.submitList(new ArrayList<>(ratings));
    }

    @Override
    public void onRatingLikedListener(Rating rating) {
        model.toggleLike(rating);
    }

    @Override
    public void onRefresh() {
        updateRatings(model.getAllRatings().getValue());
        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }
}
