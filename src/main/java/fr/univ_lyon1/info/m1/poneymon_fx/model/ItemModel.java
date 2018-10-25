package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Classe abstraite objet, sous-classe de ramassable.
 * 
 * @author Elo
 */
public abstract class ItemModel extends PickableUp {
        
    int position;
        
    public ItemModel(int pos) {
        this.position = pos;
    }
}
