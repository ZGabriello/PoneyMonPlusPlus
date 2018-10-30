package fr.univ_lyon1.info.m1.poneymon_fx.model.power;

import fr.univ_lyon1.info.m1.poneymon_fx.model.state.MultiplySpeedState;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;


public class NyanPower extends PowerModel {
    
    MultiplySpeedState state = new MultiplySpeedState(3);
    
    @Override
    public void use(PoneyModel p) {
        if (this.getPowerIsCasted() == false) {
            p.addState(state);
            state.applyState(p);
            state.setFromPower(true);
            this.setPowerIsCasted(true);
        }
    } 
}

