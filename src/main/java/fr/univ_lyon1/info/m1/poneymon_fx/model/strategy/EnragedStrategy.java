package fr.univ_lyon1.info.m1.poneymon_fx.model.strategy;

import fr.univ_lyon1.info.m1.poneymon_fx.model.EnragedPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel;

/**
 * Classe représentant une stratégie dédiée au EnragedPoney.
 *
 */
public abstract class EnragedStrategy extends Strategy {
    EnragedPoneyModel myPoney;
    static final int SPEED_MULTIPLIER = NyanPoneyModel.getSpeedMultiplier();

    /**
     * Constructeur de EnragedStrategy.
     * @param f terrain de jeu
     * @param i position du poney correspondant a la strategie
     */
    public EnragedStrategy(FieldModel f, EnragedPoneyModel p, int i) {
        super(f, i);
        myPoney = p;
    }
}