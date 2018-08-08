package ch.beerpro;

import android.app.Application;
import com.google.firebase.firestore.FirebaseFirestore;
import net.danlew.android.joda.JodaTimeAndroid;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseFirestore.setLoggingEnabled(true);

        JodaTimeAndroid.init(this);
    }
}