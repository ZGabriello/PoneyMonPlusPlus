package fr.univ_lyon1.info.m1.poneymon_fx.model.notification;

/**
 * Notification envoyée aux Observers poneys pour qu'ils s'initialisent.
 *
 */
public class PoneyStartNotification extends Notification {
    String color;
    int position;
    
    /**
     * Constructeur de la notification d'initialisation du poney.
     * @param color Couleur du poney
     */
    public PoneyStartNotification(String color, int position) {
        super("START");
        this.color = color;
        this.position = position;
    }
    
    public String getColor() {
        return color;
    }
    
    public int getPosition() {
        return position;
    }
}
