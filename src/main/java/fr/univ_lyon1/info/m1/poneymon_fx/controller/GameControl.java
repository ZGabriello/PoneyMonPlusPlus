package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MainView;
import java.util.List;
import javafx.animation.AnimationTimer;

/**
 * sous-classe du controller qui gère la logique du jeu.
 * @author Alex
 */
public class GameControl {

    FieldModel model;
    AnimationTimer timer;
    Controller parent;
    int controlledPoney = 0;
    /**
     * constructeur.
     * @param c Controlleur parent.
     */
    public GameControl(Controller c) {
        parent = c;
        model = new FieldModel("test", 3);
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

    /**
     * constructeur par copied'un autre GameControl.
     * @param c Controlleur parent.
     * @param gCon GameControler a copier.
     */
    public GameControl(Controller c, GameControl gCon) {
        this.parent = c;
        this.model = gCon.model;
        this.timer = gCon.timer;
    }
    
    /**
     * Déplace le poney sur la voie de gauche.
     */
    public void goToLeftLane(int i) {
        PoneyModel pm = model.getPoneyModel(i);

        pm.goToLeftLane();
    }
    
    /**
     * Déplace le poney sur la voie de droite.
     */
    public void goToRightLane(int i) {
        PoneyModel pm = model.getPoneyModel(i);

        pm.goToRightLane();
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
     */
    public void startGame() {
        setDefaultControlledPoney();
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
    public void usePower(int i, String poneyType) {
        PoneyModel pm = model.getPoneyModel(i);

        if (pm.getClass().getSimpleName().equals(poneyType)) {
            if (!pm.isIa()) {
                model.getPoneyModel(i).usePower();
            }
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
    
    
    public void setDefaultControlledPoney(){
        controlledPoney = 1;
        System.out.println("default poney");
    }
    
    void setControlledPoney(int i){
        if (i<model.getNbPoneys()-1){
            controlledPoney = i;
        }
    }
}
