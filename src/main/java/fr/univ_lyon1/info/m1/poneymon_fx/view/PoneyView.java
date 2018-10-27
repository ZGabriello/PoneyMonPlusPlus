package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.Notification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PoneyStartNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PowerNotification;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Vue gérant l'affichage du Poney.
 *
 */
public class PoneyView implements Observer {
    static final double X_OFFSET = -93;
    static final double Y_OFFSET = 20;
    // espace entre chaque poney
    static final double Y_PADDING = 10;
    static final int IMAGE_WIDTH = 123;
    static final int IMAGE_HEIGHT = 99;
    
    // position du poney dans le FieldModel
    int position;
    
    double x; // position horizontale du poney
    double y; // position verticale du poney
    
    String color;
    // On cree trois images globales pour ne pas les recreer en permanence
    Image currentPoney;
    Image poneyImage;
    Image powerImage;

    GraphicsContext gc;
    
    // Taille à parcourir par le poney en pixels, taille du terrain + taille du poney
    int width;
    
    PoneyView(GraphicsContext gc, int w) {
        // Tous les voisins commencent a gauche du canvas,
        // on commence a -100 pour les faire apparaitre progressivement
        setX(0.0);
        width = w + IMAGE_WIDTH;
        this.gc = gc;
    }
    
    public void setX(double progress) {
        x = (progress * width) - IMAGE_WIDTH + X_OFFSET;
    }
    
    public void displayPowerAnimation() {
        currentPoney = powerImage;
    }
    
    public void displayNormalAnimation() {
        currentPoney = poneyImage;
    }
    
    
    /**
     * Gère l'initialisation du PoneyView à partir des données du PoneyModel.
     * @param psn notification pour initialiser le poney
     */
    public void initialize(PoneyStartNotification psn) {
        this.color = psn.getColor();
        this.position = psn.getPosition();
        
        y = (IMAGE_HEIGHT + Y_PADDING) * position + Y_OFFSET;
        
        if (gc != null) {
            // On charge l'image associée au poney
            poneyImage = new Image("assets/pony-" + color + "-running.gif");

            currentPoney = poneyImage;
        }
    }
    
    /**
     * Gère le changement d'affichage lorsqu'un pouvoir est utilisé.
     * @param pn notification de l'utilisation du pouvoir
     */
    public void powerUsed(PowerNotification pn) {
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
        
        display();
    }
    
    public void display() {
        gc.drawImage(currentPoney, -10, 10);
    }
}