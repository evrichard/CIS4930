package dao;

import org.springframework.jdbc.core.JdbcTemplate;

import model.*;
import java.util.Collection;
import java.util.ArrayList;

public class AlbumDAO {
    private JdbcTemplate jdbcTemplate;

    public AlbumDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }

    public Album createAlbum(Album album){
        //TODO: Implement this CRUD function
        //Here we create an album with the two required feilds
        int albumId = album.getId();
        String albumTitle = album.getTitle();
        this.jdbcTemplate.update("INSERT INTO albums (title, id) VALUES (?,?)", albumTitle, albumId);
        return album;
    }

    public Album getAlbum(int id){
        Album album = new Album(id, "");
        //TODO: Implement this CRUD function
        //Get album and set tracks using getTracksByAlbumId(id) in TracksDAOINSERT INTO albums (id, title)
        //We used a method similar to the one provided, but filled in for the required fields.
        TrackDAO track = new TrackDAO(jdbcTemplate);

        this.jdbcTemplate.query(
                "SELECT * FROM albums", new Object[] { },
                (rs, rowNum) -> new Album(rs.getInt("id"), rs.getString("title"))
        ).forEach(albumInteration -> {
            album.setTitle(albumInteration.getTitle());
            album.setTracks(track.getTracksByAlbumId(id));
        });
        return album;
    }

    public Collection<Album> getAllAlbums(){
        Collection<Album> albums = new ArrayList<Album>();
        this.jdbcTemplate.query(
                "SELECT * FROM albums", new Object[] { },
                (rs, rowNum) -> new Album(rs.getInt("id"), rs.getString("title"))
        ).forEach(album -> albums.add(album));

        return albums;
    }

    public Album updateAlbum(Album album){
        //TODO: Implement this CRUD function
        //Here we update an album with simple SQL
        String albumTitle = album.getTitle();
        int albumId = album.getId();
        String updateQ = "UPDATE albums SET title = ? WHERE id = ? ";
        this.jdbcTemplate.update(updateQ, albumTitle, albumId);
        return album;
    }

    public boolean deleteAlbum(Album album){
        //TODO: Implement this CRUD function
        //Here we delete an album with simple SQL
        int albumId = album.getId();
        String delQ = "DELETE FROM albums where id = ?";
        this.jdbcTemplate.update(delQ, albumId);
        return true;
    }
}