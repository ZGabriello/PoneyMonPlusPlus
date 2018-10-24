package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PowerNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.ImStillHereNyanStrategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.NyanStrategy;

/**
 * Classe gérant la logique du NyanPoney.
 *
 */
public class NyanPoneyModel extends PoneyModel {

    static final int SPEED_MULTIPLIER = 2;
    

    /**
     * Constructeur de NyanPoney sans modèle et autres paramètres, pour tests.
     */
    public NyanPoneyModel() {
        super();
        power = new PowerDoubleSpeed();
        
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
        power = new PowerDoubleSpeed();

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
    public NyanPoneyModel(String color, int position, NyanStrategy strategy,PowerModel p) {
        super(color, position, strategy);
            this.setPower(p);
        
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
            power.use(this);
            setChanged();
            notifyObservers(new PowerNotification(true));
        }        

    }
    
    @Override
    public void applyState() {
         //TODO : si effet immédiat, appliquer l'état
        if (states != null) {
            for (State state : states) {
                state.applyState(this);
            }
        }
        
        setChanged();
        notifyObservers(new PowerNotification(true));
        
    }

    public static int getSpeedMultiplier() {
        return SPEED_MULTIPLIER;
    }

}
