package ch.beerpro.helpers;

import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import ch.beerpro.models.Beer;
import ch.beerpro.models.Entity;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.firestore.ChangeEventListener;
import com.firebase.ui.firestore.FirestoreArray;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.firebase.firestore.*;

import javax.annotation.Nullable;
import java.util.List;

public class FirestoreQueryLiveData<T extends Entity> extends LiveData<T> implements EventListener<DocumentSnapshot> {

    private static final String TAG = "FQueryLiveData";

    private final Handler handler = new Handler();
    private final DocumentReference documentReference;
    private boolean listenerRemovePending = false;
    private ListenerRegistration listenerRegistration;
    private final Runnable removeListener = new Runnable() {
        @Override
        public void run() {
            listenerRegistration.remove();
            listenerRemovePending = false;
        }
    };

    private EntityClassSnapshotParser<T> parser;

    public FirestoreQueryLiveData(DocumentReference documentReference, Class<T> modelClass) {
        this.documentReference = documentReference;
        parser = new EntityClassSnapshotParser<T>(modelClass);
    }

    @Override
    protected void onActive() {
        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener);
        } else if (listenerRegistration == null) {
            listenerRegistration = documentReference.addSnapshotListener(this);
        }
        listenerRemovePending = false;
    }

    @Override
    protected void onInactive() {
        handler.postDelayed(removeListener, 2000);
        listenerRemovePending = true;
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        if (documentSnapshot != null && documentSnapshot.exists()) {
            setValue(parser.parseSnapshot(documentSnapshot));
        }
    }
}