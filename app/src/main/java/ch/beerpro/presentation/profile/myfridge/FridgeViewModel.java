package ch.beerpro.presentation.profile.myfridge;

import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.data.repositories.BeersRepository;
import ch.beerpro.data.repositories.CurrentUser;

import ch.beerpro.domain.models.Beer;

import com.google.android.gms.tasks.Task;

import java.util.List;

import ch.beerpro.domain.models.FridgeContent;
import ch.beerpro.data.repositories.FridgeRepository;

public class FridgeViewModel extends ViewModel implements CurrentUser {

    private static final String TAG = "FridgeViewModel";

    private final MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private final FridgeRepository fridgeRepository;
    private final BeersRepository beersRepository;

    public FridgeViewModel() {
        fridgeRepository = new FridgeRepository();
        beersRepository = new BeersRepository();

        currentUserId.setValue(getCurrentUser().getUid());
    }

    public LiveData<List<Pair<FridgeContent, Beer>>> getMyFridgeWithBeers() {
        return fridgeRepository.getMyFridgeContentWithBeers(currentUserId, beersRepository.getAllBeers());
    }

    public Task<Void> toggleItemInFridge(String itemId) {
        return fridgeRepository.toggleUserFridgeContentItem(getCurrentUser().getUid(), itemId);
    }
}
