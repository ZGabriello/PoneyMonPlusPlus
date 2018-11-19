package fr.univ_lyon1.info.m1.poneymon_fx.model.power;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import java.io.Serializable;


public abstract class PowerModel implements Serializable{
    
    boolean powerUsed;
    
    
    public boolean getPowerIsCasted() {
        return this.powerUsed;
    }
    
    public void setPowerIsCasted(boolean b) {
        this.powerUsed = b;
    }
    
    public abstract void use(PoneyModel p);
}
