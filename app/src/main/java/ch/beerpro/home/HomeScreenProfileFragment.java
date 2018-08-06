package ch.beerpro.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ch.beerpro.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.Fragment;

public class HomeScreenProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    public HomeScreenProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_profile_screen, container, false);
        ImageView userProfileImageView = rootView.findViewById(R.id.userProfileImageView);
        TextView userProfileNameText = rootView.findViewById(R.id.userProfileNameText);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            userProfileNameText.setText(name);
            Uri photoUrl = user.getPhotoUrl();
            Glide.with(this).load(photoUrl).apply(new RequestOptions().circleCrop()).into(userProfileImageView);

        }

        return rootView;
    }

}
