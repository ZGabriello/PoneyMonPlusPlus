package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ArcLanePart;
import fr.univ_lyon1.info.m1.poneymon_fx.model.LanePart;
import fr.univ_lyon1.info.m1.poneymon_fx.model.Line;
import fr.univ_lyon1.info.m1.poneymon_fx.model.StraightLanePart;
import fr.univ_lyon1.info.m1.poneymon_fx.model.TrackModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.Notification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.TrackInitializationNotification;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;

import static java.lang.Math.min;
import static java.lang.Math.toDegrees;

/**
 * Classe gérant l'affichage d'un terrain.
 *
 */
public class TrackView {
    TrackModel track;
    Controller controller;
    
    final GraphicsContext gc;
    final int width;
    final int height;
    static final int PADDING = 80;
    
    final double lineWidth = 1.5;
    final Color borderColor = Color.BLACK;
    final Color insideColor = Color.LIGHTGREY;
    
    /**
     * Canvas dans lequel on va dessiner le jeu.
     * @param gc GraphicsContext du canvas parent dans lequel on dessine le circuit
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
    public TrackView(GraphicsContext gc, TrackModel track, int w, int h) {
        this.track = track;
        this.controller = controller;

        width = w;
        height = h;

        this.gc = gc;

        display();
    }
    
    private void drawLine(Line line, Color strokeColor) {
        gc.setStroke(strokeColor);
        
        double[] points = line.getPoints(0, line.getNbLanes());

        double xA = points[0];
        double yA = points[1];
        double xB = points[2];
        double yB = points[3];
        
        gc.strokeLine(xA, yA, xB, yB);
    }
    
    /*
     * Passe l'angle en degrés puis applique une symétrie par rapport à x
     * car JavaFX inverse le sens de l'ordonnée par rapport aux conventions mathématiques,
     * (360 - angle) puis applique une symétrie par rapport au centre car notre modèle
     * a ses angles qui pointent vers le centre du cercle, à l'inverse des conventions
     * mathématiques (180 + angle).
     *
     */
    private double formatAngle(double angle) {
        angle = toDegrees(angle);
        angle = (180 + 360 - angle) % 360;
        
        return angle;
    }
    
    private void drawArc(ArcLanePart lp, Color strokeColor, Color fillColor) {
        gc.beginPath();
        gc.setStroke(strokeColor);
        gc.setFill(fillColor);

        double[] points = lp.getPoints();
        
        double x0 = points[0];
        double y0 = points[1];
        double x1 = points[2];
        double y1 = points[3];
        double x2 = points[4];
        double y2 = points[5];
        double x3 = points[6];
        double y3 = points[7];
        
        double[] center = lp.getCenter();
        double centerX = center[0];
        double centerY = center[1];
        
        double outerRadius = lp.getOuterRadius();
        double innerRadius = lp.getInnerRadius();
        
        double startAngle = formatAngle(lp.getStartAngle());
        double endAngle = formatAngle(lp.getEndAngle());
        double arcLength = toDegrees(lp.getArcLength());
        
        gc.moveTo(x0, y0);
        gc.arc(centerX, centerY, outerRadius, outerRadius, startAngle, -arcLength);
        gc.lineTo(x3, y3);
        gc.arc(centerX, centerY, innerRadius, innerRadius, endAngle, arcLength);
        
        gc.closePath();
        
        gc.stroke();
        gc.fill();
    }
    
    private void drawStraight(StraightLanePart lp, Color strokeColor, Color fillColor) {
        gc.beginPath();
        gc.setStroke(strokeColor);
        gc.setFill(fillColor);
        gc.setFillRule(FillRule.EVEN_ODD);
        
        double[] points = lp.getPoints();
        
        double x0 = points[0];
        double y0 = points[1];
        
        double x1 = points[2];
        double y1 = points[3];
        
        double x2 = points[4];
        double y2 = points[5];
        
        double x3 = points[6];
        double y3 = points[7];
        
        gc.moveTo(x0, y0);
        gc.lineTo(x1, y1);
        gc.lineTo(x3, y3);
        gc.lineTo(x2, y2);
        gc.lineTo(x0, y0);
        gc.closePath();
        
        gc.stroke();
        gc.fill();
    }
    
    private void drawLanePart(LanePart lp, Color borderColor, Color insideColor) {
        if (lp instanceof StraightLanePart) {
            drawStraight((StraightLanePart) lp, borderColor, insideColor);
        } else if (lp instanceof ArcLanePart) {
            drawArc((ArcLanePart) lp, borderColor, insideColor);
        } else {
            System.err.println("A LanePart wasn't recognized...");
        }
    }
    
    private void display() {
        double fakeWidth = width - PADDING;
        double fakeHeight = height - PADDING;
        
        double widthScale = fakeWidth / track.getWidth();
        double heightScale = fakeHeight / track.getHeight();
        
        double scale = min(widthScale, heightScale);
        
        double minX = track.getMinX();
        double maxX = track.getMaxX();
        double minY = track.getMinY();
        double maxY = track.getMaxY();
        
        // Taille des intervalles des x et des y
        double xSize = maxX - minX;
        double ySize = maxY - minY;
        
        // On centre l'affichage par rapport à la fenêtre
        double xOffset = -minX + (width / scale - xSize) / 2;
        double yOffset = -minY + (height / scale - ySize) / 2;
        
        // height - yOffset * scale permet de replacer y en bas de la fenêtre
        gc.translate(xOffset * scale, height - yOffset * scale);
        
        // on applique un scale négatif à l'axe des y pour inverser le sens des
        // ordonnées et suivre les conventions mathématiques
        gc.scale(scale, -scale);
        gc.setLineWidth(lineWidth / scale);
        
        for (Line line : track.getLines().values()) {
            drawLine(line, borderColor);
        }
        
        for (LanePart lp : track.getLaneParts()) {
            drawLanePart(lp, borderColor, insideColor);
        }
    }
}