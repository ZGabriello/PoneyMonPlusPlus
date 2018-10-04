package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MainView;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;

/**
 * Classe contrôleur.
 *
 */
public class Controller {
    FieldModel model;
    List<MainView> views = new ArrayList<>();
    
    AnimationTimer timer;

    /**
     * Constructeur du contrôleur.
     */
    public Controller() {
        timer = new AnimationTimer() {
            /**
            * Boucle principale du jeu.
            *
            * handle() est appelee a chaque rafraichissement de frame
            * soit environ 60 fois par seconde.
            */
            public void handle(long currentNanoTime) {
                model.step();
            }
        };
    }
    
    /**
     * Ajoute une vue qui sera suivie par le contrôleur.
     * @param view vue à suivre dans le contrôleur
     */
    public void addMainView(MainView view) {
        view.setController(this);
        
        // Si il y a déjà des vues, la nouvelle doit afficher la même chose que les autres
        if (!views.isEmpty()) {
            // Si le modèle existe, une partie est en cours
            if (model != null) {
                view.setModel(model);
                view.createGameView();
            } else {
                view.createMenuView();
            }
        } else {
            view.createMenuView();
        }
        
        views.add(view);
    }
    
    /**
     * Démarre une nouvelle partie en créant un modèle et en le fournissant aux vues suivies.
     * @param nbPoneys nombre de poneys
     */
    public void startGame(int nbPoneys) {
        model = new FieldModel(nbPoneys);
        
        for (MainView view : views) {
            view.setModel(model);
            view.createGameView();
        }
        
        gameUnpause(); // La partie démarre en pause
    }
    
    /**
     * Utilise le pouvoir sur le poney si ce n'est pas une IA.
     * @param i position du poney dans le modèle
     */
    public void usePower(int i) {
        PoneyModel pm = model.getPoneyModel(i);
        if (!pm.isIa()) {
            model.getPoneyModel(i).applyState();
        }
    }
    
    /**
     * Permet de relancer la partie après une pause.
     */
    public void gameUnpause() {
        timer.start();
        
        for (MainView view : views) {
            view.gameUnpause();
        }
    }
    
    /**
     * Permet de mettre le jeu en pause.
     */
    public void gamePause() {
        timer.stop();
        
        for (MainView view : views) {
            view.gamePause();
        }
    }
    
    /**
     * Permet de retourner au menu.
     */
    public void menu() {
        for (MainView view : views) {
            view.setActiveView("MenuView");
            view.deleteView("GameView");
            view.setModel(null);
        }
    }
}