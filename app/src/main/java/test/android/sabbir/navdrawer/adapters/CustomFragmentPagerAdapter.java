package test.android.sabbir.navdrawer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import test.android.sabbir.navdrawer.fragments.MusicLibraryFragment;
import test.android.sabbir.navdrawer.fragments.NasaMainFragment;
import test.android.sabbir.navdrawer.fragments.PlayListFragment;
import test.android.sabbir.navdrawer.fragments.SongFragment;
import test.android.sabbir.navdrawer.musicplayer.MusicMainFragment;

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
            case 0:
                return new MusicLibraryFragment();
            case 1:
                return new MusicMainFragment();
            case 2:
                return new PlayListFragment();
            case 3:
                return new SongFragment();
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
