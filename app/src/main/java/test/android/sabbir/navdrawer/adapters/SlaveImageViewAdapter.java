package test.android.sabbir.navdrawer.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.utilites.Helper;

/**
 * Created by sabbir on 8/6/17.
 */

public class SlaveImageViewAdapter extends RecyclerView.Adapter<SlaveImageViewAdapter.SlaveImageViewHolder>{
    ArrayList<String > imagePathList;
    Context context;

    public SlaveImageViewAdapter(Context context,ArrayList<String > data){
        this.imagePathList=data;
        this.context=context;
    }
    @Override
    public SlaveImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slave_row,parent,false);
        return new SlaveImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SlaveImageViewHolder holder, int position) {
        Log.d("TAG",""+imagePathList.get(position));
        File file=new File(imagePathList.get(position));
        Uri uri=Uri.fromFile(file);
        Glide.with(context).load(uri).into(holder.imageView);
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        holder.textView.setText(file.getName());
    }

    @Override
    public int getItemCount() {
        Log.d("TAG",""+imagePathList.size());
        return imagePathList.size();
    }

    class SlaveImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gallery_imageview)
        ImageView imageView;
        @BindView(R.id.tv_image_name)
        TextView textView;
        SlaveImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
