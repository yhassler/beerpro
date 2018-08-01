package ch.beerpro.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrentSearchTermViewModel extends ViewModel {
    private final MutableLiveData<String> selected = new MutableLiveData<>();

    public void set(String searchTerm) {
        selected.setValue(searchTerm);
    }

    public LiveData<String> getCurrentSearchTerm() {
        return selected;
    }
}
