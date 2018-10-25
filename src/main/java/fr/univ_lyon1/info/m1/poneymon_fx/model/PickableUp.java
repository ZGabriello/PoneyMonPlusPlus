package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Classe abstraite ramassable.
 * 
 * @author Elo
 */
public abstract class PickableUp {
    
    public State state;
    
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
    
}
