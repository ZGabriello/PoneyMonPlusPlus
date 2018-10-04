/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Classe .
 * @author Elo
 */
public class StateModel {

    /**
     * boleen est actif.
     */
    private boolean isActive;

    /**
     * Constructeur de state.
     */
    public StateModel() {
        this.isActive = false;

    }
    
    /**
     * setter.
     * 
     * @param state etat
     */
    public void setIsActive(boolean state) {
        this.isActive = state;
    }

    /**
     * Getter .
     * 
     * @return état
     */
    public boolean getIsActive() {
        return this.isActive;
    }  

}
