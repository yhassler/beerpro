package ch.beerpro.presentation.profile.myfridge;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.GlideApp;
import ch.beerpro.R;
import ch.beerpro.domain.models.FridgeContent;


import ch.beerpro.presentation.utils.EntityPairDiffItemCallback;
import ch.beerpro.domain.models.Beer;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;

public class FridgeRecyclerViewAdapter extends ListAdapter<Pair<FridgeContent, Beer>, FridgeRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "FridgeRecyclerViewAda";

    private static final DiffUtil.ItemCallback<Pair<FridgeContent, Beer>> DIFF_CALLBACK = new EntityPairDiffItemCallback<>();

    private final OnFridgeContentItemInteractionListener listener;

    public FridgeRecyclerViewAdapter(OnFridgeContentItemInteractionListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public FridgeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_my_fridge_listentry, parent, false);
        return new FridgeRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FridgeRecyclerViewAdapter.ViewHolder holder, int position) {
        Pair<FridgeContent, Beer> item = getItem(position);
        holder.bind(item.first, item.second, listener);
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

        @BindView(R.id.addedAt)
        TextView addedAt;

        @BindView(R.id.removeFromFridge)
        Button remove;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        void bind(FridgeContent fridgeContent, Beer item, OnFridgeContentItemInteractionListener listener) {
            name.setText(item.getName());
            manufacturer.setText(item.getManufacturer());
            category.setText(item.getCategory());
            name.setText(item.getName());
            GlideApp.with(itemView).load(item.getPhoto()).apply(new RequestOptions().override(240, 240).centerInside())
                    .into(photo);
            ratingBar.setNumStars(5);
            ratingBar.setRating(item.getAvgRating());
            numRatings.setText(itemView.getResources().getString(R.string.fmt_num_ratings, item.getNumRatings()));
            itemView.setOnClickListener(v -> listener.onMoreClickedListener(photo, item));

            String formattedDate =
                    DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(fridgeContent.getAddedAt());
            addedAt.setText(formattedDate);
            remove.setOnClickListener(v -> listener.onFridgeClickedListener(item));
        }

    }
}
