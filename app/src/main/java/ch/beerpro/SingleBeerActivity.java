package ch.beerpro;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.models.Beer;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import com.squareup.picasso.Picasso;

public class SingleBeerActivity extends AppCompatActivity {

    public static final String ITEM = "item";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.fab)
    FloatingActionButton fab;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_beer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Beer item = (Beer) getIntent().getExtras().getSerializable(ITEM);
        update(item);

        SingleBeerActivityViewModel model = ViewModelProviders.of(this).get(SingleBeerActivityViewModel.class);
        model.getBeer(item.getId()).observe(this, this::update);
    }

    private void update(Beer item) {
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
}
