package ch.epfl.sweng.smartTabs.test;

import ch.epfl.sweng.smartTabs.gfx.FavoritesView;
import android.test.AndroidTestCase;

public class FavoritesViewTest extends AndroidTestCase {
    FavoritesView fav = new FavoritesView(getContext(), true);
    
    public void testSetIsFav() {
        fav.setIsFav();
        boolean test = fav.isFav();
        assertEquals("Swag",false, test);
    }   
 }