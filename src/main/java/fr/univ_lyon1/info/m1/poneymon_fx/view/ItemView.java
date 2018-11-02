package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.track.Line;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Vue d'un objet.
 * 
 * @author Elo
 */
public class ItemView {
    
    static final int IMAGE_WIDTH = 220;
    static final int IMAGE_HEIGHT = 120;
    
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
        currentImage = itemImage;
        itemImage.setVisible(true);
        
        currentImage.setX(x);
        currentImage.setY(y);
    }
    
    public ImageView getItemImage() {
        return itemImage;
    }
    
}
