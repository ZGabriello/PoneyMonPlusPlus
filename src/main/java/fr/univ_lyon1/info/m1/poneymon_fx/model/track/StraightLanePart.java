package fr.univ_lyon1.info.m1.poneymon_fx.model.track;

import static java.lang.Math.acos;
import static java.lang.Math.toDegrees;

/**
 * Classe gérant les bouts de voie en ligne droite.
 *
 */
public class StraightLanePart extends LanePart {
    double poneyDirection;
    
    /**
     * Constructeur de StraightLanePart.
     * @param l1 Line de début de LanePart
     * @param beginLaneId voie concernée sur la Line de début
     * @param l2 Line de fin de LanePart
     * @param endLaneId voie concernée sur la line de fin
     *
     */
    public StraightLanePart(Line l1, int beginLaneId, Line l2, int endLaneId) {
        super(l1, beginLaneId, l2, endLaneId);
        
        minX = Util.min(x0, x1, x2, x3);
        minY = Util.min(y0, y1, y2, y3);
        maxX = Util.max(x0, x1, x2, x3);
        maxY = Util.max(y0, y1, y2, y3);
        
        length = Util.dist(x0, y0, x2, y2);
        
        // On utilise le théorème d'al-kashi pour trouver la différence d'angle
        // entre startAngle et la direction du poney dans la voie
        
        double laneWidth = Line.laneWidth;
        double diagonal = Util.dist(x0, y0, x3, y3);
        double angleOffset = acos(((laneWidth * laneWidth + length * length 
                                    - diagonal * diagonal) / (2 * laneWidth * length)));
        poneyDirection = startAngle + angleOffset;
    }
    
    @Override
    public double[] getInfos(double t) {
        double x = (1 - t) * (x0 + x1) / 2 + t * (x2 + x3) / 2;
        double y = (1 - t) * (y0 + y1) / 2 + t * (y2 + y3) / 2;
        
        return new double[] {x, y, poneyDirection};
    }
}
