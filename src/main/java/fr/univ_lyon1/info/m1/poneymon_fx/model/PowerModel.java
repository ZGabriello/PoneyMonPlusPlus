/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.model;


public abstract class PowerModel {
    
    boolean powerUsed;
    
    
    public boolean getPowerIsCasted(){
        return this.powerUsed;
    }
    
    public void setPowerIsCasted(boolean b){
        this.powerUsed= b;
    }
    
    public abstract void  use(PoneyModel p);
}
