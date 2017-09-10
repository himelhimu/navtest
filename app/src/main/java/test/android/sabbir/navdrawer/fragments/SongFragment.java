package test.android.sabbir.navdrawer.fragments;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import test.android.sabbir.navdrawer.R;
import test.android.sabbir.navdrawer.adapters.SongAdapter;
import test.android.sabbir.navdrawer.models.Song;
import test.android.sabbir.navdrawer.musicplayer.MusicController;
import test.android.sabbir.navdrawer.musicplayer.MusicService;

public class SongFragment extends Fragment implements MediaController.MediaPlayerControl {
    private ArrayList<Song> songsList;
    private MusicService mMusicService;
    private MusicController mMusicController;
    private Intent playIntent;
    private boolean playbackPaused;
    private boolean mMusicBound;
    public SongFragment() {
    }

private static final String TAG="SongFragment";

    private ServiceConnection musicServiceConnect=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder  musicBinder= (MusicService.MusicBinder) service;
            mMusicService=musicBinder.getService();
            Log.d(TAG,"In OnServiceConnected");
            mMusicService.setSongsList(songsList);
            mMusicBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"In OnServiceDisConnected");
            mMusicBound=false;
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        getActivity().setTitle("Songs");
        RecyclerView songRecyclerView = (RecyclerView)view.findViewById(R.id.song_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        songRecyclerView.setLayoutManager(linearLayoutManager);
        songRecyclerView.setHasFixedSize(true);
        songsList= (ArrayList<Song>) getSongsList();

        Collections.sort(songsList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        SongAdapter mAdapter = new SongAdapter(getActivity(), songsList, new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Song song,int position) {
                if (song!=null){
                    songPicked(song,position);
                    Log.d("SongFragment",song.getArtist()+" "+song.getTitle()+" "+song.getId());
                }

            }
        });
        songRecyclerView.setAdapter(mAdapter);


        return view;
    }


    public void songPicked(Song song, int position){
        mMusicService.setSong(position);
        mMusicService.playSong();
        if(playbackPaused){
            setupController();
            playbackPaused=false;
        }
        mMusicController.show(0);
    }


    private void playNext(){
        mMusicService.playNext();
        if(playbackPaused){
            setupController();
            playbackPaused=false;
        }
        mMusicController.show(0);
    }

    private void playPrev(){
        mMusicService.playPrev();
        if(playbackPaused){
            setupController();
            playbackPaused=false;
        }
        mMusicController.show(0);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupController();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (playIntent==null){
            playIntent=new Intent(getActivity(),MusicService.class);
            getActivity().bindService(playIntent,musicServiceConnect, Context.BIND_AUTO_CREATE);
            getActivity().startService(playIntent);

        }
    }

    void startMusicService(){
        if (playIntent==null){
            playIntent=new Intent(getActivity(),MusicService.class);
            getActivity().bindService(playIntent,musicServiceConnect, Context.BIND_AUTO_CREATE);
            getActivity().startService(playIntent);

        }
    }

    private void setupController() {
        mMusicController=new MusicController(getContext());
        mMusicController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });

        mMusicController.setMediaPlayer(this);
        mMusicController.setAnchorView(getView().findViewById(R.id.song_list));
        mMusicController.setEnabled(true);


    }

    public List<Song> getSongsList() {
        List<Song> allSongList = new ArrayList<Song>();
        ContentResolver musicResolver=getActivity().getContentResolver();
        Uri musicUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor mCursor=musicResolver.query(musicUri,null,null,null,null);

        if (mCursor!=null && mCursor.moveToFirst()){
            do {
                Song song=new Song(mCursor.getLong(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)),
                        mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)),
                        mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                allSongList.add(song);
            }while (mCursor.moveToNext());

        }
        return allSongList;
    }

    @Override
    public void start() {
        mMusicService.go();
    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
