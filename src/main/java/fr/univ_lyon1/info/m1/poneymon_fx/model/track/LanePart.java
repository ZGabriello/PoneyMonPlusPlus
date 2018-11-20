package fr.univ_lyon1.info.m1.poneymon_fx.model.track;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import java.io.Serializable;
import java.util.List;

/**
 * Classe gérant un bout de voie.
 *
 */
public abstract class LanePart implements Serializable {

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

    LanePart leftLane;
    LanePart rightLane;

    //NavigableMap<Double, Objet> objets;
    //NavigableMap<Double, Obstacle> obstacles;
    List<PoneyModel> poneysOn;

    /**
     * Constructeur de LanePart.
     *
     * @param beginLine Line de début de LanePart
     * @param beginLaneId voie concernée sur la Line de début
     * @param endLine Line de fin de LanePart
     * @param endLaneId voie concernée sur la line de fin
     *
     */
    public LanePart(Line beginLine, int beginLaneId, Line endLine, int endLaneId) {
        this.beginLine = beginLine;
        this.beginLaneId = beginLaneId;
        this.endLine = endLine;
        this.endLaneId = endLaneId;

        discoverNextLane();
        discoverNeighbors();

        fetchPoints();
        arcLength = Util.getAngleDifference(beginLine, endLine);

        // Avec nos conventions, on pointe vers le centre du cercle dans le sens
        // trigonométrique, on veut donc l'opposé (angle qui part du centre)
        // Mais dans le sens anti-trigo, on pointe déjà vers l'extérieur du cercle
        if (arcLength >= 0) {
            startAngle = beginLine.getOppositeAngle();
            endAngle = endLine.getOppositeAngle();
        } else {
            startAngle = beginLine.getAngle();
            endAngle = endLine.getAngle();
        }
    }

    /**
     * Permet de trouver et de mettre à jour la voie suivante.
     */
    public void discoverNextLane() {
        beginLine.setNext(beginLaneId, this);
        endLine.setPrev(endLaneId, this);
        LanePart prev = beginLine.getPrev(beginLaneId);
        LanePart next = endLine.getNext(endLaneId);

        if (prev != null) {
            prev.setNext(this);
        }

        if (next != null) {
            nextLane = next;
        }
    }

    /**
     * Permet de trouver et de mettre à jour ses voisins (leftLane et
     * rightLane).
     */
    public void discoverNeighbors() {
        leftLane = beginLine.getLeftLane(beginLaneId);
        rightLane = beginLine.getRightLane(beginLaneId);

        if (leftLane != null) {
            leftLane.setRight(this);
        }

        if (rightLane != null) {
            rightLane.setLeft(this);
        }
    }

    /**
     * Récupère les points délimitant la LanePart.
     */
    public void fetchPoints() {
        double[] points1 = beginLine.getPoints(beginLaneId, 1);

        x0 = points1[0];
        y0 = points1[1];

        x1 = points1[2];
        y1 = points1[3];

        double[] points2 = endLine.getPoints(endLaneId, 1);

        x2 = points2[0];
        y2 = points2[1];

        x3 = points2[2];
        y3 = points2[3];
    }

    public double getLength() {
        return length;
    }

    public double[] getPoints() {
        return new double[]{x0, y0, x1, y1,
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

    public LanePart getLeft() {
        return leftLane;
    }

    public LanePart getRight() {
        return rightLane;
    }

    public abstract double[] getInfos(double progress);

    public void setNext(LanePart lp) {
        nextLane = lp;
    }

    public void setLeft(LanePart lp) {
        leftLane = lp;
    }

    public void setRight(LanePart lp) {
        rightLane = lp;
    }
}
