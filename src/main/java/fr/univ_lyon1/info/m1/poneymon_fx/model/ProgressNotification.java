package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.util.List;

/**
 * Notification envoyée quand les poneys progressent sur le Terrain.
 *
 */
public class ProgressNotification extends Notification {
    public List<Double> progresses;
    
    public ProgressNotification(List<Double> progresses) {
        super("PROGRESS");
        this.progresses = progresses;
    }
}