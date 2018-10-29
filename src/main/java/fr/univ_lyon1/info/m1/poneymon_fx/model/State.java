package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.sql.Timestamp;

/**
 * Classe gérant l'état du poney (Bonus ou malus).
 *
 * @author Elo
 */
public abstract class State {

    Timestamp startTime;
    Timestamp endTime;
    long duration;

    boolean isExpired;

    /**
     * Constructeur de State.
     * 
     * @param duration une durée
     */
    public State(long duration) {
 
        this.duration = duration;
        this.isExpired = false;
    }

    /**
     * Applique un état au poney.
     *
     * @param pm un poneyModel
     */
    public void applyState(PoneyModel pm) {

    }

    /**
     * Vérifie si l'état a expiré.
     * 
     * @return vrai ou faux
     */
    public boolean checkExpired() {
        
        this.isExpired = startTime.getTime() + 1000 * duration < System.currentTimeMillis();

        return this.isExpired;
    }

    /**
     * Eliminer l'état du poney.
     * 
     * @param pm un poneyModel
     */
    public void unapplyState(PoneyModel pm, double sp) {

        
        pm.removeState(this);
        pm.endPower();
        pm.setSpeed(sp);
        System.out.println("désappliquer état : " + pm.getSpeed());


    }

}
