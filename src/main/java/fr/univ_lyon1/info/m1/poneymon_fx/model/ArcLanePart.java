package fr.univ_lyon1.info.m1.poneymon_fx.model;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;

/**
 * Classe gérant les bouts de voie circulaires.
 *
 */
public class ArcLanePart extends LanePart {
    double centerX;
    double centerY;
    
    double innerRadius;
    double outerRadius;
    double midRadius;
 
    
    /**
     * Constructeur de ArcLanePart.
     * @param l1 Line de début de LanePart
     * @param beginLaneId voie concernée sur la Line de début
     * @param l2 Line de fin de LanePart
     * @param endLaneId voie concernée sur la line de fin
     *
     */
    public ArcLanePart(Line l1, int beginLaneId, Line l2, int endLaneId) {
        super(l1, beginLaneId, l2, endLaneId);
        
        double[] center = null;
        try {
            center = Util.getIntersection(l1, l2);
        } catch (Util.ParallelLinesException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        centerX = center[0];
        centerY = center[1];

        innerRadius = Util.dist(centerX, centerY, x1, y1);
        outerRadius = innerRadius + Line.laneWidth;
        midRadius = (innerRadius + outerRadius) / 2;
        
        double[] minMax = Util.minMaxInAngleRange(l1, l2);

        minX = centerX + minMax[0] * outerRadius;
        minY = centerY + minMax[1] * outerRadius;
        maxX = centerX + minMax[2] * outerRadius;
        maxY = centerY + minMax[3] * outerRadius;
        
        // la longueur de l'arc au milieu de celui-ci
        length = arcLength * midRadius;
    }
    
    public double[] getCenter() {
        return new double[] {centerX, centerY};
    }
    
    public String getShape() {
        return "ARC";
    }
    
    public double[] getInfos(double progress) {
        double currentAngle = startAngle + progress * arcLength;
        double poneyDirection = currentAngle + PI / 2;
        double x = centerX + cos(currentAngle) * midRadius;
        double y = centerY + sin(currentAngle) * midRadius;
        
        return new double[] {x, y, poneyDirection};
    }
    
    public double getInnerRadius() {
        return innerRadius;
    }
    
    public double getOuterRadius() {
        return outerRadius;
    }
    
    public double getStartAngle() {
        return startAngle;
    }
    
    public double getEndAngle() {
        return endAngle;
    }
    
    public double getArcLength() {
        return arcLength;
    }
}
