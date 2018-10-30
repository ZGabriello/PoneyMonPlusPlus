package fr.univ_lyon1.info.m1.poneymon_fx.model.power;

import fr.univ_lyon1.info.m1.poneymon_fx.model.state.DivideSpeedState;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;


public class BitePower extends PowerModel {

    DivideSpeedState state = new DivideSpeedState(3);
    
    @Override
    public void use(PoneyModel p) {
        if (this.getPowerIsCasted() == false) {
            state.applyState(p);
            this.setPowerIsCasted(true);
        }
    }
}