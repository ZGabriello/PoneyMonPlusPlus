package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.power.PowerModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.power.NyanPower;
import fr.univ_lyon1.info.m1.poneymon_fx.model.track.Line;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PowerNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.ImStillHereNyanStrategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.NyanStrategy;

/**
 * Classe gérant la logique du NyanPoney.
 *
 */
public class NyanPoneyModel extends PoneyModel {
    public static final int SPEED_MULTIPLIER = 2;

    /**
     * Constructeur de NyanPoney sans modèle et autres paramètres, pour tests.
     */
    public NyanPoneyModel() {
        super();
        power = new NyanPower();
        
    }

    /**
     * Constructeur de NyanPoney pour joueur humain.
     *
     * @param color couleur du poney
     * @param position position du poney dans le modèle
     * @param f modèle
     */
    public NyanPoneyModel(String color, Line beginLine, int position, FieldModel f) {
        super(color, beginLine, position);
        power = new NyanPower();
        
        // stratégie par défaut, peut-être utile sur un joueur humain
        // en cas de déconnexion en réseau
        strategy = new ImStillHereNyanStrategy(f, this, 0);
    }

    /**
     * Constructeur de NyanPoney avec IA.
     *
     * @param color couleur du poney
     * @param position position du poney dans le modèle
     * @param strategy stratégie à utiliser pour l'ia
     */
    public NyanPoneyModel(String color, Line beginLine, int position,
                          NyanStrategy strategy, PowerModel p) {
        super(color, beginLine, position, strategy);
        this.setPower(p);
    }

    public void setStrategy(NyanStrategy s) {
        ia = true;
        strategy = s;
    }

    @Override
    public void usePower() {
        if (powerState == false && nbPowers < 1) {
            super.usePower();
        }        
    }
    
    public static int getSpeedMultiplier() {
        return SPEED_MULTIPLIER;
    }

}
