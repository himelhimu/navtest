package test.android.sabbir.navdrawer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.android.sabbir.navdrawer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NasaMainFragment extends Fragment {


    public NasaMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nasa_main, container, false);
    }

}
