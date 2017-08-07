package test.android.sabbir.navdrawer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.models.Song;


public class SongAdapter extends RecyclerView.Adapter<SongViewHolder>{

    private Context context;
    private List<Song> allSongs;
    private OnItemClickListener mCallBack;

    public interface OnItemClickListener{
        void onItemClicked(Song song);
    }

    public SongAdapter(Context context, List<Song> allSongs, OnItemClickListener listener) {
        this.context = context;
        this.allSongs = allSongs;
        this.mCallBack=listener;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_list_layout, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        final Song songs = allSongs.get(position);
        holder.songTitle.setText(songs.getTitle());
        holder.songAuthor.setText(songs.getArtist());

        holder.songTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.onItemClicked(songs);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allSongs.size();
    }

}
