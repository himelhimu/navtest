package test.android.sabbir.navdrawer.interfaces;

import android.graphics.Bitmap;

import test.android.sabbir.navdrawer.models.NasaPhoto;

/**
 * Created by sabbir on 8/3/17.
 */

public interface FavButtonClickedListener {
    public void favClicked(NasaPhoto nasaPhoto, Bitmap bitmap);
    void favOnLongClicked(NasaPhoto nasaPhoto);
}
