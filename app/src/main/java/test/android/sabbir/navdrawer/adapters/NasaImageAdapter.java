package test.android.sabbir.navdrawer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.models.NasaPhoto;

/**
 * Created by Sabbir on 08,March,2017
 * sabbir@mpower-social.com
 * Dhaka
 *
 * @author sabbir
 */
public class NasaImageAdapter extends RecyclerView.Adapter<NasaImageAdapter.ImageViewHolder> {
    private Context mContext;
    private ArrayList<NasaPhoto> mReceivedNasaImageList;
  public NasaImageAdapter(Context context, ArrayList<NasaPhoto> list){
        this.mReceivedNasaImageList=list;

      this.mContext=context;
    }

    @Override
    public NasaImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.nasa_image_item_card,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Log.i("inOnBind",""+mReceivedNasaImageList.size());
        Picasso.with(mContext).load(mReceivedNasaImageList.get(position).getUrl()).into(holder.nasaImageView);
        holder.tvDate.setText(mReceivedNasaImageList.get(position).getDate());
        holder.tvDetails.setText(mReceivedNasaImageList.get(position).getExplanation());
    }


    @Override
    public int getItemCount() {
        Log.i("list size",""+mReceivedNasaImageList.size());
        return mReceivedNasaImageList.size();
    }

 static class ImageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.nasa_image)
        ImageView nasaImageView;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_details)
        TextView tvDetails;
        ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
