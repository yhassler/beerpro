package ch.beerpro.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.beerpro.R;

import java.util.Arrays;


public class SearchSuggestionsFragment extends Fragment {

    private OnItemSelectedListener mListener;

    public SearchSuggestionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchsuggestion_list, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(context, layoutManager.getOrientation()));

        recyclerView.setAdapter(new SearchSuggestionsRecyclerViewAdapter(Arrays.asList("IPA"),
                Arrays.asList("Moretti Limone", "Lager Hell"), mListener));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            mListener = (OnItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnItemSelectedListener {
        void onSearchSuggestionListItemSelected(String text);
    }
}
