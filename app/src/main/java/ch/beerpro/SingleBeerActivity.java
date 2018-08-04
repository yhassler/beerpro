package ch.beerpro;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.models.Beer;
import ch.beerpro.models.Rating;
import ch.beerpro.search.adapters.MyBeersRecyclerViewAdapter;
import ch.beerpro.single.OnRatingLikedListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SingleBeerActivity extends AppCompatActivity implements OnRatingLikedListener {

    public static final String ITEM = "item";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.photo)
    ImageView photo;

    @BindView(R.id.avgRating)
    TextView avgRating;

    @BindView(R.id.numRatings)
    TextView numRatings;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.manufacturer)
    TextView manufacturer;

    @BindView(R.id.category)
    TextView category;

    @BindView(R.id.addRatingBar)
    RatingBar addRatingBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RatingsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_beer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RatingsRecyclerViewAdapter(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        recyclerView.setAdapter(adapter);

        Beer item = (Beer) getIntent().getExtras().getSerializable(ITEM);
        updateBeer(item);

        SingleBeerViewModel model = ViewModelProviders.of(this).get(SingleBeerViewModel.class);
        model.getBeer(item.getId()).observe(this, this::updateBeer);
        model.getRatings(item.getId()).observe(this, this::updateRatings);

        addRatingBar.setOnRatingBarChangeListener(this::addNewRating);
    }

    private void addNewRating(RatingBar ratingBar, float v, boolean b) {
        Beer item = (Beer) getIntent().getExtras().getSerializable(ITEM);
        Intent intent = new Intent(this, RatingActivity.class);
        intent.putExtra(RatingActivity.ITEM, item);
        intent.putExtra(RatingActivity.RATING, v);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, addRatingBar, "rating");
        startActivity(intent, options.toBundle());
    }

    private void updateBeer(Beer item) {
        name.setText(item.getName());
        manufacturer.setText(item.getManufacturer());
        category.setText(item.getCategory());
        name.setText(item.getName());
        Picasso.get().load(item.getPhoto()).resize(120, 160).centerInside().into(photo);
        ratingBar.setNumStars(5);
        ratingBar.setRating(item.getAvgRating());
        numRatings.setText(getResources().getString(R.string.fmt_num_ratings, item.getNumRatings()));
        collapsingToolbar.setTitle(item.getName());
    }

    private void updateRatings(List<Rating> ratings) {
        adapter.submitList(ratings);
    }

    @Override
    public void onRatingLikedListener(String ratingId) {

    }
}
