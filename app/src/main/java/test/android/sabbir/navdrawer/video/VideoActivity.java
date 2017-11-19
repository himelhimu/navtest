package test.android.sabbir.navdrawer.video;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import test.android.sabbir.navdrawer.R;


/**
 * @author sabbir ,sabbir@mpower-social.com
 * */
public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
   private YouTubePlayerView youTubePlayerView;
    private ProgressDialog mDialog;
    private YouTubePlayer mYouTubePlayer;
    private TextView tvNowPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }*/

       tvNowPlaying=(TextView) findViewById(R.id.tv_now_playing);
        mDialog=new ProgressDialog(this);
        mDialog.setMessage("Loading,Please Wait..");
        mDialog.show();

        if (!isAppInstalled("com.google.android.youtube")){
            redirectToBrowser();
        }

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(VideoConfig.YOUTUBE_DATA_API_KEY, this);
    }

    private void redirectToBrowser() {
    }

    protected boolean isAppInstalled(String packageName) {
        Intent mIntent = getPackageManager().getLaunchIntentForPackage(packageName);
        return mIntent != null;
    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        this.mYouTubePlayer=youTubePlayer;
        mDialog.dismiss();
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        /** Start buffering **/
        if (!b) {
            youTubePlayer.cueVideo(VideoConfig.Five_Key_Practices_of_Producing_Jute_ID);
            tvNowPlaying.setText("Five Key Practices of Producing Jute");
           // youTubePlayer.cuePlaylist("PL0Rw2Fh6RZSHWTvADVGYLHfX-PBNAr1um");
        }
    }


   public void videoButtonCLicked(View view){
       switch (view.getId()){
           case R.id.Five_Key_Practices_of_Producing_Jute_ID:
               mYouTubePlayer.cueVideo(VideoConfig.Five_Key_Practices_of_Producing_Jute_ID);
               tvNowPlaying.setText("Five Key Practices of Producing Jute");
               break;
           case R.id.Five_Key_Practices_of_Producing_CHILLI_ID:
               mYouTubePlayer.cueVideo(VideoConfig.Five_Key_Practices_of_Producing_CHILLI_ID);
               tvNowPlaying.setText("Five Key Practices of Producing CHILLI");
               break;
           case R.id.Five_Key_Practices_of_Producing_MUNGBEAN_ID:
               mYouTubePlayer.cueVideo(VideoConfig.Five_Key_Practices_of_Producing_MUNGBEAN_ID);
               tvNowPlaying.setText("Five Key Practices of Producing MUNGBEAN");
               break;

       }

    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
            Toast.makeText(VideoActivity.this,""+arg0,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLoaded(String arg0) {

        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {

        }
    };


        @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        mDialog.show();
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent=new Intent(this,FQSMainMenuActivity.class);
                startActivity(intent);
                finish();
                return true;
           *//* case R.id.langSettings:
                Intent i=new Intent(this,LanguageActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);*//*
        }
        return true;
    }*/
}
