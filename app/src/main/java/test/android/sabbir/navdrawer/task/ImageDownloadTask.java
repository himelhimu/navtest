package test.android.sabbir.navdrawer.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import test.android.sabbir.navdrawer.activites.NasaPhotoDetailsActivity;
import test.android.sabbir.navdrawer.interfaces.ImageDownloadListener;

/**
 * Created by sabbir on 7/9/17.
 */

public class ImageDownloadTask extends AsyncTask<String,Void,Bitmap> {
    private ImageDownloadListener listener;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    public ImageDownloadTask(NasaPhotoDetailsActivity nasaPhotoDetailsActivity) {
        mContext=nasaPhotoDetailsActivity;
        mProgressDialog=new ProgressDialog(mContext);
        mProgressDialog.setTitle("Downloading");
        mProgressDialog.setMessage("Downloading Image..Please Wait");
        mProgressDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap=null;
        try {
            URL url=new URL(params[0]);
            URLConnection urlConnection=url.openConnection();
            bitmap= BitmapFactory.decodeStream(urlConnection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mProgressDialog.dismiss();
        listener.onImageDownLoaded(bitmap);
    }

    public void setDownloadListener(NasaPhotoDetailsActivity nasaPhotoDetailsActivity) {
        listener=nasaPhotoDetailsActivity;
    }
}
