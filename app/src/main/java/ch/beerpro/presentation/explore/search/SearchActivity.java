package ch.beerpro.presentation.explore.search;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import ch.beerpro.R;
import ch.beerpro.presentation.details.DetailsActivity;
import ch.beerpro.domain.models.Beer;

import android.os.Bundle;

import ch.beerpro.presentation.profile.mybeers.OnMyBeerItemInteractionListener;
import ch.beerpro.presentation.explore.search.beers.SearchResultFragment;
import ch.beerpro.presentation.explore.search.suggestions.SearchSuggestionsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.common.base.Strings;

public class SearchActivity extends AppCompatActivity
        implements SearchResultFragment.OnItemSelectedListener, SearchSuggestionsFragment.OnItemSelectedListener,
        OnMyBeerItemInteractionListener {

    private SearchViewModel model;
    private ViewPagerAdapter adapter;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                handleSearch(searchEditText.getText().toString());
            }
            return false;
        });

        findViewById(R.id.clearFilterButton).setOnClickListener(view -> {
            searchEditText.setText(null);
            handleSearch(null);
        });

        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        model = ViewModelProviders.of(this).get(SearchViewModel.class);
    }

    @Override
    public void onSearchResultListItemSelected(View animationSource, Beer item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.ITEM_ID, item.getId());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, animationSource, "image");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onSearchSuggestionListItemSelected(String text) {
        searchEditText.setText(text);
        searchEditText.setSelection(text.length());
        hideKeyboard();
        handleSearch(text);
    }

    private void handleSearch(String text) {
        model.setSearchTerm(text);
        adapter.setShowSuggestions(Strings.isNullOrEmpty(text));
        adapter.notifyDataSetChanged();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
        }
    }

    @Override
    public void onMoreClickedListener(ImageView photo, Beer item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.ITEM_ID, item.getId());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, photo, "image");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onWishClickedListener(Beer item) {
        model.toggleItemInWishlist(item.getId());
    }
}
