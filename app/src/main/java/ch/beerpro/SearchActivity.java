package ch.beerpro;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import ch.beerpro.dummy.DummyContent;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class SearchActivity extends AppCompatActivity
        implements SearchResultFragment.OnListFragmentInteractionListener {

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

        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        setupViewPager(viewPager, tabLayout);
    }


    private void setupViewPager(ViewPager viewPager, TabLayout tabLayout) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchResultFragment(), "Alle Biere");
        adapter.addFragment(new SearchResultFragment(), "Meine Biere");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.SearchResult item) {

    }

    @Override
    public void onSearch(String text) {
        searchEditText.setText(text);
        handleSearch(text);
    }

    private void handleSearch(String text) {
        unfocusSearchInput();
        hideKeyboard();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

    private void unfocusSearchInput() {
        // https://stackoverflow.com/a/17508213/313873
        searchEditText.setFocusableInTouchMode(false);
        searchEditText.setFocusable(false);
        searchEditText.setFocusableInTouchMode(true);
        searchEditText.setFocusable(true);
    }
}
