package DAO;

import DataBaseManager.DBconfig;
import Model.Playlist;
import Model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static DataBaseManager.DBconfig.getConnection;

public class PlayListDAO
{

    public int insertPlaylist(String playlistName) {
        String sql = "INSERT INTO Playlists (name) VALUES (?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, playlistName);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int playlistID = generatedKeys.getInt(1);  // Get the generated PlaylistID
                        return playlistID;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }



    //   Insert song into a playlist in the database
    public void insertSongIntoPlaylist(int playlistID, int songID) {
        String sql = "INSERT INTO Playlistsongs (PlaylistID, songId) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, playlistID);
            preparedStatement.setInt(2, songID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







    // Retrieve all playlists from the database
    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM Playlists";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Playlist playlist = new Playlist();
                    playlist.setPlaylistID(resultSet.getInt("PlaylistID"));
                    playlist.setPlaylistName(resultSet.getString("name"));
                    playlists.add(playlist);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }
    public boolean deletePlaylist(int playlistID) {
        String sql = "DELETE FROM Playlists WHERE PlaylistID = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, playlistID);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Playlist deleted successfully.");
                return true;
            } else {
                System.out.println("No playlist found with the given ID.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve songs in a playlist
    public List<Song> getSongsInPlaylist(int playlistID, SongDAO songDAO) {
        List<Song> songsInPlaylist = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String query = "SELECT SongID FROM PlaylistSongs WHERE PlaylistID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, playlistID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int songID = resultSet.getInt("SongID");
                    Song song = songDAO.getSongByID(songID);  // Fetch song using SongDAO
                    if (song != null) {
                        songsInPlaylist.add(song);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songsInPlaylist;
    }





}
