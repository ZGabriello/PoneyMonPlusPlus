package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Vue d'un objet.
 * 
 * @author Elo
 */
public class ItemView {
    
    static final int IMAGE_WIDTH = 110;
    static final int IMAGE_HEIGHT = 90;
    
    // position de l'objet dans le FieldModel
    int position = 1;
    
    double x; // position horizontale de l'objet
    double y; // position verticale de l'objet
    
    String color;

    Image itemImage;

    GraphicsContext graphicsContext;
    
    
    public ItemView(GraphicsContext gc, int x, int y ) {
        this.x = x;
        this.y = y;
        graphicsContext = gc;
    }
    
    /**
     * Gère l'initialisation de l'objet.
     * 
     */
    public void initialize() {
        
        y = (IMAGE_HEIGHT ) * position;
        
        
    }
    
    public void display() {
        graphicsContext.drawImage(itemImage, x, y);
    }
    
    
}
