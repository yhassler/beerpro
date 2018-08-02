package ch.beerpro.search;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.beerpro.R;
import ch.beerpro.dummy.Beer;
import ch.beerpro.viewmodels.SearchActivityViewModel;

import java.util.List;

public class SearchResultFragment extends Fragment {

    private static final String TAG = "SearchResultFragment";

    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private View emptyView;
    private SearchResultRecyclerViewAdapter adapter;

    public SearchResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchresult_list, container, false);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.emptyView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SearchResultRecyclerViewAdapter(mListener);

        SearchActivityViewModel model = ViewModelProviders.of(getActivity()).get(SearchActivityViewModel.class);
        model.getFilteredBeers().observe(getActivity(), this::handleBeersChanged);

        recyclerView.setAdapter(adapter);
        return view;
    }

    private void handleBeersChanged(List<Beer> beers) {
        adapter.submitList(beers);
        if (beers.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Beer item);
    }
}
