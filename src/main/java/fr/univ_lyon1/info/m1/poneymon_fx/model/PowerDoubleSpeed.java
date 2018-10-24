package fr.univ_lyon1.info.m1.poneymon_fx.model;


public class PowerDoubleSpeed extends PowerModel {
    
    DoubleSpeedState state = new DoubleSpeedState(10);
    
    @Override
    public void use(PoneyModel p) {
        if (this.getPowerIsCasted() == false) {
            state.applyState(p);
            this.setPowerIsCasted(true);
        }
    }
}

