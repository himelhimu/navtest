package test.android.sabbir.navdrawer.firbase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by sabbir on 6/14/17.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    public static final String TAG="MYFCM";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshToken= FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,refreshToken);
    }
}
