package ch.beerpro.search;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.R;
import ch.beerpro.search.SearchResultFragment.OnItemSelectedListener;
import ch.beerpro.dummy.Beer;
import com.squareup.picasso.Picasso;


public class SearchResultRecyclerViewAdapter extends ListAdapter<Beer, SearchResultRecyclerViewAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Beer> DIFF_CALLBACK = new DiffUtil.ItemCallback<Beer>() {
        @Override
        public boolean areItemsTheSame(@NonNull Beer oldUser, @NonNull Beer newUser) {
            return oldUser.id.equals(newUser.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Beer oldUser, @NonNull Beer newUser) {
            return oldUser.equals(newUser);
        }
    };

    private final OnItemSelectedListener listener;

    public SearchResultRecyclerViewAdapter(OnItemSelectedListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_searchresult_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.manufacturer)
        TextView manufacturer;

        @BindView(R.id.category)
        TextView category;

        @BindView(R.id.photo)
        ImageView photo;

        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        @BindView(R.id.numRatings)
        TextView numRatings;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        void bind(Beer item, SearchResultFragment.OnItemSelectedListener listener) {
            name.setText(item.name);
            manufacturer.setText(item.manufacturer);
            category.setText(item.category);
            name.setText(item.name);
            Picasso.get().load(item.photo).resize(240, 240).centerInside().into(photo);
            ratingBar.setNumStars(5);
            ratingBar.setRating(item.avgRating);
            numRatings.setText(itemView.getResources().getString(R.string.fmt_num_ratings, item.numRatings));
            itemView.setOnClickListener(v -> listener.onSearchResultListItemSelected(item));
        }
    }
}
