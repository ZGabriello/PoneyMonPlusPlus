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

    GameControl currentGame;
    GameControl game;
    FieldModel model;
    List<MainView> views = new ArrayList<>();
    boolean isOnline;
    AnimationTimer timer;
    OnlineGameControl oGame;

    /**
     * Constructeur du contrôleur.
     */
    public Controller() {
        game = new GameControl(this);
        currentGame = game;
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
     */
    public void startGame() {
        currentGame.startGame();

    }

    /**
     * Utilise le pouvoir sur le poney si ce n'est pas une IA.
     *
     * @param i position du poney dans le modèle
     */
    public void usePower(int i, String poneyType) {
        currentGame.usePower(i, poneyType);
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
     * Permet de relancer la partie après une pause.
     */
    public void gameUnpause() {
        currentGame.gameUnpause();
    }

    /**
     * Permet de mettre le jeu en pause.
     */
    public void gamePause() {
        currentGame.gamePause();
    }

    /**
     * Permet de retourner au menu.
     */
    public void menuFromGame() {
        currentGame.menuFromGame();
        game = new GameControl(this);
        currentGame = game;
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

    /**
     * crée un lobby en ligne.
     */
    public void createLobby() {
        oGame = new OnlineGameControl(this, game);
        oGame.lobby = new Lobby();
        oGame.lobby.setController(oGame);
        currentGame = oGame;
        for (MainView view : views) {
            view.setActiveView("OnlineServerView");
        }
    }
    
    /**
     * rejoins un lobby.
     */
    public void joinLobby() {
        oGame = new OnlineGameControl(this, game);
        oGame.lobby = new Lobby();
        oGame.lobby.setController(oGame);
        currentGame = oGame;
        for (MainView view : views) {
            view.setActiveView("OnlineClientView");
        }
    }

    /**
     * lance la vue lobby pour le client, adapte le lobby en conéquence.
     * @param ip @ip du serveur distant.
     * @param port port du serveur distant.
     */
    public void lobbyViewFromClient(String ip, String port) {
        oGame.lobby.getRemoteLobby(ip, Integer.parseInt(port));

        for (MainView view : views) {
            view.createLobbyView(oGame.lobby);
            view.setActiveView("LobbyView");
        }
    }

    /**
     * lance la vue lobby pour un serveur, adapte le lobby en conséquance.
     * @param ip ip du serveur a créer.
     * @param port port du serveur a créer.
     */
    public void lobbyViewFromServer(String ip, String port) {
        oGame.lobby.setSelfServer(ip, Integer.parseInt(port));
        oGame.lobby.openServer();
        for (MainView view : views) {
            view.createLobbyView(oGame.lobby);
            view.setActiveView("LobbyView");
        }
    }
}
