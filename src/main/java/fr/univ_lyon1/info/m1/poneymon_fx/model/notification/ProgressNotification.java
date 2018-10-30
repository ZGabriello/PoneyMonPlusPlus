package fr.univ_lyon1.info.m1.poneymon_fx.model.notification;

import java.util.List;

/**
 * Notification envoyée quand les poneys progressent sur le Terrain.
 *
 */
public class ProgressNotification extends Notification {
    List<double[]> coords;
    double[] angles;
    
    /**
     * Constructeur de la notification d'avancement du poney.
     * @param coords position de chaque poney
     * @param angles angle de direction de chaque poney
     */
    public ProgressNotification(List<double[]> coords, double[] angles) {
        super("PROGRESS");
        this.coords = coords;
        this.angles = angles;
    }
    
    public List<double[]> getCoords() {
        return coords;
    }
    
    public double[] getAngles() {
        return angles;
    }
}