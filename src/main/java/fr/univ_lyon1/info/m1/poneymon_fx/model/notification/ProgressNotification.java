package fr.univ_lyon1.info.m1.poneymon_fx.model.notification;

import java.util.List;

/**
 * Notification envoyée quand les poneys progressent sur le Terrain.
 *
 */
public class ProgressNotification extends Notification {
    List<Double> progresses;
    
    public ProgressNotification(List<Double> progresses) {
        super("PROGRESS");
        this.progresses = progresses;
    }
    
    public List<Double> getProgresses() {
        return progresses;
    }
}