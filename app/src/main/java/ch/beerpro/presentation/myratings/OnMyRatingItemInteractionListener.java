package ch.beerpro.presentation.myratings;

import ch.beerpro.domain.models.Rating;

public interface OnMyRatingItemInteractionListener {

    void onMoreClickedListener(Rating item);

    void onWishClickedListener(Rating item);
}
