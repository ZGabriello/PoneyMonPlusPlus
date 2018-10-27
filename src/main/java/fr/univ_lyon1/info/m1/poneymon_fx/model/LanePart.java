package fr.univ_lyon1.info.m1.poneymon_fx.model;

import static java.lang.Math.sqrt;
import java.util.List;
import java.util.NavigableMap;

/**
 * Classe gérant un bout de voie.
 *
 */
public class LanePart {
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
    
    double length;
    
    LanePart nextLanePart;
    
    LanePart leftLanePart;
    LanePart rightLanePart;
    
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
}