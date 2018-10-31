package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.track.Line;

/**
 * Classe abstraite objet, sous-classe de ramassable.
 * 
 * @author Elo
 */
public abstract class ImmediateItem extends ItemModel {
        
            
    public ImmediateItem(int pos) {
        this.position = pos;
    }
    
    ImmediateItem(Line line, int position, double posWidth) {
        
        this.line = line;
        this.position = position;  
        
        
    }
    
    
}
