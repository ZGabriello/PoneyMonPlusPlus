package fr.univ_lyon1.info.m1.poneymon_fx.model.strategy;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel;

public class NotEnoughSpeedNyanStrategy extends NyanStrategy {

    public NotEnoughSpeedNyanStrategy(FieldModel f, NyanPoneyModel p, int i) {
        super(f, p, i);
    }
    
    @Override
    public void checkPower() {
        if (myPoney.getDistance() == 0.0) {
            if (myPoney.getSpeed() < 0.4 || (myPoney.getNbTours() == field.getWinAt() - 1)) {
                myPoney.usePower();
            }
        }
    }
}
