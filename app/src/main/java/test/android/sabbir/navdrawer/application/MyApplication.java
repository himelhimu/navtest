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

    }


  @SuppressWarnings("StatementWithEmptyBody")
  public static void createDirectory() throws IOException {
      boolean isCreated=false;
        String path= Environment.getExternalStorageDirectory()+ File.separator+IMAGE_FOLDER_NAME;
        File dir=new File(path);
      if (!dir.exists()){
          isCreated=dir.mkdir();
      }

        if (isCreated);
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
