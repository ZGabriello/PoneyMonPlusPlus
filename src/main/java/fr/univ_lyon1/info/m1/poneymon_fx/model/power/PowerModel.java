package fr.univ_lyon1.info.m1.poneymon_fx.model.power;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;


public abstract class PowerModel {
    
    boolean powerUsed;
    
    
    public boolean getPowerIsCasted() {
        return this.powerUsed;
    }
    
    public void setPowerIsCasted(boolean b) {
        this.powerUsed = b;
    }
    
    public abstract void use(PoneyModel p);
}
