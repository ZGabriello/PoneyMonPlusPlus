package fr.univ_lyon1.info.m1.poneymon_fx.model.notification;

import fr.univ_lyon1.info.m1.poneymon_fx.model.TrackModel;

/**
 * Notification envoyée pour initialiser une TrackView.
 *
 */
public class TrackInitializationNotification extends Notification {
    TrackModel track;
    
    public TrackInitializationNotification(TrackModel track) {
        super("TRACK");
        this.track = track;
    }
    
    public TrackModel getTrack() {
        return track;
    }
}