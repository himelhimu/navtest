package test.android.sabbir.navdrawer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.models.PlaylistObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListFragment extends Fragment {


    public PlayListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_list, container, false);
    }

    public List<PlaylistObject> getTestData() {
        List<PlaylistObject> trackList = new ArrayList<PlaylistObject>();
        /*trackList.add(new PlaylistObject("Falling over", "12 tracks", ""));
        trackList.add(new PlaylistObject("Falling over", "12 tracks", ""));
        trackList.add(new PlaylistObject("Falling over", "12 tracks", ""));
        trackList.add(new PlaylistObject("Falling over", "12 tracks", ""));
        trackList.add(new PlaylistObject("Falling over", "12 tracks", ""));
        trackList.add(new PlaylistObject("Falling over", "12 tracks", ""));
        trackList.add(new PlaylistObject("Falling over", "12 tracks", ""));
        trackList.add(new PlaylistObject("Falling over", "12 tracks", ""));*/

        return trackList;

    }
}