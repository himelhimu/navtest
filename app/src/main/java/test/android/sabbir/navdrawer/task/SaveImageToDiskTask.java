package test.android.sabbir.navdrawer.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import test.android.sabbir.navdrawer.activites.NasaPhotoDetailsActivity;
import test.android.sabbir.navdrawer.application.MyApplication;
import test.android.sabbir.navdrawer.interfaces.ImageSaveListener;

/**
 * Created by sabbir on 7/9/17.
 */

public class SaveImageToDiskTask extends AsyncTask<Bitmap,Void,Void> {
    private ImageSaveListener imageSaveListener;
    private boolean isSaved;
    @Override
    protected Void doInBackground(Bitmap... params) {
       String path=Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+MyApplication.IMAGE_FOLDER_NAME;
        File fileName=new File(path,NasaPhotoDetailsActivity.getImageName()+".jpg");
        try {
            fileName.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(fileName);
            params[0].compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            isSaved=true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (isSaved)
            imageSaveListener.onSaved(isSaved);
    }

    public void setSavingListener(NasaPhotoDetailsActivity nasaPhotoDetailsActivity) {
        imageSaveListener=nasaPhotoDetailsActivity;
    }
}
