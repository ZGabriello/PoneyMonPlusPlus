package fr.univ_lyon1.info.m1.poneymon_fx.model;

import static fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel.SPEED_MULTIPLIER;
import java.sql.Timestamp;

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

        pm.multiplySpeed(SPEED_MULTIPLIER);       
        unapplyState(pm);
    }

}
