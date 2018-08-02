package ch.beerpro.search.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.R;
import ch.beerpro.models.Beer;
import ch.beerpro.search.MyBeersFragment.OnItemSelectedListener;
import ch.beerpro.search.utils.BeerDiffItemCallback;
import com.squareup.picasso.Picasso;


public class MyBeersRecyclerViewAdapter extends ListAdapter<Beer, MyBeersRecyclerViewAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Beer> DIFF_CALLBACK = new BeerDiffItemCallback();

    private final OnItemSelectedListener listener;

    public MyBeersRecyclerViewAdapter(OnItemSelectedListener listener) {
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

        void bind(Beer item, OnItemSelectedListener listener) {
            name.setText(item.name);
            manufacturer.setText(item.manufacturer);
            category.setText(item.category);
            name.setText(item.name);
            Picasso.get().load(item.photo).resize(240, 240).centerInside().into(photo);
            ratingBar.setNumStars(5);
            ratingBar.setRating(item.avgRating);
            numRatings.setText(itemView.getResources().getString(R.string.fmt_num_ratings, item.numRatings));
            itemView.setOnClickListener(v -> listener.onMyBeersListItemSelected(item));
        }
    }
}
