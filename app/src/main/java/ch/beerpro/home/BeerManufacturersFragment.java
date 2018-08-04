package ch.beerpro.home;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.R;
import ch.beerpro.helpers.GridSpacingItemDecoration;


public class BeerManufacturersFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private OnItemSelectedListener mListener;

    public BeerManufacturersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beer_manufacturers, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, false, 0));

        BeerManufacturersRecyclerViewAdapter adapter = new BeerManufacturersRecyclerViewAdapter(mListener);

        HomeScreenViewModel model = ViewModelProviders.of(getActivity()).get(HomeScreenViewModel.class);
        model.getBeerManufacturers().observe(getActivity(), adapter::submitList);

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            mListener = (OnItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnItemSelectedListener {
        void onBeerManufacturerSelected(String name);
    }
}
