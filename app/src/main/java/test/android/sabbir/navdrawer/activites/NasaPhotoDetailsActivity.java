package test.android.sabbir.navdrawer.activites;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.models.NasaPhoto;

public class NasaPhotoDetailsActivity extends AppCompatActivity {
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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imagePopup.initiatePopup(imageView.getDrawable());
            }
        });

    }
}
