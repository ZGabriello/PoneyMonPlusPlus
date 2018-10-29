package fr.univ_lyon1.info.m1.poneymon_fx.model;


import static fr.univ_lyon1.info.m1.poneymon_fx.model.EnragedPoneyModel.SPEED_DIVIDER_ENRAGED;
import java.sql.Timestamp;

/**
 * Classe gérant la perte de vitesse (malus).
 * 
 * @author Elo
 */
public class DivideSpeedState extends State {
    
    public DivideSpeedState(long duration) {
        super(duration);
    }
    
    /**
     * Méthode appliquant l'état au poney.
     *  
     * @param pm poneyModel
     */
    @Override
    public void applyState(PoneyModel pm) {
        this.startTime = new Timestamp(System.currentTimeMillis());
        
        double speed = pm.getSpeed();

        pm.divideSpeed(SPEED_DIVIDER_ENRAGED);
        
         while(true)
        {
            if(checkExpired())
            {
                unapplyState(pm, speed);
                break;
            }
        }        
    }            
    
}
