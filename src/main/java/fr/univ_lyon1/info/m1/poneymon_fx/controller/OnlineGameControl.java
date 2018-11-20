package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MainView;
import javafx.animation.AnimationTimer;

/**
 * sous classe de controleur, gère la logique de jeu en ligne.
 * @author Alex
 */
public class OnlineGameControl extends GameControl {

    public Lobby lobby;

    /**
     * constructeur.
     * @param c Controlleur parent.
     */
    public OnlineGameControl(Controller c) {

        super(c);

        this.model = new FieldModel("test", 3);
        this.lobby = new Lobby();
        timer = new AnimationTimer() {
            
            /**
             * Boucle principale du jeu.
             *
             * handle() est appelee a chaque rafraichissement de frame soit
             * environ 60 fois par seconde.
             */
            @Override
            public void handle(long currentNanoTime) {

                if (lobby.isIsHost()) {
                    model.step();
                } else {
                    model.lookAtMe();
                }
            }
        };
    }
    
    /**
     * constructeur par copie d'un GameController.
     * @param c Controlleur parent.
     * @param gCon GameController a copier.
     */
    public OnlineGameControl(Controller c, GameControl gCon) {
        super(c, gCon);
        this.lobby = new Lobby();
        timer = new AnimationTimer() {
            
            /**
             * Boucle principale du jeu.
             *
             * handle() est appelee a chaque rafraichissement de frame soit
             * environ 60 fois par seconde.
             */
            @Override
            public void handle(long currentNanoTime) {

                if (lobby.isIsHost()) {
                    System.out.println("host");
                    model.step();
                } else {
                    System.out.println("client");
                    //model.lookAtMe();
                    model.step();
                }
            }
        };
    }

    /**
     * crée un lobby pour le controller, le lobby devient server par défaut.
     */
    void createLobby() {
        lobby.setController(this);
        lobby.setSelfServer();
        lobby.openServer();
    }

    /**
     * rejoins un lobby existant.
     *
     * @param ip addresse ipv4 du serveur distant.
     * @param port port du serveur distant.
     */
    void joinLobby(String ip, int port) {
        lobby = new Lobby();
        lobby.getRemoteLobby(ip, port);
        lobby.client.lobby = lobby;
        lobby.setController(this);
    }

    /**
     * Permet de mettre le jeu en pause.
     */
    @Override
    public void gamePause() {
        if (lobby.isHost) {
            timer.stop();

            for (MainView view : parent.views) {
                view.gamePause();
            }
            lobby.server.sendToAll("COMMAND", "PAUSE");
        }

    }

    
    /**
     * met le jeu en pause, utilisé par le client.
     */
    public void pauseClient() {
        timer.stop();
        for (MainView view : parent.views) {
            view.gamePause();
        }
    }

    /**
     * Démarre une nouvelle partie en créant un modèle et en le fournissant aux
     * vues suivies.
     *
     */
    @Override
    public void startGame() {
        lobby.launchGame();
        System.out.println("lets go");
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
        if (lobby.isHost) {
            PoneyModel pm = model.getPoneyModel(i);
            if (!pm.isIa()) {
                model.getPoneyModel(i).usePower();
            }
        } else {
            lobby.client.sendInput("POW" + i);
        }

    }

    /**
     * utilisatoin du pouvoir pour un client.
     * @param i poney utilisant le pouvoir.
     */
    void usePowerClient(int i) {
        PoneyModel pm = model.getPoneyModel(i);
        if (!pm.isIa()) {
            model.getPoneyModel(i).usePower();
        }
    }

    /**
     * Permet de relancer la partie après une pause.
     */
    @Override
    public void gameUnpause() {
        if (lobby.isHost) {
            timer.start();

            for (MainView view : parent.views) {
                view.gameUnpause();
            }
            lobby.server.sendToAll("COMMAND", "UNPAUSE");
        }
    }

    /**
     * reprise du jeu pour un client.
     */
    void gameUnpauseClient() {
        timer.start();

        for (MainView view : parent.views) {
            view.gameUnpause();
        }

    }

    /**
     * Permet de retourner au menu.
     */
    @Override
    public void menuFromGame() {
        System.out.println("right logic");
        if (lobby.isHost) {
            System.out.println("am closing");
            lobby.server.close();
            timer.stop();

            for (MainView view : parent.views) {
                view.setActiveView("MenuView");
                view.deleteView("GameView");
                view.setModel(null);
            }
        } else {
            System.out.println("not host");
            if (lobby.client != null) {
                lobby.client.close();
            }
            timer.stop();
            for (MainView view : parent.views) {
                view.setActiveView("MenuView");
                view.deleteView("GameView");
                view.setModel(null);
            }
        }
    }
}
