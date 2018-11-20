package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.state.State;
import fr.univ_lyon1.info.m1.poneymon_fx.model.track.LanePart;

/**
 * Classe abstraite ramassable.
 * 
 * @author Elo
 */
public abstract class ItemModel {
    
    public State state;
    
    
    /**
     * Gère la collision entre un ramassable et un poney.
     * 
     * @param pm poneyModel
     */
    public abstract void collision(PoneyModel pm);
    
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
