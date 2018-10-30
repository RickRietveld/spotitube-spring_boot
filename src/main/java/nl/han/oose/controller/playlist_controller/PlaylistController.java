package nl.han.oose.controller.playlist_controller;

import nl.han.oose.entity.playlist_entity.Playlist;
import nl.han.oose.entity.playlist_entity.PlaylistCollection;
import nl.han.oose.entity.track_entity.Track;
import nl.han.oose.entity.track_entity.TrackCollection;
import nl.han.oose.service.playlist_service.PlaylistServiceImpl;
import nl.han.oose.service.track_service.TrackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
public class PlaylistController {

    @Autowired
    private PlaylistServiceImpl playlistService;
    @Autowired
    private TrackServiceImpl trackService;

    @GetMapping(path = "/playlists", produces = {"application/json"})
    public ResponseEntity<PlaylistCollection> getAllPlaylists(@RequestParam("token") String token) {
        try {
            return new ResponseEntity<>(playlistService.getAllPlaylists(token), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(path = "/playlists", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<PlaylistCollection> addPlaylist(@RequestParam("token") String token, @RequestBody Playlist playlist) {
        try {
            return new ResponseEntity<>(playlistService.addPlaylist(token, playlist), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/playlists/{id}/tracks", produces = {"application/json"})
    public ResponseEntity<TrackCollection> getAttachedTracks(@RequestParam("token") String token, @PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(trackService.getAttachedTracks(token, id), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/playlists/{id}/tracks", produces = {"application/json"})
    public ResponseEntity<TrackCollection> addTrackToPlaylist(@RequestParam("token") String token, @PathVariable("id") int playlistId, @RequestBody Track track) {
        try {
            return new ResponseEntity<>(trackService.addTrackToPlaylist(token, playlistId, track), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(path = "/playlists/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<PlaylistCollection> renamePlaylist(@RequestParam("token") String token, @PathVariable("id") int playlistId, @RequestBody Playlist playlist) {
        try {
            return new ResponseEntity<>(playlistService.renamePlaylist(token, playlistId, playlist), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(path = "/playlists/{id}", produces = {"application/json"})
    public ResponseEntity<PlaylistCollection> deletePlaylist(@RequestParam("token") String token, @PathVariable("id") int playlistId) {
        try {
            return new ResponseEntity<>(playlistService.deletePlaylist(token, playlistId), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(path = "/playlists/{playlistId}/tracks/{trackId}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<TrackCollection> removeTrackFromPlaylist(@RequestParam("token") String token, @PathVariable("playlistId") int playlistId, @PathVariable("trackId") int trackId) {
        try {
            return new ResponseEntity<>(trackService.removeTrackFromPlaylist(token, playlistId, trackId), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


}
