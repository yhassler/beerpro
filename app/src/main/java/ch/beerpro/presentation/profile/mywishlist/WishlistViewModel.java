package ch.beerpro.presentation.profile.mywishlist;

import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Entity;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.data.repositories.BeersRepository;
import ch.beerpro.data.repositories.WishesRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static androidx.lifecycle.Transformations.map;
import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.utils.LiveDataExtensions.combineLatest;

public class WishlistViewModel extends ViewModel {

    private static final String TAG = "WishlistViewModel";

    private final LiveData<List<Wish>> myWishlist;

    private final MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private final WishesRepository wishesRepository;
    private final BeersRepository beersRepository;

    public WishlistViewModel() {
        wishesRepository = new WishesRepository();
        beersRepository = new BeersRepository();

        myWishlist = switchMap(currentUserId, WishesRepository::getWishesByUser);

        currentUserId.setValue(getCurrentUser().getUid());
    }

    FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public LiveData<List<Pair<Wish, Beer>>> getMyWishlistWithBeers() {
        return map(combineLatest(getMyWishlist(), map(beersRepository.getAllBeers(), Entity::entitiesById)), input -> {
            List<Wish> wishes = input.first;
            HashMap<String, Beer> beersById = input.second;

            ArrayList<Pair<Wish, Beer>> result = new ArrayList<>();
            for (Wish wish : wishes) {
                Beer beer = beersById.get(wish.getBeerId());
                result.add(Pair.create(wish, beer));
            }
            return result;
        });
    }

    public LiveData<List<Wish>> getMyWishlist() {
        return myWishlist;
    }

    public Task<Void> toggleItemInWishlist(String itemId) {
        return wishesRepository.toggleUserWishlistItem(getCurrentUser().getUid(), itemId);
    }

}