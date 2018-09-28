package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.Notification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PowerNotification;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Vue gérant l'affichage du Poney.
 *
 */
public class PoneyView implements View, Observer {
    static final double X_OFFSET = -100; // à fixer, doit être la taille de l'image
    static final double Y_OFFSET = 20;
    
    double x;       // position horizontale du poney
    final double y; // position verticale du poney
    final int width;
    
    // On cree trois images globales pour ne pas les recreer en permanence
    Image currentPoney;
    Image poneyImage;
    Image powerImage;

    GraphicsContext graphicsContext;
    
    PoneyView(GraphicsContext gc, String color, int yInit, int w) {
        // Tous les voisins commencent a gauche du canvas,
        // on commence a -100 pour les faire apparaitre progressivement
        x = X_OFFSET;
        y = yInit + Y_OFFSET;
        width = w;
        
        if (gc != null) {
            // gc == null can be provided for testing
            graphicsContext = gc;

            // On charge l'image associée au poney
            poneyImage = new Image("assets/pony-" + color + "-running.gif");

            currentPoney = poneyImage;
        }
    }
    
    public void setX(double progress) {
        x = progress * width + X_OFFSET;
    }
    
    public void displayPowerAnimation() {
        currentPoney = powerImage;
    }
    
    public void displayNormalAnimation() {
        currentPoney = poneyImage;
    }
    
    /**
     * Gère le changement d'affichage lorsqu'un pouvoir est utilisé.
     * @param pn notification de l'utilisation du pouvoir
     */
    public void powerUsed(PowerNotification pn) {
        boolean state = pn.state;
        if (pn.state == true) {
            displayPowerAnimation();
        } else {
            displayNormalAnimation();
        }
    }
    
    @Override
    public void update(Observable obs, Object o) {
        Notification n = (Notification) o;
        
        switch (n.name) {
            case "POWER":
                powerUsed((PowerNotification) n);
                break;
            default:
                System.err.println("Erreur : Notification de nom '" + n.name + "' inconnue !");
        }
    }
    
    @Override
    public void display() {
        graphicsContext.drawImage(currentPoney, x, y);
    }
}