package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.track.Line;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Vue d'un objet.
 * 
 * @author Elo
 */
public class ItemView {
    
    double xOffset;
    double yOffset;
    
    static final int IMAGE_WIDTH = 220;
    static final int IMAGE_HEIGHT = 120;
    
    double pivotX; // pivot pour effet miroir
    double pivotY;
    
    boolean mirrored;
    double angle;
    
    // position de l'objet dans le FieldModel
    int position = 1;
    
    double x; // position horizontale de l'objet
    double y; // position verticale de l'objet
 
    ImageView currentImage;
    ImageView itemImage;
    
    double scale;
    double itemScale;
    static final double laneWidth = Line.laneWidth;
    
    final double effectiveWidth;
    final double effectiveHeight;
    
   
    ItemView(double scale) {
        // Tous les voisins commencent a gauche du canvas,
        // on commence a -100 pour les faire apparaitre progressivement
        this.scale = scale;
        itemScale = (laneWidth * scale / IMAGE_HEIGHT);
        
        effectiveWidth = itemScale * IMAGE_WIDTH;
        effectiveHeight = itemScale * IMAGE_HEIGHT;
        
        initialize();
               
    }
    
    /**
     * Gère l'initialisation de l'objet.
     * 
     */
    public void initialize() {       
        
        
    }
    
    protected ImageView loadImage(String filename) {
        // On charge l'image associée au poney
        Image tmp = new Image(filename, effectiveWidth, effectiveHeight, false, false);
        ImageView image = new ImageView(tmp);
        image.setCache(true);
        
        return image;
    }
    
    /**
     * Affiche l'objet.
     */
    public void display() {
       
        
        System.out.println("x : " + x);
        System.out.println("y : " + y);
        
        currentImage.setX(x);
        currentImage.setY(y);
    }
    
     protected void displayNormalAnimation() {
        currentImage = itemImage;
        itemImage.setVisible(true);
    }
    
    public ImageView getItemImage() {
        return itemImage;
    }

    private double[] formatPos(double[] pos) {
        double newX = pos[0] * scale;
        double newY = -pos[1] * scale;
        
        return new double[] {newX, newY};
    }
    
    /**
     * Met à jour les informations d'affichage à partir des données de position
     * et de direction du poney (angle) données par le modèle.
     *
     */
    public void setPos(double[] pos, double angle) {
        angle = angle % (2 * PI);
        if (angle < 0) {
            angle = angle + 2 * PI;
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
    
}
