package test.android.sabbir.navdrawer.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.adapters.CustomFragmentPagerAdapter;
import test.android.sabbir.navdrawer.adapters.MusicFragmentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicLibraryFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public MusicLibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_music_library,container,false);
        tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager= (ViewPager) view.findViewById(R.id.view_pager);

        viewPager.setAdapter(new MusicFragmentPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
