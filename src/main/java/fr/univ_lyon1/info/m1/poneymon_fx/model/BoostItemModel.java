package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Objet boost.
 * @author Elo
 */
public class BoostItemModel extends ItemModel {
        
    public BoostItemModel(int pos) {
        super(pos);
    }
    
    @Override
    public void collision(PoneyModel pm) {
        applyState(pm);
    }

    
}
