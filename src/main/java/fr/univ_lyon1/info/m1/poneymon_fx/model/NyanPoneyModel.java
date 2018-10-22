package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PowerNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.ImStillHereNyanStrategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.NyanStrategy;

/**
 * Classe gérant la logique du NyanPoney.
 *
 */
public class NyanPoneyModel extends PoneyModel {

    static final int SPEED_MULTIPLIER = 3;

    /**
     * Constructeur de NyanPoney sans modèle et autres paramètres, pour tests.
     */
    public NyanPoneyModel() {
        super();
    }

    /**
     * Constructeur de NyanPoney pour joueur humain.
     *
     * @param color couleur du poney
     * @param position position du poney dans le modèle
     * @param f modèle
     */
    public NyanPoneyModel(String color, int position, FieldModel f) {
        super(color, position);

        // stratégie par défaut, peut-être utile sur un joueur humain
        // en cas de déconnexion en réseau
        strategy = new ImStillHereNyanStrategy(f, this, position);
    }

    /**
     * Constructeur de NyanPoney avec IA.
     *
     * @param color couleur du poney
     * @param position position du poney dans le modèle
     * @param strategy stratégie à utiliser pour l'ia
     */
    public NyanPoneyModel(String color, int position, NyanStrategy strategy) {
        super(color, position, strategy);
    }

    public void setStrategy(NyanStrategy s) {
        ia = true;
        strategy = s;
    }

    @Override
    protected void newTurn() {
        super.newTurn();
        if (powerState) {
            endPower();
        }
    }

    @Override
    public void usePower() {

        if (powerState == false && nbPowers < 1) {
            ++nbPowers;
            powerState = true;

            speed *= SPEED_MULTIPLIER;

            setChanged();
            notifyObservers(new PowerNotification(true));
        }

    }

    public static int getSpeedMultiplier() {
        return SPEED_MULTIPLIER;
    }

}
