package nl.han.oose.service.track_service;

import nl.han.oose.entity.track_entity.Track;
import nl.han.oose.entity.track_entity.TrackCollection;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

@Component
public interface TrackService {
    TrackCollection getAvailableTracks(String token, int playlistId) throws AuthenticationException;

    TrackCollection getAttachedTracks(String token, int playlistId) throws AuthenticationException;

    TrackCollection addTrackToPlaylist(String token, int playlistId, Track track) throws AuthenticationException;

    TrackCollection removeTrackFromPlaylist(String token, int playlistId, int trackId) throws AuthenticationException;

}
