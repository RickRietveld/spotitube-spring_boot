package nl.han.oose.controller.track_controller;

import nl.han.oose.entity.track_entity.TrackCollection;
import nl.han.oose.service.track_service.TrackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
public class TrackController {

    @Autowired
    private TrackServiceImpl trackService;

    @GetMapping(path = "/tracks", produces = {"application/json"})
    public ResponseEntity<TrackCollection> getAllAvailableTracksForPlaylist(@RequestParam("token") String token, @RequestParam("forPlaylist") int playlistId) {
        try {
            return new ResponseEntity<>(trackService.getAvailableTracks(token, playlistId), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


}
