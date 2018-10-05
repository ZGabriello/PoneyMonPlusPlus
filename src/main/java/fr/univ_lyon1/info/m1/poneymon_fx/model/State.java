/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

        this.startTime = new Timestamp(System.currentTimeMillis());
        this.endTime = new Timestamp(this.startTime.getTime() + duration);

        pm.usePower();
        
    }

    /**
     * Vérifie si l'état a expiré.
     * 
     * @return vrai ou faux
     */
    public boolean checkExpired() {
        
        if (System.currentTimeMillis() > this.endTime.getTime()) {

            this.isExpired = true;
            
        } else {
            
            this.isExpired = false;
            
        }

        return this.isExpired;
    }

    /**
     * Eliminer l'état du poney.
     * 
     * @param pm un poneyModel
     */
    public void unapplyState(PoneyModel pm) {

        pm.endPower();
        pm.removeState(this);

    }

}
