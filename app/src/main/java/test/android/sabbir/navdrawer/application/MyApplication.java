package test.android.sabbir.navdrawer.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;

import java.io.File;
import java.io.IOException;

/**
 * Created by sabbir on 7/9/17.
 */

public class MyApplication extends Application {
    private static  MyApplication ourInstance;
    public static String IMAGE_FOLDER_NAME="nasaImage";
    public static MyApplication getInstance() {
        return ourInstance;
    }

    public MyApplication() {
        createDirctory();
    }

    private void createDirctory() {
        String path= Environment.getExternalStorageDirectory()+ File.separator+IMAGE_FOLDER_NAME;
        File dir=new File(path);
        boolean isCreated=dir.mkdir();
        if (isCreated);
        else {
            try {
                throw new IOException();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance=new MyApplication();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
