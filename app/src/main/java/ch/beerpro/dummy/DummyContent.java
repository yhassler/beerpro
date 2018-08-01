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
    public static final List<Beer> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Beer> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        addItem(new Beer("0", "Schützengarten", "Lager Hell", "Lager",
                "http://shop.gedex.ch/shop/resources/product_images_klein/16831_kl.jpg", 3, 2));
        addItem(new Beer("1", "Appenzeller", "IPA", "India Pale Ale",
                "https://res.cloudinary.com/ratebeer/image/upload/w_152,h_309,c_pad,d_beer_img_default.png,f_auto/beer_380506",
                5, 50));
    }

    private static void addItem(Beer item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    public static class Beer {
        public String id;
        public String manufacturer;
        public  String name;
        public  String category;
        public  String photo;
        public  int avgRating;
        public  int numRatings;

        public Beer() { } // Needed for Firebase

        public Beer(String id, String manufacturer, String name, String category, String photo, int avgRating,
                    int numRatings) {
            this.id = id;
            this.manufacturer = manufacturer;
            this.name = name;
            this.category = category;
            this.photo = photo;
            this.avgRating = avgRating;
            this.numRatings = numRatings;
        }

        @Override
        public String toString() {
            return "Beer{" + "id='" + id + '\'' + ", manufacturer='" + manufacturer + '\'' + ", name='" + name + '\'' +
                    ", category='" + category + '\'' + ", avgRating=" + avgRating +
                    ", numRatings=" + numRatings + '}';
        }
    }
}
