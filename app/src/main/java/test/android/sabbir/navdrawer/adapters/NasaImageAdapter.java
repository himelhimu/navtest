package test.android.sabbir.navdrawer.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.activites.NasaPhotoDetailsActivity;
import test.android.sabbir.navdrawer.interfaces.FavButtonClickedListener;
import test.android.sabbir.navdrawer.models.NasaPhoto;
import test.android.sabbir.navdrawer.utilites.Helper;

/**
 * Created by Sabbir on 08,March,2017
 * sabbir@mpower-social.com
 * Dhaka
 *
 * @author sabbir
 */
@SuppressWarnings("StatementWithEmptyBody")
public class NasaImageAdapter extends RecyclerView.Adapter<NasaImageAdapter.ImageViewHolder> {
    private Context mContext;
    private ArrayList<NasaPhoto> mReceivedNasaImageList;
    private NasaPhoto mNasaPhoto=null;
    private FavButtonClickedListener favButtonClickedListener=null;
    private Bitmap bitmap;
    private boolean shouldHideFavIcon;
  public NasaImageAdapter(Context context, ArrayList<NasaPhoto> list,FavButtonClickedListener favButtonClickedListener){
        this.mReceivedNasaImageList=list;
        this.mContext=context;
      if (favButtonClickedListener==null){

      }else {
          this.favButtonClickedListener= favButtonClickedListener;
      }
        shouldHideFavIcon=false;

    }

    @SuppressWarnings("StatementWithEmptyBody")
    public NasaImageAdapter(Context context, ArrayList<NasaPhoto> mNasaImageList, FavButtonClickedListener favButtonClickedListener, boolean isOnFavFragment) {
        this.mReceivedNasaImageList=mNasaImageList;
        this.mContext=context;
        if (favButtonClickedListener==null){

        }else {
            this.favButtonClickedListener= favButtonClickedListener;
        }

        shouldHideFavIcon=isOnFavFragment;
    }

    @Override
    public NasaImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.nasa_image_item_card,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Log.i("inOnBind",""+mReceivedNasaImageList.size());
        NasaPhoto np=mReceivedNasaImageList.get(position);
        holder.bindPhoto(np);
        /*Picasso.with(mContext).load(mReceivedNasaImageList.get(position).getUrl()).into(holder.nasaImageView);
        holder.tvDate.setText(mReceivedNasaImageList.get(position).getDate());
        holder.tvDetails.setText(mReceivedNasaImageList.get(position).getExplanation());*/
    }


    @Override
    public int getItemCount() {
        Log.i("list size",""+mReceivedNasaImageList.size());
        return mReceivedNasaImageList.size();
    }

 class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.nasa_image)
        ImageView nasaImageView;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_details)
        TextView tvDetails;
     @BindView(R.id.imagview_fav_border)
     ImageView favImage;
     private NasaPhoto mNasaPhoto=null;
        ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
            if (shouldHideFavIcon){
                favImage.setVisibility(View.GONE);
            }else {
                setUpFavIcon();
            }

        }

     private void setUpFavIcon() {
         favImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 bitmap = ((BitmapDrawable)nasaImageView.getDrawable()).getBitmap();

                 if (bitmap!=null && mNasaPhoto!=null && favButtonClickedListener!=null){
                     saveAsFav(mNasaPhoto,bitmap);
                     favImage.setImageResource(R.drawable.ic_fav_filled);
                 }

             }
         });
     }

     @Override
     public void onClick(View v) {
         if (v.getId()==R.id.imagview_fav_border) return;
         Context context=itemView.getContext();
         Intent intent=new Intent(context, NasaPhotoDetailsActivity.class);
         if (mNasaPhoto!=null) intent.putExtra("photo",mNasaPhoto);

         context.startActivity(intent);
     }


     //TODO have to load the Bitmap from database for Fav
     ///Glide.with(nasaImageView.getContext()).load(shouldHideFavIcon? Helper.getBytes(photo.getBitmap()):photo.getUrl())
     void bindPhoto(NasaPhoto photo) {
         mNasaPhoto = photo;
         Glide.with(nasaImageView.getContext()).load(photo.getUrl())
                 .asBitmap()
                 .centerCrop()
                 .thumbnail(0.1f)
                 .placeholder(R.drawable.img_place_holder)
                .into(nasaImageView);
         tvDate.setText(photo.getDate());
         tvDetails.setText(photo.getExplanation());

     }
 }

    private void saveAsFav(NasaPhoto photo, Bitmap bitmap) {
        favButtonClickedListener.favClicked(photo,bitmap);
        Toast.makeText(mContext,"Added to Favorites",Toast.LENGTH_SHORT).show();
    }

  /*  public void bindPhoto(NasaPhoto photo) {
        mNasaPhoto = photo;
        Picasso.with(mItemImage.getContext()).load(photo.getUrl()).into(mItemImage);
        mItemDate.setText(photo.getHumanDate());
        mItemDescription.setText(photo.getExplanation());
    }*/
}
