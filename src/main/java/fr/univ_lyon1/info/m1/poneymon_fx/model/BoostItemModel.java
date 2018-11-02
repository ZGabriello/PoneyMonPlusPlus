package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.state.MultiplySpeedState;
import fr.univ_lyon1.info.m1.poneymon_fx.model.track.LanePart;

/**
 * Objet boost.
 * @author Elo
 */
public class BoostItemModel extends ImmediateItem {
        
    //MultiplySpeedState state = new MultiplySpeedState(3);

    
    public BoostItemModel() {
        state = new MultiplySpeedState(3);
    }   
    
    
    @Override
    public void collision(PoneyModel pm) {
        pm.addState(state);
        applyState(pm);
    }

    
}
