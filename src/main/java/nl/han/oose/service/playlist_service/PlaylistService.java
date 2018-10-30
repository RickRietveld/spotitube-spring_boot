package nl.han.oose.service.playlist_service;

import nl.han.oose.entity.playlist_entity.Playlist;
import nl.han.oose.entity.playlist_entity.PlaylistCollection;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

@Component
public interface PlaylistService {
    PlaylistCollection getAllPlaylists(String token) throws AuthenticationException;

    PlaylistCollection addPlaylist(String token, Playlist playlist) throws AuthenticationException;

    PlaylistCollection renamePlaylist(String token, int id, Playlist playlist) throws AuthenticationException;

    PlaylistCollection deletePlaylist(String token, int id) throws AuthenticationException;


}
