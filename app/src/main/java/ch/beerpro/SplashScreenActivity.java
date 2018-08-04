package ch.beerpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import ch.beerpro.home.HomeScreenActivity;
import ch.beerpro.models.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";

    private static final int RC_SIGN_IN = 123;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Log.i(TAG, "No user found, redirect to Login screen");

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build());

                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                        .setLogo(R.drawable.beer_glass_icon).setTheme(R.style.LoginScreenTheme).build(), RC_SIGN_IN);
            }, 1000);


        } else {
            Log.i(TAG, "User found, redirect to Home screen");
            // We save all logged in users so we can query them later on.
            User user = new User(null, currentUser.getDisplayName(), currentUser.getPhotoUrl().toString());
            FirebaseFirestore.getInstance().collection(User.COLLECTION).document(currentUser.getUid()).set(user);

            startActivity(new Intent(this, HomeScreenActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                Log.i(TAG, "User signed in");
            } else if (response == null) {
                Log.w(TAG, "User cancelled signing in");
            } else {
                Log.e(TAG, "Error logging in", response.getError());
            }
        }
    }

}
