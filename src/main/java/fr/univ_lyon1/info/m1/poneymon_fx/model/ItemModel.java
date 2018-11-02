package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.state.State;

/**
 * Classe abstraite ramassable.
 * 
 * @author Elo
 */
public abstract class ItemModel {
    
    public State state;
    
    int position;
    double posWidth;
        
    public ItemModel(int position, double posWidth) {
        
        this.position = position;  
        this.posWidth = posWidth;        
        
    }
    
    /**
     * Gère la collision entre un ramassable et un poney.
     * 
     * @param pm poneyModel
     */
    public void collision(PoneyModel pm) {
        
    }
    
    /**
     * Applique l'état lié au ramassable.
     * 
     * @param pm poneyModel
     */
    public void applyState(PoneyModel pm) {
        pm.addState(state);
        state.applyState(pm);
    }


    public int getPosition() {
        return this.position;
    }
    
    
    public double getProg() {
        return this.posWidth;
    }
}
