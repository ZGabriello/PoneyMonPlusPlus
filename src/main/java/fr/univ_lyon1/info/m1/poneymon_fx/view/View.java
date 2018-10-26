package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.layout.StackPane;

public abstract class View extends StackPane {
    int width;
    int height;

     /**
     * Resize toutes les vues.
     */
    public void resize(int newWidth, int newHeight) {
        setPrefSize(newWidth, newHeight);
        width = newWidth;
        height = newHeight;
    }
}
