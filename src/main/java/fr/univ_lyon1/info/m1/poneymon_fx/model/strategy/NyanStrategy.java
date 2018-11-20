package fr.univ_lyon1.info.m1.poneymon_fx.model.strategy;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel;

/**
 * Classe représentant une stratégie dédiée au NyanPoney.
 *
 */
public abstract class NyanStrategy extends Strategy {

    NyanPoneyModel myPoney;
    static final int SPEED_MULTIPLIER = NyanPoneyModel.getSpeedMultiplier();

    /**
     * Constructeur de NyanStrategy.
     *
     * @param f terrain de jeu
     * @param i position du poney correspondant a la strategie
     */
    public NyanStrategy(FieldModel f, NyanPoneyModel p, int i) {
        super(f, i);
        myPoney = p;
    }
}
