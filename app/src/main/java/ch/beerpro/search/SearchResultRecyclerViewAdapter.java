package ch.beerpro.search;

import android.widget.ImageView;
import android.widget.RatingBar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.beerpro.R;
import ch.beerpro.search.SearchResultFragment.OnListFragmentInteractionListener;
import ch.beerpro.dummy.DummyContent.SearchResult;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder> {

    public static final int VIEW_TYPE_SEARCH_RESULTS = 1;
    public static final int VIEW_TYPE_EMPTY_RESULTS = 2;

    private final List<SearchResult> searchResults;

    private final OnListFragmentInteractionListener listener;

    public SearchResultRecyclerViewAdapter(List<SearchResult> searchResults,
                                           OnListFragmentInteractionListener listener) {
        this.searchResults = searchResults;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasEmptySearchResults()) {
            return VIEW_TYPE_EMPTY_RESULTS;
        } else {
            return VIEW_TYPE_SEARCH_RESULTS;
        }
    }

    private boolean hasEmptySearchResults() {
        return searchResults != null && searchResults.isEmpty();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view;
        switch (viewType) {
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
        if (hasEmptySearchResults()) {
            return;
        } else {
            SearchResult item = searchResults.get(position);
            holder.mItem = item;
            holder.mContentView.setText(item.title);
            holder.mSubtitleView.setText(item.subtitle);
            holder.mCategoryView.setText(item.category);
            holder.mContentView.setText(item.title);
            Picasso.get().load(item.photo).resize(240, 240).centerInside().into(holder.mImageView);
            holder.mRatingBar.setNumStars(5);
            holder.mRatingBar.setRating(item.rating);
            holder.mNumberOfRatings
                    .setText(holder.mView.getResources().getString(R.string.fmt_num_ratings, item.numberOfRatings));

            holder.mView.setOnClickListener(v -> listener.onListFragmentInteraction(holder.mItem));
        }
    }

    @Override
    public int getItemCount() {
        if (hasEmptySearchResults()) {
            // the empty page placeholder
            return 1;
        } else {
            return searchResults.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mContentView;
        final TextView mSubtitleView;
        final TextView mCategoryView;
        final ImageView mImageView;
        final RatingBar mRatingBar;
        final TextView mNumberOfRatings;
        SearchResult mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
            mSubtitleView = view.findViewById(R.id.subtitle);
            mCategoryView = view.findViewById(R.id.category);
            mImageView = view.findViewById(R.id.photo);
            mRatingBar = view.findViewById(R.id.ratingBar);
            mNumberOfRatings = view.findViewById(R.id.numberOfRatings);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
