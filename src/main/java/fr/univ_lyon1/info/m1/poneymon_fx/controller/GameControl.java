/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MainView;
import javafx.animation.AnimationTimer;

/**
 *
 * @author Alex
 */
public class GameControl {
    FieldModel model;
    AnimationTimer timer;
    Controller parent;
    public GameControl(Controller c){
        parent = c;
        model =new FieldModel("test",3);
        timer = new AnimationTimer() {
            /**
             * Boucle principale du jeu.
             *
             * handle() est appelee a chaque rafraichissement de frame soit
             * environ 60 fois par seconde.
             */
            @Override
            public void handle(long currentNanoTime) {
                model.step();
            }
        };
    }
    
    public GameControl(Controller c, GameControl gCon){
        this.parent = c;
        this.model = gCon.model;
        this.timer = gCon.timer;
    }
    
    /**
     * Permet de mettre le jeu en pause.
     */
    public void gamePause() {
        timer.stop();

        for (MainView view : parent.views) {
            view.gamePause();
        }
    }
    
    
    /**
    * Démarre une nouvelle partie en créant un modèle et en le fournissant aux
    * vues suivies.
    *
    * @param filename nom du fichier du circuit à charger
    * @param nbPoneys nombre de poneys
    */
    public void startGame(String filename, int nbPoneys) {
        model = new FieldModel(filename, nbPoneys);

        for (MainView view : parent.views) {
            view.setModel(model);
            view.createGameView();
            view.setActiveView("GameView");
            gameUnpause();
        }

        gamePause();
    }
    
    /**
     * Utilise le pouvoir sur le poney si ce n'est pas une IA.
     *
     * @param i position du poney dans le modèle
     */
    public void usePower(int i) {
        PoneyModel pm = model.getPoneyModel(i);
        if (!pm.isIa()) {
            model.getPoneyModel(i).usePower();
        }

    }

    /**
     * Permet de relancer la partie après une pause.
     */
    public void gameUnpause() {
        timer.start();

        for (MainView view : parent.views) {
            view.gameUnpause();
        }
    }
    
    /**
     * Permet de retourner au menu.
     */
    public void menuFromGame() {
        timer.stop();

        for (MainView view : parent.views) {
            view.setActiveView("MenuView");
            view.deleteView("GameView");
            view.setModel(null);
        }
    }
}
