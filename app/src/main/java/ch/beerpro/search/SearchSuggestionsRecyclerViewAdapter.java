package ch.beerpro.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import ch.beerpro.R;
import ch.beerpro.search.SearchSuggestionsFragment.OnItemSelectedListener;

import java.util.List;


public class SearchSuggestionsRecyclerViewAdapter
        extends RecyclerView.Adapter<SearchSuggestionsRecyclerViewAdapter.ViewHolder> {

    public static final int VIEW_TYPE_NO_SEARCH_HEADER = 1;
    public static final int VIEW_TYPE_NO_SEARCH_ENTRY = 2;

    /*
     * LETZTE SUCHEN
     *  - term
     *  - term
     *  - term
     * BELIEBTE SUCHEN
     *  - term 1
     *  - term 10
     * */
    private final List<String> previousSearches;
    private final List<String> popularSearches;
    private final OnItemSelectedListener listener;

    public SearchSuggestionsRecyclerViewAdapter(List<String> previousSearches, List<String> popularSearches,
                                                OnItemSelectedListener listener) {
        this.previousSearches = previousSearches;
        this.popularSearches = popularSearches;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position - 1 == previousSearches.size()) {
            return VIEW_TYPE_NO_SEARCH_HEADER;
        } else {
            return VIEW_TYPE_NO_SEARCH_ENTRY;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view;
        switch (viewType) {
            case VIEW_TYPE_NO_SEARCH_HEADER:
                view = layoutInflater.inflate(R.layout.fragment_searchsuggestion_header, parent, false);
                break;
            default:
                view = layoutInflater.inflate(R.layout.fragment_searchsuggestion_entry, parent, false);
                break;
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 0) {
            holder.mContentView.setText("LETZTE SUCHEN");
        } else if (position - 1 == previousSearches.size()) {
            holder.mContentView.setText("BELIEBTE SUCHEN");
        } else if (position <= previousSearches.size()) {
            String text = previousSearches.get(position - 1);
            holder.mContentView.setText(text);
            holder.mView.setOnClickListener(v -> listener.onSearchSuggestionListItemSelected(text));
        } else {
            String text = popularSearches.get(position - 2 - previousSearches.size());
            holder.mContentView.setText(text);
            holder.mView.setOnClickListener(v -> listener.onSearchSuggestionListItemSelected(text));
        }
    }

    @Override
    public int getItemCount() {
        return previousSearches.size() + popularSearches.size() + 2 /*headers*/;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
        }
    }
}
