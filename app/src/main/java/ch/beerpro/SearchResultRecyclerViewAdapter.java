package ch.beerpro;

import android.util.Log;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.beerpro.SearchResultFragment.OnListFragmentInteractionListener;
import ch.beerpro.dummy.DummyContent.SearchResult;
import com.squareup.picasso.Picasso;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SearchResult} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder> {

    public static final int VIEW_TYPE_NO_SEARCH_HEADER = 982;
    public static final int VIEW_TYPE_NO_SEARCH_ENTRY = 981;
    public static final int VIEW_TYPE_SEARCH_RESULTS = 999;
    public static final int VIEW_TYPE_EMPTY_RESULTS = 983;

    private final List<SearchResult> searchResults;

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
    private final OnListFragmentInteractionListener listener;

    public SearchResultRecyclerViewAdapter(List<SearchResult> searchResults, List<String> previousSearches,
                                           List<String> popularSearches, OnListFragmentInteractionListener listener) {
        this.searchResults = searchResults;
        this.previousSearches = previousSearches;
        this.popularSearches = popularSearches;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasNoSearchResults()) {
            if (position == 0 || position - 1 == previousSearches.size()) {
                return VIEW_TYPE_NO_SEARCH_HEADER;
            } else {
                return VIEW_TYPE_NO_SEARCH_ENTRY;
            }
        } else if (hasEmptySearchResults()) {
            return VIEW_TYPE_EMPTY_RESULTS;
        } else {
            return VIEW_TYPE_SEARCH_RESULTS;
        }
    }

    private boolean hasEmptySearchResults() {
        return searchResults != null && searchResults.isEmpty();
    }

    private boolean hasNoSearchResults() {
        return searchResults == null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view;
        switch (viewType) {
            case VIEW_TYPE_NO_SEARCH_HEADER:
                view = layoutInflater.inflate(R.layout.fragment_searchresult_header, parent, false);
                break;
            case VIEW_TYPE_NO_SEARCH_ENTRY:
                view = layoutInflater.inflate(R.layout.fragment_searchresult_entry, parent, false);
                break;
            case VIEW_TYPE_SEARCH_RESULTS:
                view = layoutInflater.inflate(R.layout.fragment_searchresult_result, parent, false);
                break;
            default:
                view = layoutInflater.inflate(R.layout.fragment_searchresult_empty, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (hasNoSearchResults()) {
            if (position == 0) {
                holder.mContentView.setText("LETZTE SUCHEN");
            } else if (position - 1 == previousSearches.size()) {
                holder.mContentView.setText("BELIEBTE SUCHEN");
            } else if (position <= previousSearches.size()) {
                String text = previousSearches.get(position - 1);
                holder.mContentView.setText(text);
                holder.mView.setOnClickListener(v -> listener.onSearch(text));
            } else {
                String text = popularSearches.get(position - 2 - previousSearches.size());
                holder.mContentView.setText(text);
                holder.mView.setOnClickListener(v -> listener.onSearch(text));
            }
        } else if (hasEmptySearchResults()) {
            return;
        } else {
            SearchResult item = searchResults.get(position);
            holder.mItem = item;
            holder.mContentView.setText(item.title);
            holder.mSubtitleView.setText(item.subtitle);
            holder.mCategoryView.setText(item.category);
            holder.mContentView.setText(item.title);
            Log.i("SearchResultAdapter", item.photo.toString());
            Picasso.get().load(item.photo).resize(240, 240).centerInside().into(holder.mImageView);

            holder.mView.setOnClickListener(v -> listener.onListFragmentInteraction(holder.mItem));
        }
    }

    @Override
    public int getItemCount() {
        if (hasNoSearchResults()) {
            return previousSearches.size() + popularSearches.size() + 2 /*headers*/;
        } else if (hasEmptySearchResults()) {
            // the empty page placeholder
            return 1;
        } else {
            return searchResults.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        private final TextView mSubtitleView;
        private final TextView mCategoryView;
        private final ImageView mImageView;
        public SearchResult mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
            mSubtitleView = view.findViewById(R.id.subtitle);
            mCategoryView = view.findViewById(R.id.category);
            mImageView = view.findViewById(R.id.photo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
