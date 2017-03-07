package test.android.sabbir.navdrawer.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import test.android.sabbir.navdrawer.models.NasaPhoto;

/**
 * Created by sabbi on 3/7/2017.
 */

public interface ImageRequestor {
    @GET("{date}=&api_key={api_key}")
    Call<NasaPhoto>getPhoto(@Path("date")String date,@Path("api_key") String api);
}
