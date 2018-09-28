package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Classe gérant la logique du NyanPoney.
 *
 */
public class NyanPoneyModel extends PoneyModel {
    static final int SPEED_MULTIPLIER = 10;
    public int exhaustion;
    
    public NyanPoneyModel(String color) {
        super(color);
        exhaustion = 0;
    }
    
    @Override
    public double step() {
        progress += speed;
        if (progress > 1.0) {
            progress = 0;
            nbTurns++;
            setRandSpeed();
            
            if (powerState) {
                endPower();
            }
        }
        strat.action();
        return progress;
    }
    
    @Override
    public void usePower() {
        if (powerState == false && exhaustion < 1) {
            powerState = true;
            exhaustion++;
            speed *= SPEED_MULTIPLIER;
            
            setChanged();
            notifyObservers(new PowerNotification(true));
        }
    }
    
    public int getSpeedMultiplier() {
    	return SPEED_MULTIPLIER;
    }
}