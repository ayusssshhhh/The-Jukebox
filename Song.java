package Model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Song {
    private int songID;
    private String title;
    private String artist;
    private String genre;
    private String album;
    private long duration;
    private String filePath;

    // Audio playback fields
    private Clip clip;
    private Long currentFrame;

    public Song() {
    }

    public Song(int songID, String title, String artist, String genre, String album, long duration, String filePath) {
        this.songID = songID;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.album = album;
        this.duration = duration;
        this.filePath = filePath;
    }

    // Getters and Setters
    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songID=" + songID +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                ", filePath='" + filePath + '\'' +
                '}';
    }


    // Methods for playback functionality
    /**
     * Loads the audio file for playback.
     */
    public void loadAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path is not specified for this song.");
        }
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    /**
     * Starts playing the song from the beginning or the current frame.
     */
    public void play() {
        if (clip != null) {
            clip.start();
            System.out.println("Playing: " + title);
        } else {
            System.out.println("Audio clip is not loaded.");
        }
    }

    /**
     * Pauses the playback and stores the current frame position.
     */
    public void pause() {
        if (clip != null && clip.isRunning()) {
            currentFrame = clip.getMicrosecondPosition();
            clip.stop();
            System.out.println("Paused: " + title);
        }
    }

    /**
     * Resumes playback from the paused position.
     */
    public void resume() {
        if (clip != null && currentFrame != null) {
            clip.setMicrosecondPosition(currentFrame);
            clip.start();
            System.out.println("Resumed: " + title);
        }
    }

    /**
     * Stops the playback and resets the frame position.
     */
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
            System.out.println("Stopped: " + title);
        }
    }
}
