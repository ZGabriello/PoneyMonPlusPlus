package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.Strategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PoneyStartNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PowerNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.track.LanePart;
import fr.univ_lyon1.info.m1.poneymon_fx.model.track.Line;
import fr.univ_lyon1.info.m1.poneymon_fx.model.power.PowerModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.state.State;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Classe gérant la logique du Poney.
 *
 */
public abstract class PoneyModel extends Observable implements Serializable{
    static final int SPEED_DIVIDER = 5;
    static final double MIN_SPEED = 0;
    static final double MAX_SPEED = 0.5;

    boolean isTouched;
    double acceleration;

    Line beginLine;
    LanePart curLane;
    
    double[] infos;
    
    double distance;
    double curLaneLength;
    double progress;
    int lanesPassed;
    
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
        distance = 0;
        progress = 0;
        powerState = false;
        nbPowers = 0;
        
        nbTurns = 0;
        lanesPassed=0;
        ia = false;
        
        speed = MIN_SPEED;
        acceleration = 0.002;


        this.states = new ArrayList<>();
        this.progress = 0.0;
        setRandSpeed();
    }

    /**
     * Constructeur de PoneyModel.
     *
     * @param color Contient la couleur du poney
     * @param position position du poney sur le terrain
     */
    public PoneyModel(String color, Line beginLine, int position) {
        this();
        this.color = color;
        this.beginLine = beginLine;
        this.position = position;

        setCurLane(beginLine.getNext(position));
        infos = curLane.getInfos(progress);
        
        setDistance(0);
    }

    /**
     * Constructeur de PoneyModel avec IA.
     *
     * @param color Contient la couleur du poney
     * @param position position du poney sur le terrain
     * @param strategy stratégie à utiliser pour l'ia
     */
    public PoneyModel(String color, Line beginLine, int position, Strategy strategy) {
        this(color, beginLine, position);
        ia = true;
        this.strategy = strategy;
    }

    /**
     * Fonction chargée de faire avancer le poney à chaque frame.
     * 
     */
    public void step() {
        if (ia) {
            strategy.checkPower();
        }
        
        setDistance(distance + speed / SPEED_DIVIDER);
        accelerer();
        
        if (distance > curLaneLength) {
            goToNextLane();
            
            if (curLane.getBeginLine() == beginLine) {
                newTurn();
            }
        }
        
        infos = curLane.getInfos(progress);
        
        checkStates();
    }
    
    protected void checkStates() {
        List<State> expiredStates = new ArrayList<>();
        
        for (State state : states) {
            if (state.checkExpired()) {
                state.unapplyState(this);
                expiredStates.add(state);
            }
        }
        
        for (State state : expiredStates) {
            removeState(state);
        }
    }
    
    protected void goToNextLane() {
        lanesPassed++;
        double overDistance = distance - curLaneLength;
        setCurLane(curLane.getNext());
        setDistance(overDistance);
        
        double[] points = curLane.getPoints();
        double x0 = (points[0] + points[2]) / 2;
        double y0 = (points[1] + points[3]) / 2;
    } 
    
    protected void setOnRightLane(){
        LanePart lane = beginLine.getNext(position);
        for (int i = 0; i<lanesPassed;i++){
            lane = lane.getNext();
        }
        curLane = lane;
    }

    /**
     * Déplace le poney sur la voie de gauche si possible.
     */
    public void goToLeftLane() {
        LanePart leftLane = curLane.getLeft();
        
        if (leftLane != null) {
            setCurLane(leftLane);
        }
        
        setDistance(distance);
    }
    
    /**
     * Déplace le poney sur la voie de droite si possible.
     */
    public void goToRightLane() {
        LanePart rightLane = curLane.getRight();
        
        if (rightLane != null) {
            setCurLane(rightLane);
        }
        
        setDistance(distance);
    }
    
    /**
     * Action à effectuer au début d'un nouveau tour.
     */
    protected void newTurn() {
        setDistance(0);
        nbTurns++;
        setRandSpeed();
    }

    /**
     * Utilisation du pouvoir.
     */
    public void usePower() {
        ++nbPowers;
        powerState = true;
        power.use(this);
        
        setChanged();
        notifyObservers(new PowerNotification(true));
    }
    
    public void usePower(PoneyModel p){
        
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
     * Change la voie actuelle que parcourt le poney.
     */
    public void setCurLane(LanePart lp) {
        curLane = lp;
        curLaneLength = lp.getLength();
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
    
    public void setDistance(double distance) {
        this.distance = distance;
        progress = distance / curLaneLength;
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
    
    public double getDistance() {
        return distance;
    }

    public double getProgress() {
        return progress;
    }
    
    public double[] getInfos() {
        return infos;
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
        return (distance + nbTurns) - (poney.distance + poney.nbTurns);
    }

    /**
     * Méthode gérant l'accéleration du poney.
     * 
     */  
    public void accelerer() {
        setSpeed(speed + acceleration);
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


    public void divideSpeed(int speedDivider) {
        speed /= speedDivider;
                
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isPowerState() {
        return powerState;
    }

    public void setPowerState(boolean powerState) {
        this.powerState = powerState;
    }

    public int getNbPowers() {
        return nbPowers;
    }

    public void setNbPowers(int nbPowers) {
        this.nbPowers = nbPowers;
    }

    public int getNbTurns() {
        return nbTurns;
    }

    public void setNbTurns(int nbTurns) {
        this.nbTurns = nbTurns;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Random getRandomGen() {
        return randomGen;
    }

    public void setRandomGen(Random randomGen) {
        this.randomGen = randomGen;
    }

}