package ch.beerpro.presentation.wishlist;

import android.widget.ImageView;
import ch.beerpro.domain.models.Beer;

public interface OnWishlistItemInteractionListener {

    void onMoreClickedListener(ImageView photo, Beer beer);

    void onWishClickedListener(Beer beer);
}
