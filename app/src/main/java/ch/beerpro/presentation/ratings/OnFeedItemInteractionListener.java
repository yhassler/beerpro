package ch.beerpro.presentation.ratings;

import ch.beerpro.domain.models.Rating;

public interface OnFeedItemInteractionListener {
    void onRatingLikedListener(Rating rating);

    void onMoreClickedListener(Rating rating);

    void onWishClickedListener(Rating item);
}
