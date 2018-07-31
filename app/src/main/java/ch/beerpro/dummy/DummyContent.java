package ch.beerpro.dummy;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<SearchResult> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, SearchResult> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        addItem(new SearchResult("0", "Schützengarten", "Lager Hell", "Lager",
                new Uri.Builder().scheme("http").path("shop.gedex.ch/shop/resources/product_images_klein/16831_kl.jpg")
                        .build()));
        addItem(new SearchResult("1", "Appenzeller", "IPA", "India Pale Ale", new Uri.Builder().scheme("https")
                .path("res.cloudinary.com/ratebeer/image/upload/w_152,h_309,c_pad,d_beer_img_default.png,f_auto/beer_380506")
                .build()));
    }

    private static void addItem(SearchResult item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    public static class SearchResult {
        public final String id;
        public final String subtitle;
        public final String title;
        public final String category;
        public final Uri photo;

        public SearchResult(String id, String subtitle, String title, String category, Uri photo) {
            this.id = id;
            this.subtitle = subtitle;
            this.title = title;
            this.category = category;
            this.photo = photo;
        }
    }
}
