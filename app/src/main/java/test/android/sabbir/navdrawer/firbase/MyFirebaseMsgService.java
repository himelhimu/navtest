package test.android.sabbir.navdrawer.firbase;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import test.android.sabbir.navdrawer.R;

/**
 * Created by sabbir on 6/14/17.
 */

public class MyFirebaseMsgService extends FirebaseMessagingService {
    public static final String TAG="MyFirebaseMsgService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,remoteMessage.toString());
        createNotificationtoShowMsg(remoteMessage.getNotification().getBody());
    }

    private void createNotificationtoShowMsg(String body) {
       // Intent intent=new Intent(this,)
        Uri notificationSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifBuilder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.nerd_icon)
                .setContentTitle("New Message")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSoundUri);

        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notifBuilder.build());
    }
}
