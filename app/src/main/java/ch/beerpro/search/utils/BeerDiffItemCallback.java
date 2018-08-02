package ch.beerpro.search.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import ch.beerpro.models.Beer;

public class BeerDiffItemCallback extends DiffUtil.ItemCallback<Beer> {
    @Override
    public boolean areItemsTheSame(@NonNull Beer oldUser, @NonNull Beer newUser) {
        return oldUser.id.equals(newUser.id);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Beer oldUser, @NonNull Beer newUser) {
        return oldUser.equals(newUser);
    }
}
