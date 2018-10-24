package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PowerNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.NyanStrategy;


public class EnragedPoneyModel extends PoneyModel  {
    
    static final int SPEED_DIVIDER_ENRAGED = 2;
    
    /**
     * Constructeur de EnragedPoney sans modèle et autres paramètres, pour tests.
     */
    public EnragedPoneyModel() {
        super();
        power = new PowerHit();
        
    }
    
    /**
     * Constructeur de EnragedPoney pour joueur humain.
     * 
     * @param color couleur du poney
     * @param position position du poney dans le modèle
     * 
     */
    public EnragedPoneyModel(String color, int position) {
        super(color, position);
        power = new PowerHit();
    }
    
    /**
     * Constructeur de Enraged avec IA.
     * @param color couleur du poney
     * @param position position du poney dans le modèle
     * @param strategy stratégie à utiliser pour l'ia
     */
    public EnragedPoneyModel(String color, int position, NyanStrategy strategy) {
        super(color, position, strategy);
        power = new PowerHit();
    }
    
    @Override
    public void usePower(PoneyModel p) {
        
        if (powerState == false && nbPowers < 1) {
            ++nbPowers;
            powerState = true;
            power.use(p);
            setChanged();
            notifyObservers(new PowerNotification(true));
        }

    }
    
}