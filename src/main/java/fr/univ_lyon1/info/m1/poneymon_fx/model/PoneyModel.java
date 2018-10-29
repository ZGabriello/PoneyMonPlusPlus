package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.Strategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PoneyStartNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PowerNotification;
import static java.lang.Math.PI;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Classe gérant la logique du Poney.
 *
 */
public abstract class PoneyModel extends Observable {
    static final int SPEED_DIVIDER = 5;
    static final double MIN_SPEED = 0.1;
    static final double MAX_SPEED = 0.9;
    
    Line beginLine;
    LanePart curLane;
    
    String laneShape;
    double[] infos;
    
    double distance;
    double curLaneLength;
    double progress;
    
    double speed;
    String color;
    int position;
    
    boolean powerState;
    int nbPowers;
    
    int nbTurns;

    Strategy strategy;
    boolean ia;
    
    Random randomGen;
    
    /**
     * Constructeur du PoneyModel sans paramètres, pour tests.
     *
     */
    public PoneyModel() {
        distance = 0;

        powerState = false;
        nbPowers = 0;
        
        nbTurns = 0;
        ia = false;

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
     * Avancée du poney.
     * @return cordonnées du poney
     */
    public void step() {
        if (ia) {
            strategy.checkPower();
        }
        
        setDistance(distance + speed / SPEED_DIVIDER);
        
        if (distance > curLaneLength) {
            nextLane();
            
            if (curLane.getBeginLine() == beginLine) {
                newTurn();
            }
        }
        
        infos = curLane.getInfos(progress);
    }
    
    
    protected void nextLane() {
        double overProgress = distance - curLaneLength;
        setCurLane(curLane.getNext());
        setDistance(overProgress);
        
        double[] points = curLane.getPoints();
        double x0 = (points[0] + points[2]) / 2;
        double y0 = (points[1] + points[3]) / 2;
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
    
    public void setCurLane(LanePart lp) {
        curLane = lp;
        curLaneLength = lp.getLength();
        laneShape = curLane.getShape();
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
    
    public String getLaneShape() {
        return laneShape;
    }
    
    public double[] getInfos() {
        return infos;
    }
    
    public boolean isIa() {
        return ia;
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
}