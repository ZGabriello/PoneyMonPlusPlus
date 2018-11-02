package fr.univ_lyon1.info.m1.poneymon_fx.view;

/**
 * Vue de l'objet boost.
 * 
 * @author Elo
 */
public class BoostItemView extends ItemView {
 
    BoostItemView(double scale) {
        super(scale);
    }
    
    @Override
    public void initialize() {
        super.initialize();
        
        itemImage = loadImage("assets/star.gif");
        
        display();
    }
    
    
}
