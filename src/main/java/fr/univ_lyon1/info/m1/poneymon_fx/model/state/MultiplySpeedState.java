package fr.univ_lyon1.info.m1.poneymon_fx.model.state;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

import static fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel.SPEED_MULTIPLIER;

/**
 * Classe gérant le boost de vitesse (Bonus).
 *
 * @author Elo
 */
public class MultiplySpeedState extends State {

    public MultiplySpeedState(long duration) {
        super(duration);
    }

    /**
     * Méthode appliquant l'état au poney.
     *
     * @param pm PoneyModel
     */
    public void applyState(PoneyModel pm) {
        super.applyState(pm);

        pm.multiplySpeed(SPEED_MULTIPLIER);
    }
    
    /**
     * Eliminer l'état du poney.
     *
     * @param pm un poneyModel
     */
    public void unapplyState(PoneyModel pm) {
        super.unapplyState(pm);
        
        pm.divideSpeed(SPEED_MULTIPLIER);
    }
}
