package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.Line;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.Notification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PoneyStartNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PowerNotification;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

/**
 * Vue gérant l'affichage du Poney.
 *
 */
public class PoneyView implements Observer {  
    double xOffset;
    double yOffset;
    
    static final int IMAGE_WIDTH = 220;
    static final int IMAGE_HEIGHT = 120;
    static final int PONEY_WIDTH = 123;
    static final int PONEY_HEIGHT = 99;
    static final int X_MARGIN = 10;
    static final double laneWidth = Line.laneWidth;
    
    final double effectiveWidth;
    final double effectiveHeight;
            
    // position du poney dans le FieldModel
    int position;
    
    double x; // position horizontale du poney
    double y; // position verticale du poney
    double pivotX; // pivot pour effet miroir
    double pivotY;
    
    boolean mirrored;
    double angle; // angle d'orientation de l'image du poney
    Rotate rotation;
    Rotate mirror;
    
    String color;
    // On cree trois images globales pour ne pas les recreer en permanence
    ImageView currentPoney;
    ImageView poneyImage;
    ImageView powerImage;

    double scale;
    double poneyScale;
    
    int i;
    
    PoneyView(double scale) {
        // Tous les voisins commencent a gauche du canvas,
        // on commence a -100 pour les faire apparaitre progressivement
        this.scale = scale;
        poneyScale = (laneWidth * scale / IMAGE_HEIGHT);
        
        effectiveWidth = poneyScale * IMAGE_WIDTH;
        effectiveHeight = poneyScale * IMAGE_HEIGHT;
        
        rotation = new Rotate();
        mirror = new Rotate();
    }
    
    private double[] formatPos(double[] pos) {
        double newX = pos[0] * scale;
        double newY = -pos[1] * scale;
        
        return new double[] {newX, newY};
    }
    
    public void setPos(double[] pos, double angle) {
        if (angle > PI / 2 && angle < 3 * PI / 2) {
            if (!mirrored) {
                mirrored = true;
                mirror(true);
            }
        } else {
            if (mirrored) {
                mirrored = false;
                mirror(false);
            }
        }
        
        xOffset = -effectiveWidth * cos(angle) - effectiveHeight / 2 * sin(angle);
        yOffset = effectiveWidth * sin(angle) - effectiveHeight / 2 * cos(angle);

        double[] newPos = formatPos(pos);
        
        pivotX = newPos[0];
        pivotY = newPos[1];
        x = pivotX + xOffset;
        y = pivotY + yOffset;
        this.angle = angle;
    }
        
    protected void displayPowerAnimation() {
        currentPoney = powerImage;
        powerImage.setVisible(true);
        poneyImage.setVisible(false);
    }
    
    protected void displayNormalAnimation() {
        currentPoney = poneyImage;
        poneyImage.setVisible(true);
        powerImage.setVisible(false);
    }
    
    
    /**
     * Gère l'initialisation du PoneyView à partir des données du PoneyModel.
     * @param psn notification pour initialiser le poney
     */
    protected void initialize(PoneyStartNotification psn) {
        this.color = psn.getColor();
        this.position = psn.getPosition();
        
        // On charge l'image associée au poney
        poneyImage = loadImage("assets/pony-" + color + "-running.gif");
    }
    
    protected ImageView loadImage(String filename) {
        // On charge l'image associée au poney
        ImageView image = new ImageView(new Image(filename, effectiveWidth , effectiveHeight, false, false));
        image.setCache(true);
        image.getTransforms().addAll(mirror, rotation);
        
        return image;
    }
    
    /**
     * Gère le changement d'affichage lorsqu'un pouvoir est utilisé.
     * @param pn notification de l'utilisation du pouvoir
     */
    private void powerUsed(PowerNotification pn) {
        boolean state = pn.getState();
        
        if (state == true) {
            displayPowerAnimation();
        } else {
            displayNormalAnimation();
        }
    }
    
    /**
     * Appel des différents traitements suivant la notification reçue.
     */
    @Override
    public void update(Observable obs, Object o) {
        Notification n = (Notification) o;
        
        switch (n.name) {
            case "START":
                initialize((PoneyStartNotification) n);
                break;
            case "POWER":
                powerUsed((PowerNotification) n);
                break;
            default:
                System.err.println("Erreur : Notification de nom '" + n.name + "' inconnue !");
        }
    }
    
    public void display() {
        currentPoney.setX(x);
        currentPoney.setY(y);
        setMirrorPivot();
        setRotate(angle);
    }
    
    private void setMirrorPivot() {
        mirror.setPivotX(pivotX);
        mirror.setPivotY(pivotY);
        
        double otherPivotX = pivotX + 100000 * cos(angle);
        double otherPivotY = pivotY - 100000 * sin(angle);
        
        mirror.setAxis(new Point3D(otherPivotX, otherPivotY, 0));
    }
    
    private void mirror(boolean active) {
        if (active) {
            mirror.setAngle(180);
        } else {
            mirror.setAngle(0);
        }
    }
    
    private void setRotate(double angle) {
        rotation.setPivotX(x);
        rotation.setPivotY(y);
        rotation.setAngle(-toDegrees(angle));
    }
    
    public ImageView getPoneyImage() {
        return poneyImage;
    }
    
    public ImageView getPowerImage() {
        return powerImage;
    }
}