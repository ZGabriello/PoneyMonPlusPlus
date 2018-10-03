/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 *
 * @author Elo
 */
public class StateModel {

    private boolean isActive;

  
    private Bonus bonus;

    public StateModel(Bonus bonus) {
        this.isActive = false;
        this.bonus = bonus;

    }
    
    public void setIsActive(boolean state) {
        this.isActive = state;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    
    public String toString() {
        return bonus.toString();
    }

}
