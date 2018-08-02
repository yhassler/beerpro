package ch.beerpro.search;

import android.content.Context;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import ch.beerpro.R;
import ch.beerpro.dummy.Beer;

import android.os.Bundle;

import ch.beerpro.viewmodels.SearchActivityViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.common.base.Strings;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchActivity extends AppCompatActivity implements SearchResultFragment.OnListFragmentInteractionListener,
        SearchSuggestionsFragment.OnListFragmentInteractionListener, MyBeersFragment.OnListFragmentInteractionListener {

    private SearchActivityViewModel model;
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

        model = ViewModelProviders.of(this).get(SearchActivityViewModel.class);

        FirebaseFirestore.setLoggingEnabled(true);
    }

    @Override
    public void onListFragmentInteraction(Beer item) {

    }

    @Override
    public void onSearch(String text) {
        searchEditText.setText(text);
        searchEditText.setSelection(text.length());
        hideKeyboard();
        handleSearch(text);
    }

    private void handleSearch(String text) {
        model.setCurrentSearchTerm(text);
        adapter.setShowSuggestions(Strings.isNullOrEmpty(text));
        adapter.notifyDataSetChanged();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }
}
