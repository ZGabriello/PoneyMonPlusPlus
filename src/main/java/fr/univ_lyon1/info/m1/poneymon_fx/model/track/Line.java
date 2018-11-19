package fr.univ_lyon1.info.m1.poneymon_fx.model.track;

import java.io.Serializable;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe gérant une ligne, relier 2 lignes crée une voie, ou plusieurs selon
 * la taille de la ligne.
 *
 */
public class Line implements Serializable{
    int id;
    double x;
    double y;
    
    /**
     * l'angle est compris entre 0 et 7, et représente un multiple de PI/4.
     */
    int multiple;
    int zIndex;
    
    int nbLanes;
    List<LanePart> prevLaneParts;
    List<LanePart> nextLaneParts;
    
    double angle;
    double slope;
   
    static final int numberOfAngles = 8; // doit être un multiple de 8
    static final double minAngle = PI / (numberOfAngles / 2);
    public static final double laneWidth = 0.5; // longueur d'une voie
    static final double slopeInf = 100;
    
    /**
     * Constructeur d'une ligne.
     * @param id identifiant unique d'une ligne
     * @param x position sur l'axe des abscisses
     * @param y position sur l'axe des ordonnées
     * @param multiple multiple de minAngle réprésentant l'orientation angulaire de la ligne
     * @param zIndex position verticale par rapport aux autres lignes
     * @param nbLanes nombre de voies partants de cette ligne
     */
    public Line(int id, double x, double y, int multiple, int zIndex, int nbLanes) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.multiple = Util.modulo(multiple, numberOfAngles);
        this.zIndex = zIndex;
        
        this.nbLanes = nbLanes;
        prevLaneParts = new ArrayList<>(nbLanes);
        nextLaneParts = new ArrayList<>(nbLanes);
        for (int i = 0; i < nbLanes; ++i) {
            prevLaneParts.add(null);
            nextLaneParts.add(null);
        }
        
        angle = multiple * minAngle;
        slope = Util.computeSlope(angle, x, y);
    }
    
    /**
     * Récupère les 2 points délimitant nbLanes et commençant à laneId.
     * @param laneId id de la première voie concernée
     * @param nbLanes nombre de voie passé
     */
    public double[] getPoints(int laneId, int nbLanes) {
        if (nbLanes + laneId > this.nbLanes) {
            System.err.println("Attention, nbLanes trop grand, "
                    + "un point récupéré n'appartient par à cette ligne");
        }
        double angle = getAngle();
        double xStep = laneWidth * cos(angle);
        double yStep = laneWidth * sin(angle);
        
        double x0 = x + xStep * laneId;
        double y0 = y + yStep * laneId;
        
        double x1 = x0 + xStep * nbLanes;
        double y1 = y0 + yStep * nbLanes;
        
        return new double[] {x0, y0, x1, y1};
    }
    
    public int getId() {
        return id;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public int getMultiple() {
        return multiple;
    }
    
    public double getAngle() {
        return angle;
    }
    
    public double getOppositeAngle() {
        return Util.oppositeMultOf(multiple) * minAngle;
    }
    
    public int getNbLanes() {
        return nbLanes;
    }
    
    public double getLineLength() {
        return nbLanes * laneWidth;
    }
    
    public void setPrev(int i, LanePart lp) {
        prevLaneParts.set(i, lp);
    }
    
    public void setNext(int i, LanePart lp) {
        nextLaneParts.set(i, lp);
    }
    
    public LanePart getPrev(int i) {
        return prevLaneParts.get(i);
    }
    
    public LanePart getNext(int i) {
        return nextLaneParts.get(i);
    }
}