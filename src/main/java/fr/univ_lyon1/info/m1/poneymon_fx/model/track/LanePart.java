package fr.univ_lyon1.info.m1.poneymon_fx.model.track;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import java.util.List;
import java.util.NavigableMap;

import static java.lang.Math.sqrt;

/**
 * Classe gérant un bout de voie.
 *
 */
public abstract class LanePart {
    Line beginLine;
    int beginLaneId;
    
    Line endLine;
    int endLaneId;
    
    double x0;
    double y0;
    double x1;
    double y1;
    double x2;
    double y2;
    double x3;
    double y3;
    
    double minX;
    double minY;
    double maxX;
    double maxY;
    
    double startAngle;
    double endAngle;
    double arcLength;
    
    double length;
    
    LanePart nextLane;
    
    LanePart leftLanes;
    LanePart rightLanes;
    
    //NavigableMap<Double, Objet> objets;
    //NavigableMap<Double, Obstacle> obstacles;
    
    List<PoneyModel> poneysOn;
    
    /**
     * Constructeur de LanePart.
     * @param l1 Line de début de LanePart
     * @param beginLaneId voie concernée sur la Line de début
     * @param l2 Line de fin de LanePart
     * @param endLaneId voie concernée sur la line de fin
     *
     */
    public LanePart(Line l1, int beginLaneId, Line l2, int endLaneId) {
        this.beginLine = l1;
        this.beginLaneId = beginLaneId;
        this.endLine = l2;
        this.endLaneId = endLaneId;
        
        l1.setNext(beginLaneId, this);
        l2.setPrev(endLaneId, this);
        LanePart prev = l1.getPrev(beginLaneId);
        LanePart next = l2.getNext(endLaneId);
        
        if (prev != null) {
            prev.setNext(this);
        }
        
        if (next != null) {
            nextLane = next;
        }
        
        double[] points1 = l1.getPoints(beginLaneId, 1);
        
        x0 = points1[0];
        y0 = points1[1];
        
        x1 = points1[2];
        y1 = points1[3];
        
        double[] points2 = l2.getPoints(endLaneId, 1);
        
        x2 = points2[0];
        y2 = points2[1];
        
        x3 = points2[2];
        y3 = points2[3];
        
        startAngle = beginLine.getOppositeAngle();
        endAngle = endLine.getOppositeAngle();
        arcLength = Util.getAngleDifference(l1, l2);
    }
    
    public double getLength() {
        return length;
    }
    
    public double[] getPoints() {
        return new double[] {x0, y0, x1, y1,
                             x2, y2, x3, y3};
    }
    
    public Line getBeginLine() {
        return beginLine;
    }
    
    public Line getEndLine() {
        return endLine;
    }
    
    public int getBeginLaneId() {
        return beginLaneId;
    }
    
    public int getEndLaneId() {
        return endLaneId;
    }
    
    public double getMinX() {
        return minX;
    }
    
    public double getMinY() {
        return minY;
    }
    
    public double getMaxX() {
        return maxX;
    }
    
    public double getMaxY() {
        return maxY;
    }
    
    public LanePart getNext() {
        return nextLane;
    }
    
    public abstract String getShape();
    
    public abstract double[] getInfos(double progress);
    
    public void setNext(LanePart lp) {
        nextLane = lp;
    }
}