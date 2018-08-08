package ch.beerpro.presentation.mybeers;

import android.widget.ImageView;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Rating;

public interface OnMyBeerItemInteractionListener {

    void onMoreClickedListener(ImageView photo, Beer item);

    void onWishClickedListener(Beer item);
}
