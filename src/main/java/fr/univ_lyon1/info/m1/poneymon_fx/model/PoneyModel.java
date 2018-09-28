package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.util.Observable;
import java.util.Random;

/**
 * Classe gérant la logique du Poney.
 *
 */
public class PoneyModel extends Observable {
    static final int speedDivider = 200;
    static final double minSpeed = 0.1;
    static final double maxSpeed = 0.9;
    
    double progress;
    double speed;
    String color;
    
    boolean powerState;
    
    int nbTurns;
    
    Strategy strat;
    
    Random randomGen;
    
    /**
     * Constructeur du PoneyModel.
     *
     * @param color Contient la couleur du poney
     */
    public PoneyModel(String color) {
        progress = 0.0;
        this.color = color;
        //this.neighs = poneys;
        powerState = false;
        
        nbTurns = 0;
        
        strat = new Strategy();
        setRandSpeed();
    }
    
    /**
     * Avancée du poney.
     * @return position du poney
     */
    public double step() {
        progress += speed;
        if (progress > 1.0) {
            progress = 0;
            nbTurns++;
            setRandSpeed();
        }
        strat.action();        
        return progress;
    }
    
    /**
     * Changement d'etat du poney (Utilisation du pouvoir).
     */
    public void usePower() {
        if (powerState == false) {
            powerState = true;
            setChanged();
            notifyObservers(new PowerNotification(true));
        }
    }
    
    /**
     * Sortie de l'etat d'utilisation du pouvoir du poney.
     */
    public void endPower() {
        powerState = false;
        setChanged();
        notifyObservers(new PowerNotification(false));
    }
    
    /**
     * Controle de la vitesse du poney.
     */
    public void controlSpeed() {
        if (speed > maxSpeed) {
            speed = maxSpeed;
        } else if (speed < minSpeed) {
            speed = minSpeed;
        }
        
        speed /= speedDivider;
    }
    
    /**
     * Mutateur pour changer la vitesse du poney.
     * 
     * @param speed vitesse comprise entre 0 et 1 avant contrôle
     */
    public void setSpeed(double speed) {
        this.speed = speed;
        
        controlSpeed();
    }
    
    public double getSpeed() {
        return speed;
    }
    
    /**
     * Mutateur pour donner une vitesse aléatoire au poney.
     * 
     */
    public void setRandSpeed() {
        // vitesse aleatoire entre 0.0 et 1.0
        randomGen = new Random();
        speed = randomGen.nextFloat();
        
        controlSpeed();
    }
    
    public int getSpeedDivider() {
        return speedDivider;
    }
    
    public int getNbTours() {
        return nbTurns;
    }
    
    public double getProgress() {
        return progress;
    }
    
    // 
    public double distanceTo(PoneyModel poney) { 
        return progress - poney.progress;
    }
    
    public void addStrategy(Strategy s) {
        strat = s;
    }
}