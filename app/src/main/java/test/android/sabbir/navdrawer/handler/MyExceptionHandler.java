package test.android.sabbir.navdrawer.handler;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import test.android.sabbir.navdrawer.activites.RegisterActivity;
import test.android.sabbir.navdrawer.application.MyApplication;

/**
 * Created by sabbir on 11/20/17.
 */

public class MyExceptionHandler implements Thread.UncaughtExceptionHandler {

    private AppCompatActivity activity;
    private Context mContext;

    public MyExceptionHandler(Context context){
       mContext=context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Intent intent=new Intent(mContext, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent=PendingIntent.getActivity(MyApplication.getInstance().getBaseContext(),0,
                intent,PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager= (AlarmManager) MyApplication.getInstance().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC,System.currentTimeMillis()+1,pendingIntent);
    }
}
