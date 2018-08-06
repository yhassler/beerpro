package ch.beerpro.single;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.beerpro.R;
import ch.beerpro.models.Beer;
import ch.beerpro.models.Rating;
import ch.beerpro.rating.RatingActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class SingleBeerActivity extends AppCompatActivity implements OnRatingLikedListener {

    public static final String ITEM_ID = "item_id";
    private static final String TAG = "SingleBeerActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;

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

    private SingleBeerViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_beer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        toolbar.setTitleTextColor(Color.alpha(0));

        String beerId = getIntent().getExtras().getString(ITEM_ID);

        model = ViewModelProviders.of(this).get(SingleBeerViewModel.class);
        model.setBeerId(beerId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RatingsRecyclerViewAdapter(this, model.getCurrentUser());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        model.getBeer().observe(this, this::updateBeer);
        model.getRatings().observe(this, this::updateRatings);

        recyclerView.setAdapter(adapter);
        addRatingBar.setOnRatingBarChangeListener(this::addNewRating);
    }

    private void addNewRating(RatingBar ratingBar, float v, boolean b) {
        Intent intent = new Intent(this, RatingActivity.class);
        intent.putExtra(RatingActivity.ITEM, model.getBeer().getValue());
        intent.putExtra(RatingActivity.RATING, v);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, addRatingBar, "rating");
        startActivity(intent, options.toBundle());
    }

    @OnClick(R.id.actionsButton)
    public void showBottomSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.single_bottom_sheet_dialog, null);

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
    }


    private void updateBeer(Beer item) {
        name.setText(item.getName());
        manufacturer.setText(item.getManufacturer());
        category.setText(item.getCategory());
        name.setText(item.getName());
        Glide.with(this).load(item.getPhoto()).apply(new RequestOptions().override(120, 160).centerInside())
                .into(photo);
        ratingBar.setNumStars(5);
        ratingBar.setRating(item.getAvgRating());
        avgRating.setText(getResources().getString(R.string.fmt_avg_rating, item.getAvgRating()));
        numRatings.setText(getResources().getString(R.string.fmt_ratings, item.getNumRatings()));
        toolbar.setTitle(item.getName());
    }

    private void updateRatings(List<Rating> ratings) {
        adapter.submitList(new ArrayList<>(ratings));
    }

    @Override
    public void onRatingLikedListener(Rating rating) {
        model.toggleLike(rating);
    }


    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
