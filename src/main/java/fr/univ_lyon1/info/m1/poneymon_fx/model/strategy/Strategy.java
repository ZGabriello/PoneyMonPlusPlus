package fr.univ_lyon1.info.m1.poneymon_fx.model.strategy;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

/**
 * Classe représentant une stratégie d'IA.
 *
 */
public abstract class Strategy {
    FieldModel field;
    int position;

    /**
     * Constructeur de stratégie.
     * @param f terrain de jeu
     * @param p position du poney correspondant a la strategie
     */
    public Strategy(FieldModel f, int p) {
        field = f;
        position = p;
    }

    public void checkPower() {}
}
