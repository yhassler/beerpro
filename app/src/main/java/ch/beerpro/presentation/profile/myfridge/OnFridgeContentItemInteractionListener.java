package ch.beerpro.presentation.profile.myfridge;

import android.widget.ImageView;

import ch.beerpro.domain.models.Beer;

public interface OnFridgeContentItemInteractionListener {

    void onMoreClickedListener(ImageView photo, Beer beer);

    void onFridgeClickedListener(Beer beer);
}
