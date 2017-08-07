package test.android.sabbir.navdrawer.activites;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.application.MyApplication;
import test.android.sabbir.navdrawer.interfaces.ImageDownloadListener;
import test.android.sabbir.navdrawer.interfaces.ImageSaveListener;
import test.android.sabbir.navdrawer.models.NasaPhoto;
import test.android.sabbir.navdrawer.task.ImageDownloadTask;
import test.android.sabbir.navdrawer.task.SaveImageToDiskTask;

public class NasaPhotoDetailsActivity extends AppCompatActivity implements ImageDownloadListener, ImageSaveListener {
    @BindView(R.id.detail_image)
    ImageView imageView;
    @BindView(R.id.date)
    TextView textViewDate;
    @BindView(R.id.title_detail)
    TextView tvTittle;
    @BindView(R.id.details)
    TextView tvDetails;
    @BindView(R.id.colapssing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.toolbar_details)
    Toolbar mToolbar;

    public static String CURRENT_IMAGE_NAME="";

    public static String getImageName(){
        return CURRENT_IMAGE_NAME;
    }

    private String currentImageUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_photo_details);
        ButterKnife.bind(this);
        final ImagePopup imagePopup=new ImagePopup(this);
        imagePopup.setBackgroundColor(Color.BLACK);
        imagePopup.setWindowHeight(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); // Optional
        imagePopup.setWindowWidth(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); // Optional
        setSupportActionBar(mToolbar);
        NasaPhoto nasaPhoto= (NasaPhoto) getIntent().getSerializableExtra("photo");
        if (nasaPhoto!=null){
            Picasso.with(this).load(nasaPhoto.getUrl()).into(imageView);
            textViewDate.setText(nasaPhoto.getDate());
            tvTittle.setText(nasaPhoto.getTitle());
            mCollapsingToolbarLayout.setTitle(nasaPhoto.getTitle());
            tvDetails.setText(nasaPhoto.getExplanation());
        }

        CURRENT_IMAGE_NAME=nasaPhoto.getTitle().trim();
        currentImageUrl=nasaPhoto.getUrl();

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showImageSavingDialog();
                return true;
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imagePopup.initiatePopup(imageView.getDrawable());
            }
        });

    }

    private void showImageSavingDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.myDialog));
        builder.setTitle("Do you want to Save this Image?");
        builder.setMessage("This image will be downloaded and saved in SD card");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveImage();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void saveImage() {

        ImageDownloadTask imageDownloadTask=new ImageDownloadTask(this);
        imageDownloadTask.execute(currentImageUrl);
        imageDownloadTask.setDownloadListener(this);


    }

    @Override
    public void onImageDownLoaded(Bitmap bitmap) {
        Toast.makeText(this,"ImageDownloaded "+getImageName(),Toast.LENGTH_SHORT).show();
        SaveImageToDiskTask saveImageToDiskTask=new SaveImageToDiskTask();
        saveImageToDiskTask.setSavingListener(this);
        saveImageToDiskTask.execute(bitmap);
    }


    @Override
    public void onSaved(boolean b,String imagePath) {
        if (b)
        {
            Toast.makeText(this, getImageName()+" Successfully Saved",Toast.LENGTH_SHORT).show();
            MediaScannerConnection.scanFile(this, new String[] { imagePath }, new String[] { "image/jpeg" }, null);
        }


    }
}
