package fr.univ_lyon1.info.m1.poneymon_fx.model.state;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Classe gérant l'état du poney (Bonus ou malus).
 *
 * @author Elo
 */
public abstract class State implements Serializable{
    long startTime;
    long endTime;
    long duration;
    
    boolean fromPower;
    
    /**
     * Constructeur de State.
     * 
     * @param duration une durée
     */
    public State(long duration) {
        this.duration = duration;
        
        fromPower = false;
    }

    /**
     * Applique un état au poney.
     *
     * @param pm un poneyModel
     */
    public void applyState(PoneyModel pm) {
        startTime = System.currentTimeMillis();
        endTime = startTime + duration * 1000;
    }

    /**
     * Vérifie si l'état a expiré.
     * 
     * @return vrai ou faux
     */
    public boolean checkExpired() {
        return System.currentTimeMillis()  >= endTime;
    }

    public void setFromPower(boolean b) {
        fromPower = b;
    }
    
    /**
     * Eliminer l'état du poney.
     * 
     * @param pm un poneyModel
     */
    public void unapplyState(PoneyModel pm) {
        if (fromPower) {
            pm.endPower();
        }
    }

}
