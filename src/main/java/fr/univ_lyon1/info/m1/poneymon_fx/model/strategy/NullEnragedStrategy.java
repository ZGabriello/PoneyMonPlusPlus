package fr.univ_lyon1.info.m1.poneymon_fx.model.strategy;

import fr.univ_lyon1.info.m1.poneymon_fx.model.EnragedPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel;

public class NullEnragedStrategy extends EnragedStrategy {

    public NullEnragedStrategy(FieldModel f, EnragedPoneyModel p, int i) {
        super(f, p, i);
    }

    @Override
    public void checkPower() {
    }
}
