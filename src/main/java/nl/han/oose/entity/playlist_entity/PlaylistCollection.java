package nl.han.oose.entity.playlist_entity;


import java.util.ArrayList;
import java.util.List;


public class PlaylistCollection {

    private List<Playlist> playlists = new ArrayList<>();
    private int length;

    public PlaylistCollection() {
    }

    public List<Playlist> getPlaylists() {

        return this.playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}