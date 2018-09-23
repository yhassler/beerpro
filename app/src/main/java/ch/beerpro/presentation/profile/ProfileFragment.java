package ch.beerpro.presentation.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.beerpro.GlideApp;
import ch.beerpro.R;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.presentation.MainViewModel;
import ch.beerpro.presentation.profile.mybeers.MyBeersActivity;
import ch.beerpro.domain.models.MyBeer;
import ch.beerpro.presentation.profile.myratings.MyRatingsActivity;
import ch.beerpro.presentation.profile.mywishlist.WishlistActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Because the profile view is not a whole activity but rendered as part of the MainActivity in a tab, we use a so-called fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    ImageView userProfileImageView;

    TextView userProfileNameText;

    TextView myBeersCount;

    TextView myFridgeCount;

    TextView myRatingsCount;

    TextView myWishlistCount;

    private MainViewModel model;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        /* Fragments also have a layout file, this one is in res/layout/fragment_profile_screen.xml: */
        View rootView = inflater.inflate(R.layout.fragment_profile_screen, container, false);

        userProfileImageView = rootView.findViewById(R.id.userProfileImageView);
        userProfileNameText = rootView.findViewById(R.id.userProfileNameText);
        myBeersCount = rootView.findViewById(R.id.myBeersCount);
        myFridgeCount = rootView.findViewById(R.id.myFridgeCount);
        myRatingsCount = rootView.findViewById(R.id.myRatingsCount);
        myWishlistCount = rootView.findViewById(R.id.myWishlistCount);

        rootView.findViewById(R.id.myRatings).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyRatingsActivity.class);
            startActivity(intent);
        });

        rootView.findViewById(R.id.myWishlist).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WishlistActivity.class);
            startActivity(intent);
        });

        rootView.findViewById(R.id.myBeers).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyBeersActivity.class);
            startActivity(intent);
        });

        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.getMyWishlist().observe(this, this::updateWishlistCount);
        model.getMyRatings().observe(this, this::updateRatingsCount);
        model.getMyBeers().observe(this, this::updateMyBeersCount);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            userProfileNameText.setText(name);
            Uri photoUrl = user.getPhotoUrl();
            GlideApp.with(this).load(photoUrl).apply(new RequestOptions().circleCrop()).into(userProfileImageView);
        }

        return rootView;
    }

    private void updateMyBeersCount(List<MyBeer> myBeers) {
        myBeersCount.setText(String.valueOf(myBeers.size()));
    }

    private void updateRatingsCount(List<Rating> ratings) {
        myRatingsCount.setText(String.valueOf(ratings.size()));
    }

    private void updateWishlistCount(List<Wish> wishes) {
        myWishlistCount.setText(String.valueOf(wishes.size()));
    }
}
