package test.android.sabbir.navdrawer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



/**
 * Created by sabbir on 7/19/17.
 */

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    public CustomFragmentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Library";
            case 1:
                return "Music";
            case 2:
                return "PlayList";
            case 3:
                return "Song";
        }
        return null;
    }
}
