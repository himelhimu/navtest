package test.android.sabbir.navdrawer.interfaces;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import test.android.sabbir.navdrawer.models.NasaPhoto;

/**
 * Created by sabbi on 3/7/2017.
 */

public class NasaImageRequestor {
    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat;
    private ImageRequestorResponse mResponseListener;
    private Context mContext;
    private OkHttpClient mClient;
    private static final String BASE_URL = "https://api.nasa.gov/planetary/apod?";
    private static final String DATE_PARAMETER = "date=";
    private static final String API_KEY_PARAMETER = "&api_key=";
    private static final String MEDIA_TYPE_KEY = "media_type";
    private static final String MEDIA_TYPE_VIDEO_VALUE = "video";
    private static final String API_KEY="idv4ashBgAFNEVXITBLFKlwyKRPxLnUdeZ1q1yyY";
    private boolean mLoadingData=false;

    public boolean isLoadingData(){
        return mLoadingData;
    }

    public interface ImageRequestorResponse{
        void onReceivedNewPhoto(NasaPhoto nasaPhoto);
    }

    public NasaImageRequestor(FragmentActivity activity){
        mCalendar = Calendar.getInstance();
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      //  mResponseListener = (ImageRequestorResponse) activity;
        mContext = activity.getApplicationContext();
        mClient = new OkHttpClient();
        mLoadingData = false;
    }

    public void getPhoto() throws IOException {
        String date=mDateFormat.format(mCalendar.getTime());
        String urlRequest = BASE_URL + DATE_PARAMETER + date + API_KEY_PARAMETER +API_KEY;
        Request request=new Request.Builder().url(urlRequest).build();
        mLoadingData=true;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mLoadingData=false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Log.i("url to hit",""+call.request().url());
                    mCalendar.add(Calendar.DAY_OF_YEAR,-1);
                    Gson gson=new Gson();
                    NasaPhoto photoDetails=gson.fromJson(response.body().charStream(),NasaPhoto.class);
                    mResponseListener.onReceivedNewPhoto(photoDetails);
                    mLoadingData=false;

                }catch (Exception e){
                    mLoadingData=false;
                }
            }
        });
    }
}
