package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.state.MultiplySpeedState;

/**
 * Objet boost.
 * @author Elo
 */
public class BoostItemModel extends ImmediateItem {
        
    MultiplySpeedState state = new MultiplySpeedState(3);

    
    public BoostItemModel(int position, double posWidth) {
        super(position, posWidth);
    }   
    
    
    @Override
    public void collision(PoneyModel pm) {
        pm.addState(state);
        applyState(pm);
    }

    
}
