package ch.beerpro.search.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import ch.beerpro.models.Beer;
import ch.beerpro.models.Entity;

public class EntityDiffItemCallback extends DiffUtil.ItemCallback<Entity> {
    @Override
    public boolean areItemsTheSame(@NonNull Entity oldBeer, @NonNull Entity newBeer) {
        return oldBeer.getId().equals(newBeer.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Entity oldBeer, @NonNull Entity newBeer) {
        return oldBeer.equals(newBeer);
    }
}
