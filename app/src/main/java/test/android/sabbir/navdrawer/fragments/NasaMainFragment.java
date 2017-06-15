package test.android.sabbir.navdrawer.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class NasaMainFragment extends Fragment{
    //@BindView(R.id.nasa_rv)
    RecyclerView mRecyclerView;
    private Calendar mCalendar;
    private SimpleDateFormat mSimpleDateFormat;
    private ArrayList<NasaPhoto> mNasaImageList;
    private LinearLayoutManager mLinearLayoutManager;
    private NasaImageAdapter mNasaImageAdapter;
    private NasaImageRequestor mNasaImageRequestor;
    ImageRequestor imageRequestor;
    Retrofit mRetrofit;
    private GridLayoutManager mGridLayoutManager;
    private boolean isLoading=false;
    private ProgressDialog mProgressDialog;

    private boolean isLoading(){
        return this.isLoading;
    }



    public NasaMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("TAG","Nasa On OnActivityCreated");
        mNasaImageList=new ArrayList<>();
        mRecyclerView=(RecyclerView) getActivity().findViewById(R.id.nasa_rv);
        mLinearLayoutManager=new LinearLayoutManager(getContext());
        mNasaImageRequestor=new NasaImageRequestor(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //requestNewPhoto();
        try {
            getPhoto();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecyclerView.addOnScrollListener(new MyRecyclViewScrollListener());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());



        mNasaImageAdapter=new NasaImageAdapter(this.getContext(), mNasaImageList);
        mRecyclerView.setAdapter(mNasaImageAdapter);

        setOnItemTouchListener();
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

    private void setOnItemTouchListener() {
        ItemTouchHelper.SimpleCallback itemTouchCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position=viewHolder.getAdapterPosition();
                mNasaImageList.remove(position);
                mRecyclerView.getAdapter().notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG","Nasa On Oncreate");
        mProgressDialog=new ProgressDialog(getContext());
        mProgressDialog.setMessage("Fetching Nasa Image Of The Day..Please Wait");
        mProgressDialog.show();
        mCalendar=Calendar.getInstance();
        mSimpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//       requestNewPhoto();

    }


    private void requestNewPhoto() {

            Log.i("TAG", "In requestNewPhoto");
            this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCalendar.add(Calendar.DAY_OF_YEAR, -1);
                    try {
                        getPhoto();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
//           mNasaImageAdapter.notifyDataSetChanged();
                }

            });
            /*mCalendar.add(Calendar.DAY_OF_YEAR,-1);
            getPhoto();
//           mNasaImageAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.nasa_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.show_as_grid:
                mGridLayoutManager=new GridLayoutManager(getContext(),2);
                mRecyclerView.setLayoutManager(mGridLayoutManager);
                return true;
            case R.id.default_layout:
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("TAG","Nasa On OncreateView");
        View view=inflater.inflate(R.layout.fragment_nasa_main,container,false);

        setHasOptionsMenu(true);
        return view;
    }

    private void getPhoto() throws IOException{
        mRetrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();

        imageRequestor=mRetrofit.create(ImageRequestor.class);
        String date=mSimpleDateFormat.format(mCalendar.getTime());
        Call<NasaPhoto> call=imageRequestor.getPhoto(date,Constants.API_KEY);
        call.enqueue(new Callback<NasaPhoto>() {

            @Override
            public void onResponse(Call<NasaPhoto> call, Response<NasaPhoto> response) {
                isLoading=true;
                Log.i("URL1",""+call.request().url());
              // Toast.makeText(getContext(),"Status"+response.message(),Toast.LENGTH_LONG).show();
                Log.i("response",response.body().getTitle());
                mNasaImageList.add(response.body());
                mNasaImageAdapter.notifyItemInserted(mNasaImageList.size());

                isLoading=false;
                if (mProgressDialog.isShowing()) mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NasaPhoto> call, Throwable t) {
                isLoading=false;
              ///  Log.i("URL",""+call.request().url());
               // Toast.makeText(getContext(),"Error "+call.request().url(),Toast.LENGTH_LONG).show();
            }
        });


    }



    private class MyRecyclViewScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            int toatlItemCount=recyclerView.getLayoutManager().getItemCount();
            Log.i("INFO","In ScrollListener new photo");
            if (!isLoading() && toatlItemCount==getLastVisibleItemPosition()+1){
                Log.i("INFO","calling request new photo");
                requestNewPhoto();
            }
        }
    }

    private int getLastVisibleItemPosition() {
        return mLinearLayoutManager.findLastVisibleItemPosition();
    }
}
