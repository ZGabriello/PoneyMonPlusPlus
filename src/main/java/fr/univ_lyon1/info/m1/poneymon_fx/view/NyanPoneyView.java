package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Vue gérant l'affichage d'un NyanPoney.
 *
 */
public class NyanPoneyView extends PoneyView {
    NyanPoneyView(GraphicsContext gc, String color, int yInit, int w) {
        super(gc, color, yInit, w);
        powerImage = new Image("assets/pony-" + color + "-rainbow.gif");
    }
}