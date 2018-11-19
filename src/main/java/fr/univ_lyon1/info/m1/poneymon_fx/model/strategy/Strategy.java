package fr.univ_lyon1.info.m1.poneymon_fx.model.strategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import java.io.Serializable;

/**
 * Classe représentant une stratégie d'IA.
 *
 */
public abstract class Strategy implements Serializable {

    FieldModel field;
    int position;

    /**
     * Constructeur de stratégie.
     *
     * @param f terrain de jeu
     * @param p position du poney correspondant a la strategie
     */
    public Strategy(FieldModel f, int p) {
        field = f;
        position = p;
    }

    public void checkPower() {
    }

    public FieldModel getField() {
        return field;
    }

    public void setField(FieldModel field) {
        this.field = field;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
