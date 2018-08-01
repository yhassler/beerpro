package ch.beerpro.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.beerpro.R;
import ch.beerpro.dummy.DummyContent.Beer;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MyBeersFragment extends Fragment {

    private static final String TAG = "MyBeersFragment";

    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private View emptyView;

    public MyBeersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchresult_list, container, false);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.emptyView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        // TODO show user's beers!
        Query query = FirebaseFirestore.getInstance().collection("beers");

        FirestoreRecyclerOptions<Beer> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Beer>().setLifecycleOwner(this).setQuery(query, Beer.class)
                        .build();

        MyBeersRecyclerViewAdapter adapter = new MyBeersRecyclerViewAdapter(firestoreRecyclerOptions, mListener);
        recyclerView.setAdapter(adapter);

        MyAdapterDataObserver adapterDataObserver = new MyAdapterDataObserver(adapter);
        adapter.registerAdapterDataObserver(adapterDataObserver);


        return view;
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

    private class MyAdapterDataObserver extends RecyclerView.AdapterDataObserver {

        private final RecyclerView.Adapter adapter;

        public MyAdapterDataObserver(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onChanged() {
            super.onChanged();
            checkEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkEmpty();
        }

        void checkEmpty() {
            if (adapter.getItemCount() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
}
