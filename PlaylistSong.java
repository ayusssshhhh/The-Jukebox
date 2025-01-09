package Model;

public class PlaylistSong
{

    private int playlistSongID;
    private int playlistID;
    private int songID;

    // Constructors, Getters, and Setters


    public PlaylistSong() {
    }

    public PlaylistSong(int playlistSongID, int playlistID, int songID) {
        this.playlistSongID = playlistSongID;
        this.playlistID = playlistID;
        this.songID = songID;
    }

    public int getPlaylistSongID() {
        return playlistSongID;
    }

    public void setPlaylistSongID(int playlistSongID) {
        this.playlistSongID = playlistSongID;
    }

    public int getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    @Override
    public String toString() {
        return "PlaylistSong{" +
                "playlistSongID=" + playlistSongID +
                ", playlistID=" + playlistID +
                ", songID=" + songID +
                '}';
    }
}
