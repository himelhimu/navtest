package test.android.sabbir.navdrawer.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_photo_details);
        ButterKnife.bind(this);

        NasaPhoto nasaPhoto= (NasaPhoto) getIntent().getSerializableExtra("photo");
        if (nasaPhoto!=null){
            Picasso.with(this).load(nasaPhoto.getUrl()).into(imageView);
            textViewDate.setText(nasaPhoto.getDate());
            tvTittle.setText(nasaPhoto.getTitle());
            tvDetails.setText(nasaPhoto.getExplanation());
        }


    }
}
