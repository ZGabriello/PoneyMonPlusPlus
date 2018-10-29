package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Vue de l'objet boost.
 * 
 * @author Elo
 */
public class BoostItemView extends ItemView {
 
    
    public BoostItemView(GraphicsContext gc, int x, int y) {
        super(gc, x, y);
    }
    
    @Override
    public void initialize() {
        super.initialize();
        
        if (graphicsContext != null) {
            itemImage = new Image("assets/star.gif");
        }
    }
}
