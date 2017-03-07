package test.android.sabbir.navdrawer.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.android.sabbir.navdrawer.Constants.Constants;
import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.interfaces.ImageRequestor;
import test.android.sabbir.navdrawer.models.NasaPhoto;

/**
 * A simple {@link Fragment} subclass.
 */
public class NasaMainFragment extends Fragment {
    @BindView(R.id.nasa_rv)
    RecyclerView mRecyclerView;


    public NasaMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();

        ImageRequestor imageRequestor=retrofit.create(ImageRequestor.class);
        Call<NasaPhoto> call=imageRequestor.getPhoto()
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_nasa_main,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

}
