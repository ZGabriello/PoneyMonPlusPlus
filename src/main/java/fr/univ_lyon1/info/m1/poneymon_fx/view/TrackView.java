package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.BoostItemModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ItemModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.track.ArcLanePart;
import fr.univ_lyon1.info.m1.poneymon_fx.model.track.LanePart;
import fr.univ_lyon1.info.m1.poneymon_fx.model.track.Line;
import fr.univ_lyon1.info.m1.poneymon_fx.model.track.StraightLanePart;
import fr.univ_lyon1.info.m1.poneymon_fx.model.track.TrackModel;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.min;
import static java.lang.Math.toDegrees;
import java.util.Collection;
import java.util.NavigableMap;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Classe gérant l'affichage d'un terrain.
 *
 */
public class TrackView extends Canvas {
    TrackModel track;
    Controller controller;
    
    final GraphicsContext gc;
    final int width;
    final int height;
    static final int PADDING = 100;
    
    double scale;
    double xOffset;
    double yOffset;
    
    final double lineWidth = 2;
    final double lengthOfBeginLine = 0.30;
    final Color borderColor = Color.WHITE;
    final Color insideColor = Color.GREY;
    
    Pane itemground;
    
    /**
     * Canvas dans lequel on va dessiner le jeu.
     * @param track model du circuit à afficher
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
    public TrackView(TrackModel track, int w, int h) {
        super(w, h);
        
        this.track = track;
        this.controller = controller;
        
        itemground = new Pane();

        width = w;
        height = h;

        gc = this.getGraphicsContext2D();

        initialize();
    }
    
    private void initialize() {
                
        double fakeWidth = width - PADDING;
        double fakeHeight = height - PADDING;
        
        double widthScale = fakeWidth / track.getWidth();
        double heightScale = fakeHeight / track.getHeight();
        
        scale = min(widthScale, heightScale);
        
        double minX = track.getMinX();
        double maxX = track.getMaxX();
        double minY = track.getMinY();
        double maxY = track.getMaxY();
        
        // Taille des intervalles des x et des y
        double xSize = maxX - minX;
        double ySize = maxY - minY;
        
        // On centre l'affichage par rapport à la fenêtre
        xOffset = -minX + (width / scale - xSize) / 2;
        yOffset = -minY + (height / scale - ySize) / 2;
        
        // height - yOffset * scale permet de replacer y en bas de la fenêtre
        gc.translate(xOffset * scale, height - yOffset * scale);
        
        // on applique un scale négatif à l'axe des y pour inverser le sens des
        // ordonnées et suivre les conventions mathématiques
        gc.scale(scale, -scale);
        gc.setLineWidth(lineWidth / scale);        
        
        itemground.setTranslateX(xOffset * scale);
        itemground.setTranslateY(height - yOffset * scale); 
        Collection<LanePart> lanes = track.getLaneParts();
        
        for(LanePart lane : lanes) {
            for (ItemModel item : lane.getItems().values()) {
                if (item instanceof BoostItemModel) {
                    System.out.println("itemview");
                    itemground.getChildren().add(new BoostItemView(scale).getItemImage());
                }
            }
        }
        
    }
    
    private void drawBeginLine(Line line, Color strokeColor) {
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        
        gc.setLineWidth(1.5 * lineWidth / scale);
        
        double[] points = line.getPoints(0, line.getNbLanes());

        double x0 = points[0];
        double y0 = points[1] + 0.01;
        double x1 = points[2];
        double y1 = points[3] - 0.01;
        
        gc.strokeLine(x0, y0, x1, y1);
        
        double angle = line.getAngle() - PI / 2;
        double xStep = lengthOfBeginLine / 2 * cos(angle);
        double yStep = lengthOfBeginLine / 2 * sin(angle);
        
        double x2 = x0 + xStep;
        double y2 = y0 + yStep;
        double x3 = x1 + xStep;
        double y3 = y1 + yStep;
        
        gc.strokeLine(x2, y2, x3, y3);
        
        double x4 = x2 + xStep;
        double y4 = y2 + yStep;
        double x5 = x3 + xStep;
        double y5 = y3 + yStep;
        
        gc.strokeLine(x4, y4, x5, y5);
        
        double numberOfRect = 10;
        double height = y1 - y0;
        double rectHeight = height / numberOfRect;
        
        for (int i = 0; i < numberOfRect; i += 2) {
            gc.fillRect(x1, y1 - ((i + 1) * rectHeight), xStep, rectHeight + 0.01);
            gc.fillRect(x3, y3 - ((i + 2) * rectHeight) - 0.01, xStep, rectHeight + 0.01);
        }
        
        gc.setLineWidth(lineWidth / scale);
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
    
    private void drawItem(ItemModel item, double x, double y) {
                
        if (item instanceof BoostItemModel) {
            gc.drawImage(new Image("assets/star.gif"), x, y);
        }
        
    }
    
    /*
     * Passe l'angle en degrés puis applique une symétrie par rapport à x
     * car JavaFX inverse le sens de l'ordonnée par rapport aux conventions mathématiques.
     */
    private double formatAngle(double angle) {
        angle = -toDegrees(angle);
        
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
        
        NavigableMap<Double, ItemModel> items = lp.getItems();
        double progression;
        
        for (Double distance : items.keySet()) {
           
            progression = distance / lp.getLength();
            double[] positionItem = lp.getInfos(progression);
            
            ItemModel item = items.get(distance);
            
            drawItem(item, positionItem[0], positionItem[1]);   
        }
        
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
        
        NavigableMap<Double, ItemModel> items = lp.getItems();
        double progression;
        
        for (Double distance : items.keySet()) {
           
            progression = distance / lp.getLength();
            double[] positionItem = lp.getInfos(progression);
            
            ItemModel item = items.get(distance);
            
            drawItem(item, positionItem[0], positionItem[1]);           
            
            
        }
        
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
    
    
    /**
     * Fonction chargée de dessiner le circuit.
     */
    public void display() {
        for (LanePart lp : track.getLaneParts()) {
            drawLanePart(lp, borderColor, insideColor);
        }
        
        for (Line line : track.getLines().values()) {
            drawLine(line, borderColor);
        }
        
        
        drawBeginLine(track.getBeginLine(), insideColor);
    }
    
    public double getScale() {
        return scale;
    }
    
    public double getxOffset() {
        return xOffset;
    }
    
    public double getyOffset() {
        return yOffset;
    }
}