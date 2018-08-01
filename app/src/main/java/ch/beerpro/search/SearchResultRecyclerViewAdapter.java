package ch.beerpro.search;

import android.util.Log;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.beerpro.R;
import ch.beerpro.dummy.DummyContent;
import ch.beerpro.search.SearchResultFragment.OnListFragmentInteractionListener;
import ch.beerpro.dummy.DummyContent.Beer;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder>
        implements Filterable {

    private static final String TAG = "SearchResultRecyclerVie";

    private final OnListFragmentInteractionListener listener;
    private ArrayList<Beer> listEntries;
    private ArrayList<Beer> listEntriesFiltered;

    public SearchResultRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        Log.i(TAG, "constructed");
        this.listener = listener;
        this.listEntries = new ArrayList<>();
        this.listEntriesFiltered = this.listEntries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_searchresult_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Beer item = listEntriesFiltered.get(position);
        holder.mItem = item;
        holder.mContentView.setText(item.name);
        holder.mManufacturerView.setText(item.manufacturer);
        holder.mCategoryView.setText(item.category);
        holder.mContentView.setText(item.name);
        Picasso.get().load(item.photo).resize(240, 240).centerInside().into(holder.mImageView);
        holder.mRatingBar.setNumStars(5);
        holder.mRatingBar.setRating(item.avgRating);
        holder.mnumRatings.setText(holder.mView.getResources().getString(R.string.fmt_num_ratings, item.numRatings));
        holder.mView.setOnClickListener(v -> listener.onListFragmentInteraction(holder.mItem));
    }

    @Override
    public int getItemCount() {
        return listEntriesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Beer> filtered = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    filtered = listEntries;
                } else {
                    String query = charSequence.toString();
                    for (Beer beer : listEntries) {
                        if (beer.name.toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(beer);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                listEntriesFiltered = (ArrayList<Beer>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setListItems(List<Beer> beers) {
        Log.i(TAG, "setListItems");
        listEntries.clear();
        listEntries.addAll(beers);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mContentView;
        final TextView mManufacturerView;
        final TextView mCategoryView;
        final ImageView mImageView;
        final RatingBar mRatingBar;
        final TextView mnumRatings;
        Beer mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.name);
            mManufacturerView = view.findViewById(R.id.manufacturer);
            mCategoryView = view.findViewById(R.id.category);
            mImageView = view.findViewById(R.id.photo);
            mRatingBar = view.findViewById(R.id.ratingBar);
            mnumRatings = view.findViewById(R.id.numRatings);
        }
    }
}
