package test.android.sabbir.navdrawer.models;


public class PlaylistObject {

    private String playlistTitle;
    private String playlistTracks;
    private String playlistCover;

    public PlaylistObject(String playlistTitle, String playlistTracks, String playlistCover) {
        this.playlistTitle = playlistTitle;
        this.playlistTracks = playlistTracks;
        this.playlistCover = playlistCover;
    }

    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public String getPlaylistTracks() {
        return playlistTracks;
    }

    public String getPlaylistCover() {
        return playlistCover;
    }
}
