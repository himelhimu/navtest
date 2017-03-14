package test.android.sabbir.navdrawer.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.android.sabbir.navdrawer.Constants.Constants;
import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.adapters.NasaImageAdapter;
import test.android.sabbir.navdrawer.interfaces.ImageRequestor;
import test.android.sabbir.navdrawer.interfaces.NasaImageRequestor;
import test.android.sabbir.navdrawer.models.NasaPhoto;

/**
 * A simple {@link Fragment} subclass.
 */
public class NasaMainFragment extends Fragment implements NasaImageRequestor.ImageRequestorResponse{
    @BindView(R.id.nasa_rv)
    RecyclerView mRecyclerView;
    private Calendar mCalendar;
    private SimpleDateFormat mSimpleDateFormat;
    private List<NasaPhoto> mNasaImageList;
    private LinearLayoutManager mLinearLayoutManager;
    private NasaImageAdapter mNasaImageAdapter;
    private NasaImageRequestor mNasaImageRequestor;



    public NasaMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("TAG","Nasa On OnActivityCreated");
        mNasaImageRequestor=new NasaImageRequestor(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        requestNewPhoto();
        mRecyclerView.addOnScrollListener(new MyRecyclViewScrollListener());

        mNasaImageAdapter=new NasaImageAdapter(this.getContext(), (ArrayList<NasaPhoto>) mNasaImageList);
        mRecyclerView.setAdapter(mNasaImageAdapter);
       /* Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();

        ImageRequestor imageRequestor=retrofit.create(ImageRequestor.class);
        String date=mSimpleDateFormat.format(mCalendar.getTime());
        Call<NasaPhoto> call=imageRequestor.getPhoto(date,Constants.API_KEY);
        call.enqueue(new Callback<NasaPhoto>() {
            @Override
            public void onResponse(Call<NasaPhoto> call, Response<NasaPhoto> response) {
                Log.i("URL1",""+call.request().url());
               Toast.makeText(getContext(),"Status"+response.message(),Toast.LENGTH_LONG).show();
                mNasaImageList.add(response.body());
                //mNasaImageList= (ArrayList<NasaPhoto>) response.body();
                mNasaImageAdapter=new NasaImageAdapter(getContext(), (ArrayList<NasaPhoto>) mNasaImageList);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(mNasaImageAdapter);
                mRecyclerView.hasFixedSize();

            }

            @Override
            public void onFailure(Call<NasaPhoto> call, Throwable t) {
              ///  Log.i("URL",""+call.request().url());
               // Toast.makeText(getContext(),"Error "+call.request().url(),Toast.LENGTH_LONG).show();
            }
        });*/

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG","Nasa On Oncreate");
        mLinearLayoutManager=new LinearLayoutManager(getActivity());
        mNasaImageList=new ArrayList<>();
        mCalendar=Calendar.getInstance();
        mSimpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
       // requestNewPhoto();

    }

   /* @Override
    public void onStart() {
        super.onStart();
        Log.i("TAG","In OnStart of Fragment");
        if (mNasaImageList.size()==0){
            Log.i("TAG","Calling requestNewPhoto");
            requestNewPhoto();
        }
    }*/

    private void requestNewPhoto() {
        try {
            Log.i("TAG","In requestNewPhoto");
            mNasaImageRequestor.getPhoto();
          // mNasaImageAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("TAG","Nasa On OncreateView");
        View view=inflater.inflate(R.layout.fragment_nasa_main,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onReceivedNewPhoto(final NasaPhoto nasaPhoto) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNasaImageList.add(nasaPhoto);
                mNasaImageAdapter.notifyDataSetChanged();
            }
        });
    }

    private class MyRecyclViewScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            int toatlItemCount=recyclerView.getLayoutManager().getItemCount();
            Log.i("INFO","In ScrollListener new photo");
            if (!mNasaImageRequestor.isLoadingData() && toatlItemCount==getLastVisableItemPosition()+1){
                Log.i("INFO","calling request new photo");
                requestNewPhoto();
            }
        }
    }

    private int getLastVisableItemPosition() {
        return mLinearLayoutManager.findLastVisibleItemPosition();
    }
}
