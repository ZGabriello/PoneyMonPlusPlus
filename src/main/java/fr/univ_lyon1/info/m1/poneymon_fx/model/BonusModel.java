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
public class BonusModel {
    
    private boolean isActive;
    
    private boolean hasImmediateEffect;
    
    private double x;
    private double y;
    
    private Bonus bonus; 
    
    
    public BonusModel(Bonus bonus, double x, double y){
        this.isActive = false;
        this.bonus = bonus;
        this.x = x;
        this.y = y;
    }
    
    public void immediateEffect() {
        this.hasImmediateEffect = true;
    }
    
    public void setIsActive(boolean state) {
        this.isActive = state;
    }
    
    public boolean getIsActive() {
        return this.isActive;
    }
    
    public boolean getHasImmediateEffect() {
        return this.hasImmediateEffect;
    }
          
    
    public String toString() {
        return bonus.toString(); 
    }
                  
}
