package fr.univ_lyon1.info.m1.poneymon_fx.model.state;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

/**
 * Classe gérant l'état du poney (Bonus ou malus).
 *
 * @author Elo
 */
public abstract class State {
    long startTime;
    long endTime;
    long duration;
    int nbTurn;
    
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
    
    public State(int nbTurn) {
        this.nbTurn = nbTurn;
        fromPower = true;
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
    
    public boolean getFromPower() {
        return this.fromPower;
    }
    
    /**
     * Eliminer l'état du poney.
     * 
     * @param pm un poneyModel
     */
    public void unapplyState(PoneyModel pm, double lastSpeed) {
        if (fromPower) {
            pm.endPower();
        }
        pm.setSpeed(lastSpeed);      

    }

}
