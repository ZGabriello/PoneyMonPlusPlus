package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.Strategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PoneyStartNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PowerNotification;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;

/**
 * Classe gérant la logique du Poney.
 *
 */
public abstract class PoneyModel extends Observable {

    static final int SPEED_DIVIDER = 200;
    static final double MIN_SPEED = 0.1;
    static final double MAX_SPEED = 0.9;

    
    
    boolean isTouched;
    double acceleration;

    double progress;
    double speed;
    String color;
    int position;
    PowerModel power;

   
    boolean powerState;
    int nbPowers;

    int nbTurns;

    Strategy strategy;
    boolean ia;

    Random randomGen;

    List<State> states;

    /**
     * Constructeur du PoneyModel sans paramètres, pour tests.
     *
     */
    public PoneyModel() {

        progress = 0.0;
        
        powerState = false;
        nbPowers = 0;
        
        nbTurns = 0;
        ia = false;
        
        speed = MIN_SPEED;
        acceleration = 0.002;


        this.states = new ArrayList<>();
        this.progress = 0.0;

        this.powerState = false;
        this.nbPowers = 0;

        this.nbTurns = 0;
        this.ia = false;


       // setRandSpeed();
    }

    /**
     * Constructeur de PoneyModel.
     *
     * @param color Contient la couleur du poney
     * @param position position du poney sur le terrain
     */
    public PoneyModel(String color, int position) {
        this();
        this.color = color;
        this.position = position;
    }

    /**
     * Constructeur de PoneyModel avec IA.
     *
     * @param color Contient la couleur du poney
     * @param position position du poney sur le terrain
     * @param strategy stratégie à utiliser pour l'ia
     */
    public PoneyModel(String color, int position, Strategy strategy) {
        ia = true;
        this.strategy = strategy;
    }

    /**
     * Avancée du poney.
     *
     * @return position du poney 
     */
    public double step() {
        if (ia) {
            strategy.checkPower();
        }


        progress += (speed / SPEED_DIVIDER);


        if (progress > 1.0) {
            newTurn();
        }

        return progress;
    }

    /**
     * Action à effectuer au début d'un nouveau tour.
     */
    protected void newTurn() {
        progress = 0;
        nbTurns++;
        //setRandSpeed();
    }

    /**
     * Utilisation du pouvoir.
     */
    public void usePower() {

    }
    
    public void usePower(PoneyModel p){
        
    }
    

    public void applyState() {
        
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
     * Initialisation des observeurs du modèle du poney.
     */
    @Override
    public void addObserver(Observer obs) {
        super.addObserver(obs);

        setChanged();
        notifyObservers(new PoneyStartNotification(color, position));
    }

    /**
     * Controle de la vitesse du poney.
     */
    private void controlSpeed() {
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        } else if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }
        
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

    public void setProgress(double p) {
        progress = p;
    }

    public void setIa(boolean b) {
        ia = b;
    }

    public double getSpeed() {
        return speed;
    }

    public String getColor() {
        return color;
    }

    public int getSpeedDivider() {
        return SPEED_DIVIDER;
    }

    public int getNbTours() {
        return nbTurns;
    }

    public double getProgress() {
        return progress;
    }

    public boolean isIa() {
        return ia;
    }

    
    public boolean getIsTouched() {
        return isTouched;
    }
    
    public void setIsTouched(boolean t) {
        this.isTouched = t;
    }
    
    public double getAcceleration() {
        return this.acceleration;
    }
    
    public void setAcceleration(double a) {
        this.acceleration = a;
    }
    
    public PowerModel getPower() {
        return this.power;
    }
    
    public void setPower(PowerModel p) {
        this.power = p;
    }
   
    

    /**
     * Calcul la distance avec le poney donné en prenant en compte les tours,
     * une distance positive veut dire qu'on est devant, et négative l'inverse.
     *
     * @param poney poney par rapport auquel on calcule la distance
     */
    public double distanceTo(PoneyModel poney) {
        return (progress + nbTurns) - (poney.progress + poney.nbTurns);
    }

    /**
     * Méthode gérant l'accéleration du poney.
     * 
     */  
    public void accelerer() {
        // test si l'acceleration est possible
        if ((this.getSpeed() + this.getAcceleration()) >= MAX_SPEED) {
            this.setSpeed(MAX_SPEED);
            
        } else {
            this.setSpeed((this.getSpeed() + this.getAcceleration()));
        }
    }
    
    /**
     * Surcharge de la fonction accelerer.
     */    
    public void accelerer(double a) {   
        // test si l'acceleration est possible
        if ((this.getSpeed() + this.getAcceleration()) >= MAX_SPEED) {
            this.setSpeed(MAX_SPEED);
        } else {
            this.setSpeed((this.getSpeed() + a));
        }
    }
    
    
    /**
     * Méthode gérant la décéleration du poney.
     */
    public void deccelerer() {
        // test si la decceleration est possible
        if ((this.getSpeed() - this.getAcceleration()) <= MIN_SPEED) {
            this.setSpeed(MIN_SPEED);
        } else {
            this.setSpeed(this.getSpeed() - this.getAcceleration());
        }
    }
    
    /**
    * Surcharge de la fonction deccelerer.
    */
    public void deccelerer(double a) {
        // test si la decceleration est possible
        if ((this.getSpeed() - a) <= MIN_SPEED) {
            this.setSpeed(MIN_SPEED);
        } else {
            this.setSpeed(this.getSpeed() - a);
        }
    }
    
    /**
     *  Baisse de la vitesse après que le poney ait été touché.
     */
    public void isTouched() {
        if (this.getIsTouched()) {
            this.setSpeed(this.getSpeed() / 2);    
        }
    }
    
   
    public void addState(State state) {
        states.add(state);
    }

    public void removeState(State state) {
        states.remove(state);
    }

    public List<State> getStates() {
        return this.states;
    }

    public boolean getPowerState() {
        return this.powerState;
    }

 
    public void setpowerState(boolean b) {
        this.powerState = b;
            
    }

    public void multiplySpeed(int speedMultipler) {
        speed *= speedMultipler;
    }

    public int getNbPowers() {
        return this.nbPowers;
    }

    public void divideSpeed(int speedDivider) {
        speed /= speedDivider;
                
    }
}

