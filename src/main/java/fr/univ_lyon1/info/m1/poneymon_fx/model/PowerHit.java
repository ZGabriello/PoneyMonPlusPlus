package fr.univ_lyon1.info.m1.poneymon_fx.model;


public class PowerHit extends PowerModel {

    DivideSpeedState state = new DivideSpeedState(3);
    
    @Override
    public void use(PoneyModel p) {
        if (this.getPowerIsCasted() == false) {
            state.applyState(p);
            this.setPowerIsCasted(true);
        }
    }

}
    

