package dao;

import org.springframework.jdbc.core.JdbcTemplate;

import model.*;
import java.util.Collection;
import java.util.ArrayList;


public class TrackDAO {
    private JdbcTemplate jdbcTemplate;

    public TrackDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }


    public Track createTrack(Track track){
        //TODO: Implement this CRUD function
        //This function will create the track with the required variables
        String trackTitle = track.getTitle();
        int trackId = track.getId();
        int albumTitle = track.getAlbumId();
        this.jdbcTemplate.update("INSERT INTO tracks (title, id, album) VALUES (?,?,?)", trackTitle, trackId, albumTitle);
        return track;
    }

    public Track getTrack(int id){
        Track track = new Track(id);
        //TODO: Implement this CRUD function
        //This function will get the track and fill the the three required variables. We don't need to instantiate a
        //album object
        this.jdbcTemplate.query(
                "SELECT * FROM tracks", new Object[] { },
                (rs, rowNum) -> new Track(rs.getInt("id"), rs.getString("title"), rs.getInt("album"))
        ).forEach(trackIteration -> { track.setId(trackIteration.getId());
            track.setTitle(trackIteration.getTitle());
            track.setAlbumId(trackIteration.getAlbumId());
            //need to set all three variables required by the Track class
        });
        return track;
    }

    public Collection<Track> getAllTracks(){
        Collection<Track> tracks = new ArrayList<Track>();
        //TODO: Implement this CRUD function
        //Here we get all the tracks, similar to how we did in AlbumDAO
        this.jdbcTemplate.query(
                "SELECT * FROM tracks", new Object[] { },
                (rs, rowNum) -> new Track(rs.getInt("id"), rs.getString("title"), rs.getInt("album"))
        ).forEach(trackIteration -> tracks.add(trackIteration));
        return tracks;
    }

    public Collection<Track> getTracksByAlbumId(int albumId){
        Collection<Track> tracks = new ArrayList<Track>();
        this.jdbcTemplate.query(
                "SELECT id, title FROM tracks WHERE album = ?", new Object[] { albumId },
                (rs, rowNum) -> new Track(rs.getInt("id"), rs.getString("title"),albumId)
        ).forEach(track -> tracks.add(track) );

        return tracks;
    }
    public Track updateTrack(Track track){
        //TODO: Implement this CRUD function
        //Here we update the tracks with simple SQL
        String trackTitle = track.getTitle();
        int trackId = track.getId();
        String updateQ = "UPDATE tracks SET title = ? WHERE id = ?";
        this.jdbcTemplate.update(updateQ, trackTitle, trackId);
        //assuming you can't update the title
        return track;
    }

    public boolean deleteTrack(Track track){
        //TODO: Implement this CRUD function
        //Here we delete the track with simple SQL
        int trackId = track.getId();
        String delQ = "DELETE FROM tracks where id = ?";
        this.jdbcTemplate.update(delQ, trackId);
        return true;
    }

}
