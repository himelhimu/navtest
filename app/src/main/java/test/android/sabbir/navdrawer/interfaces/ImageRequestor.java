package test.android.sabbir.navdrawer.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import test.android.sabbir.navdrawer.Constants.Constants;
import test.android.sabbir.navdrawer.models.NasaPhoto;

/**
 * Created by sabbi on 3/7/2017.
 */

public interface ImageRequestor {
   /* @GET("?{date}=&api_key={api_key}")
    Call<List<NasaPhoto>> getPhoto(@Path("date")String date, @Path("api_key") String api);*/
   @GET("apod")
   Call<NasaPhoto> getPhoto(@Query("date") String date, @Query("api_key") String api);
}
