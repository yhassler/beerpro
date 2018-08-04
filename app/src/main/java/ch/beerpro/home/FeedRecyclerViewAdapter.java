package ch.beerpro.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.R;
import ch.beerpro.helpers.EntityDiffItemCallback;
import ch.beerpro.models.Rating;
import ch.beerpro.single.OnRatingLikedListener;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import java.text.DateFormat;


public class FeedRecyclerViewAdapter extends ListAdapter<Rating, FeedRecyclerViewAdapter.ViewHolder> {

    private static final EntityDiffItemCallback<Rating> DIFF_CALLBACK = new EntityDiffItemCallback<>();

    private final OnRatingLikedListener listener;
    private final FirebaseUser user;

    public FeedRecyclerViewAdapter(OnRatingLikedListener listener, FirebaseUser user) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_feed_ratings_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comment)
        TextView comment;

        @BindView(R.id.beerName)
        TextView beerName;

        @BindView(R.id.avatar)
        ImageView avatar;

        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        @BindView(R.id.authorName)
        TextView authorName;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.numLikes)
        TextView numLikes;

        @BindView(R.id.like)
        ImageView like;

        @BindView(R.id.photo)
        ImageView photo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        void bind(Rating item, OnRatingLikedListener listener) {
            beerName.setText(item.getBeerName());
            comment.setText(item.getComment());

            ratingBar.setNumStars(5);
            ratingBar.setRating(item.getRating());
            String formattedDate =
                    DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(item.getCreationDate());
            date.setText(formattedDate);

            if (item.getPhoto() != null) {
                Picasso.get().load(item.getPhoto()).into(photo);
            } else {
                photo.setVisibility(View.GONE);
            }

            authorName.setText(item.getUserName());
            Picasso.get().load(item.getUserPhoto()).transform(new CropCircleTransformation()).into(avatar);

            numLikes.setText(itemView.getResources().getString(R.string.fmt_num_ratings, item.getLikes().size()));
            if(item.getLikes().containsKey(user.getUid())) {
                like.setColorFilter(itemView.getResources().getColor(R.color.colorPrimary));
            } else {
                like.setColorFilter(itemView.getResources().getColor(android.R.color.darker_gray));
            }
            if (listener != null) {
                like.setOnClickListener(v -> listener.onRatingLikedListener(item));
            }
        }
    }
}
