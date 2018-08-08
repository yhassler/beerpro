package ch.beerpro.presentation.wishlist;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.widget.ImageView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Wish;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ch.beerpro.R;
import ch.beerpro.presentation.details.DetailsActivity;
import lombok.val;

import java.util.List;

public class WishlistActivity extends AppCompatActivity implements OnWishlistItemInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private WishlistViewModel model;
    private WishlistRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_activity_wishlist));


        model = ViewModelProviders.of(this).get(WishlistViewModel.class);
        model.getMyWishlistWithBeers().observe(this, this::updateWishlist);

        val layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new WishlistRecyclerViewAdapter(this);

        recyclerView.setAdapter(adapter);

    }

    private void updateWishlist(List<Pair<Wish, Beer>> pairs) {
        adapter.submitList(pairs);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMoreClickedListener(ImageView animationSource, Beer beer) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.ITEM_ID, beer.getId());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, animationSource, "image");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onWishClickedListener(Beer beer) {
        model.toggleItemInWishlist(beer.getId());
    }
}
