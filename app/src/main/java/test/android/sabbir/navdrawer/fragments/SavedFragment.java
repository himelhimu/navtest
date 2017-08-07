package test.android.sabbir.navdrawer.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.adapters.SlaveImageViewAdapter;
import test.android.sabbir.navdrawer.application.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedFragment extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<String> dataList=new ArrayList<>();
    SlaveImageViewAdapter slaveImageViewAdapter;
    public SavedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_nasa_main,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.nasa_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        getImagePathListFromSdCard();
        return view;
    }

    private void getImagePathListFromSdCard() {
        String path= Environment.getExternalStorageDirectory().toString();
        path+="/"+ MyApplication.IMAGE_FOLDER_NAME;
        File folder=new File(path);
        if (folder.exists() && folder.isDirectory()){
            File[] files=folder.listFiles();
            for (File file:files){
                dataList.add(file.getAbsolutePath());
            }
        }

        slaveImageViewAdapter=new SlaveImageViewAdapter(getActivity(),dataList);
        mRecyclerView.setAdapter(slaveImageViewAdapter);
    }

}
