package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.image.Image;


/**
 * Classe decrivant un sprite d'images.
 *
 */

public class Sprite {
    int frameTime;
    Image [] images;

    /**
     * Constructeur de Sprite.
     * @param duration durée de chaque image
     */

    public Sprite(int duration) {
        this.frameTime = duration;
        images = new Image[6];
        for (int i = 0; i < 6;i++) {
            images[i] = new Image("assets/Crunch_Sprite" + i + ".png");
        }


    }

    public Image[] getImages() {
        return images;
    }

    Image getImage(int index) {
        return images[index];
    }

    public int getFrameTime() {
        return frameTime;
    }
}
