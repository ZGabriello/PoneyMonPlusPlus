package fr.univ_lyon1.info.m1.poneymon_fx.model.state;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

import static fr.univ_lyon1.info.m1.poneymon_fx.model.EnragedPoneyModel.SPEED_DIVIDER_ENRAGED;

/**
 * Classe gérant la perte de vitesse (malus).
 *
 * @author Elo
 */
public class DivideSpeedState extends State {

    public DivideSpeedState(long duration) {
        super(duration);
    }

    /**
     * Méthode appliquant l'état au poney.
     *
     * @param pm poneyModel
     */
    public void applyState(PoneyModel pm) {
        super.applyState(pm);

        pm.divideSpeed(SPEED_DIVIDER_ENRAGED);
    }
}
