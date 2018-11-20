package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PoneyStartNotification;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

/**
 * Vue gérant l'affichage d'un NyanPoney.
 *
 */
public class NyanPoneyView extends PoneyView {

    NyanPoneyView(double scale) {
        super(scale);
    }

    /**
     * Gère l'initialisation du NyanPoneyView à partir des données du
     * PoneyModel.
     *
     * @param psn notification pour initialiser le poney
     */
    @Override
    public void initialize(PoneyStartNotification psn) {
        super.initialize(psn);

        powerImage = loadImage("assets/pony-" + color + "-rainbow.gif");

        displayNormalAnimation();
    }
}
