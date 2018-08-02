package ch.beerpro.search.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import ch.beerpro.models.Beer;

public class BeerDiffItemCallback extends DiffUtil.ItemCallback<Beer> {
    @Override
    public boolean areItemsTheSame(@NonNull Beer oldBeer, @NonNull Beer newBeer) {
        return oldBeer.getName().equals(newBeer.getName()) && oldBeer.getManufacturer().equals(newBeer.getManufacturer());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Beer oldBeer, @NonNull Beer newBeer) {
        return oldBeer.equals(newBeer);
    }
}
