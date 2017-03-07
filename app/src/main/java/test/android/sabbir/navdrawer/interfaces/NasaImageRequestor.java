package test.android.sabbir.navdrawer.interfaces;

import android.app.Activity;

/**
 * Created by sabbi on 3/7/2017.
 */

public class NasaImageRequestor {
    private boolean isLoading=false;
    GetNasaImage mGetNasaImage;
    private

    public boolean isLoading(){
        return this.isLoading;
    }

  public interface GetNasaImage{

    }

    NasaImageRequestor(Activity listener){

    }

    public void getPhoto(){

    }
}
