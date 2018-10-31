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
    GameControl game;
    FieldModel model;
    List<MainView> views = new ArrayList<>();
    boolean isOnline;
    AnimationTimer timer;
    Lobby lobby;

    /**
     * Constructeur du contrôleur.
     */
    public Controller() {
        game = new GameControl(this);
    }

    /**
     * Ajoute une vue qui sera suivie par le contrôleur.
     *
     * @param view vue à suivre dans le contrôleur
     */
    public void addMainView(MainView view) {
        view.setController(this);
        view.setId(views.size());

        initializeMainView(view);

        // Si il y a déjà des vues, la nouvelle doit afficher la même chose que les autres
        if (!views.isEmpty()) {
            // Si le modèle existe, une partie est en cours
            if (model != null) {
                view.setModel(model);
                view.createGameView();
            }
            String activeView = views.get(0).getActiveView();
            view.setActiveView(activeView);
        } else {
            view.setActiveView("MenuView");
        }

        views.add(view);
    }

    /**
     * Initialisation des vues.
     */
    public void initializeMainView(MainView view) {
        view.createMenuView();
        view.createMenuParametersView();
        view.createMenuControlesView();
        view.createMenuResolutionView();
        view.createOnlineServerView();
        view.createOnlineClientView();
    }



    /**
    * Démarre une nouvelle partie en créant un modèle et en le fournissant aux
    * vues suivies.
    *
    * @param filename nom du fichier du circuit à charger
    * @param nbPoneys nombre de poneys
    */
    public void startGame(String filename, int nbPoneys) {
        game.startGame(filename, nbPoneys);
        
    }
    
    /**
     * Utilise le pouvoir sur le poney si ce n'est pas une IA.
     *
     * @param i position du poney dans le modèle
     */
    public void usePower(int i) {
        game.usePower(i);
    }

    /**
     * Permet de relancer la partie après une pause.
     */
    public void gameUnpause() {
        game.gameUnpause();
    }

    /**
     * Permet de mettre le jeu en pause.
     */
    public void gamePause() {
        game.gamePause();
    }

    /**
     * Permet de retourner au menu.
     */
    public void menuFromGame() {
        game.menuFromGame();
        game = new GameControl(this);
    }

    /**
     * Permet d'aller dans le menu controles.
     */
    public void menuControles() {
        for (MainView view : views) {
            view.setActiveView("MenuControlesView");
        }
    }

    /**
     * Permet d'aller dans le menu paramètres.
     */
    public void menuParameters() {
        for (MainView view : views) {
            view.setActiveView("MenuParametersView");
        }
    }

    /**
     * Permet d'aller dans le menu paramètres.
     */
    public void menuResolution() {
        for (MainView view : views) {
            view.setActiveView("MenuResolutionView");
        }
    }

    public void changeResolution(int idMainView, int newWidth, int newHeight) {
        views.get(idMainView).resize(newWidth, newHeight);
    }
    
    public void createLobby(){
        game = new OnlineGameControl(this,game);
        for (MainView view : views) {
            view.setActiveView("OnlineServerView");
        }
    }
    
    public void joinLobby(){
        game = new OnlineGameControl(this,game);
        for (MainView view : views){
            view.setActiveView("OnlineClientView");
        }
    }

    public void LobbyViewFromClient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void LobbyViewFromClient(String text, String text0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
