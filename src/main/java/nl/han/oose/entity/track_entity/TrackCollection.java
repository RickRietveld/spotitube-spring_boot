package nl.han.oose.entity.track_entity;

import java.util.ArrayList;
import java.util.List;

public class TrackCollection {

    private List<Track> tracks = new ArrayList<>();

    public TrackCollection() {

    }

    public List<Track> getTracks() {

        return this.tracks;
    }

    public void setTracks(List<Track> tracks) {

        this.tracks = tracks;
    }
}