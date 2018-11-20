package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MainView;
import java.util.List;
import javafx.animation.AnimationTimer;

/**
 * sous classe de controleur, gère la logique de jeu en ligne.
 *
 * @author Alex
 */
public class OnlineGameControl extends GameControl {

    public Lobby lobby;

    /**
     * constructeur.
     *
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
                model.step();
            }
        };
    }

    /**
     * constructeur par copie d'un GameController.
     *
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
                model.step();
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
        } else {
            lobby.client.sendCommand("PAUSE");
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
        setDefaultControlledPoney();
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
    @Override
    public void usePower(int i, String poneyType) {
        if (lobby.isHost) {
            PoneyModel pm = model.getPoneyModel(i);
            if (!pm.isIa()) {
                model.getPoneyModel(i).usePower();
                lobby.server.sendToAll("INPUT", "POW" + i);
            }

        } else {
            lobby.client.sendInput("POW" + i);
        }

    }

    /**
     * utilisatoin du pouvoir pour un client.
     *
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
        } else {
            lobby.client.sendCommand("UNPAUSE");
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
     * Déplace le poney sur la voie de gauche.
     *
     * @param i numero du poney a deplacer.
     */
    @Override
    public void goToLeftLane(int i) {
        System.out.println("left");
        if (lobby.isHost) {
            System.out.println("left");
            PoneyModel pm = model.getPoneyModel(i);

            pm.goToLeftLane();
            lobby.server.sendToAll("INPUT", "LFT" + i);
        } else {
            System.out.println("hi");
            lobby.client.sendInput("LFT" + i);
        }
    }

    /**
     * Déplace le poney sur la voie de gauche.
     *
     * @param i numero du poney a deplacer.
     */
    public void goToLeftLaneClient(int i) {
        PoneyModel pm = model.getPoneyModel(i);

        pm.goToLeftLane();
    }

    /**
     * Déplace le poney sur la voie de droite.
     *
     * @param i numero du poney a déplacer.
     */
    @Override
    public void goToRightLane(int i) {
        if (lobby.isHost) {
            PoneyModel pm = model.getPoneyModel(i);
            pm.goToRightLane();
            lobby.server.sendToAll("INPUT", "RGT" + i);
        } else {
            lobby.client.sendInput("RGT" + i);
        }

    }

    @Override
    public void setDefaultControlledPoney() {
        List<String> ips = lobby.getIps();
        List<Integer> ports = lobby.getPorts();
        for (int i = 0; i < ips.size(); i++) {
            if (ips.get(i).equals(lobby.usedIp) && ports.get(i) == lobby.portUsed) {
                controlledPoney = i;
            }
        }
        System.out.println("je suis le poney nim :" + controlledPoney);
    }

    /**
     * Déplace le poney sur la voie de droite.
     *
     * @param i numero du poney a déplacer.
     */
    public void goToRightLaneClient(int i) {
        PoneyModel pm = model.getPoneyModel(i);
        pm.goToRightLane();
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
