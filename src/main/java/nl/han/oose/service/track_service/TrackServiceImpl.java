package nl.han.oose.service.track_service;

import nl.han.oose.entity.account_entity.UserToken;
import nl.han.oose.entity.track_entity.Track;
import nl.han.oose.entity.track_entity.TrackCollection;
import nl.han.oose.persistence.account_dao.TokenDAO;
import nl.han.oose.persistence.track_dao.TrackDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

@Component
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackDAO trackDAO;
    @Autowired
    private TokenDAO tokenDAO;

    @Override
    public TrackCollection getAvailableTracks(String token, int playlistId) throws AuthenticationException {
        UserToken userToken = getUserToken(token);
        if (tokenDAO.isTokenValid(userToken)) {
            return trackDAO.getAvailableTracks(playlistId);
        } else {
            throw new AuthenticationException("Usertoken mismatch.");
        }
    }

    @Override
    public TrackCollection getAttachedTracks(String token, int playlistId) throws AuthenticationException {
        UserToken userToken = getUserToken(token);
        if (tokenDAO.isTokenValid(userToken)) {
            return trackDAO.getAttachedTracks(playlistId);
        } else {
            throw new AuthenticationException("Usertoken mismatch.");
        }
    }

    @Override
    public TrackCollection addTrackToPlaylist(String token, int playlistId, Track track) throws AuthenticationException {
        UserToken userToken = getUserToken(token);
        if (tokenDAO.isTokenValid(userToken)) {
            trackDAO.addTrackToPlaylist(playlistId, track);
            return trackDAO.getAttachedTracks(playlistId);
        } else {
            throw new AuthenticationException("Usertoken mismatch.");
        }
    }

    @Override
    public TrackCollection removeTrackFromPlaylist(String token, int playlistId, int trackId) throws AuthenticationException {
        UserToken userToken = getUserToken(token);
        if (tokenDAO.isTokenValid(userToken)) {
            trackDAO.removeTrackFromPlaylist(playlistId, trackId);
            return trackDAO.getAttachedTracks(playlistId);
        } else {
            throw new AuthenticationException("Usertoken mismatch.");
        }
    }

    private UserToken getUserToken(String token) {
        return tokenDAO.getUserToken(token);
    }
}
