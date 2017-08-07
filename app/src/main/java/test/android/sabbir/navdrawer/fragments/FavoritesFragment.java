package test.android.sabbir.navdrawer.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.adapters.NasaImageAdapter;
import test.android.sabbir.navdrawer.databases.DatabaseHelper;
import test.android.sabbir.navdrawer.models.NasaPhoto;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    private ArrayList<NasaPhoto> mNasaImageList;
    private LinearLayoutManager mLinearLayoutManager;
    private NasaImageAdapter mNasaImageAdapter;
    RecyclerView mRecyclerView;
    private boolean isOnFavFragment=true;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=this.getArguments();
        if (bundle!=null) {
            isOnFavFragment=bundle.getBoolean("isFavFragment");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.nasa_rv);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        getFavImagesFromDb();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_nasa_main,container,false);


        return view;
    }

    private void getFavImagesFromDb() {
        DatabaseHelper databaseHelper=new DatabaseHelper(getContext());
        mNasaImageList=databaseHelper.getAllFavNasaImage();

        mNasaImageAdapter = new NasaImageAdapter(getContext(),mNasaImageList,null,true);
        mRecyclerView.setAdapter(mNasaImageAdapter);
    }

}
