package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import java.util.ArrayList;
import fr.univ_lyon1.info.m1.poneymon_fx.view.View;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;

/**
 * Classe contrôleur.
 *
 */
public class Controller {
    FieldModel model;
    List<View> views = new ArrayList<>();
    
    public Controller() {
        model = null;
    }
    
    public void addView(View view) {
        views.add(view);
    }
    
    /**
     * Initialisation du modèle du jeu.
     * @param m Modèle du jeu
     */
    public void setModel(FieldModel m) {
        if (model == null) {
            model = m;
        } else {
            System.err.println("Controller is already linked to one model !");
        }
    }
    
    public void usePower(int i) {
        model.usePower(i);
    }
    
    /**
     * Boucle principale du jeu.
     *
     * handle() est appelee a chaque rafraichissement de frame
     * soit environ 60 fois par seconde.
     */
    public void startTimer() {

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                model.step();
            }
        }
            .start(); // On lance la boucle de rafraichissement
    }
}