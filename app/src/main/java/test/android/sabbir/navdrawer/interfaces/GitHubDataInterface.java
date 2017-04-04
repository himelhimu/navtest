package test.android.sabbir.navdrawer.interfaces;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import test.android.sabbir.navdrawer.models.GitHubUser;

/**
 * Created by Sabbir on 07,March,2017
 * sabbir@mpower-social.com
 * Dhaka
 *
 * @author sabbir
 */
public interface GitHubDataInterface {
    @GET("/users/{username}")
    Call<GitHubUser> getUser(@Path("username") String username);
}
