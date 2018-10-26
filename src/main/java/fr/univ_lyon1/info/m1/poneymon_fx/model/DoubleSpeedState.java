package fr.univ_lyon1.info.m1.poneymon_fx.model;

import static fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel.SPEED_MULTIPLIER;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe gérant le boost de vitesse (Bonus).
 * 
 * @author Elo
 */
public class DoubleSpeedState extends State {
    
    
    public DoubleSpeedState(long duration) {
        super(duration);
    }
    
    /**
     * Méthode appliquant l'état au poney.
     * 
     * @param pm PoneyModel
     */
    @Override
    public void applyState(PoneyModel pm) {
        this.startTime = new Timestamp(System.currentTimeMillis());
        this.endTime = new Timestamp(this.startTime.getTime() + duration);

        double speed = pm.getSpeed();
        pm.multiplySpeed(SPEED_MULTIPLIER);     
        /*try {
            Thread.currentThread().sleep(duration);
        } catch (InterruptedException ex) {
            Logger.getLogger(DoubleSpeedState.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        System.out.println("appliquer état : " + pm.getSpeed());
        /*if(checkExpired()) {*/
            unapplyState(pm, speed);
        //}
    }

}
