package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MainView;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;

/**
 * controller spécifique au jeu en ligne.
 *
 * @author Alex
 */
public class OnlineController extends Controller {

    public Lobby lobby;

    /**
     * constructeur par défaut.
     */
    public OnlineController() {
        timer = new AnimationTimer() {
            /**
             * Boucle principale du jeu.
             *
             * handle() est appelee a chaque rafraichissement de frame soit
             * environ 60 fois par seconde.
             */
            @Override
            public void handle(long currentNanoTime) {

                if (OnlineController.this.lobby.isHost) {
                    model.step();
                } else {
                    model.lookAtMe();
                }
            }
        };
    }

    /**
     * crée un lobby pour le controller, le lobby devient server par défaut.
     */
    public void createLobby() {
        lobby = new Lobby();

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
    public void joinLobby(String ip, int port) {
        lobby = new Lobby();
        lobby.getRemoteLobby(ip, port);
        lobby.client.lobby = lobby;
        lobby.setController(this);
    }

    public void startGame(int nbPoneys) {
        if (model == null) {
            model = new FieldModel("test",nbPoneys);
        }
        for (MainView view : views) {
            view.setModel(model);
            view.createGameView();
        }

        gameUnpause(); // La partie démarre en pause
    }

    public void setModel(FieldModel f) {
        model = f;
    }

    @Override
    public void usePower(int i) {
        System.out.println("using power");
        if (this.lobby.isHost) {
            applyPower(i);
            this.lobby.server.sendToAll("INPUT", "POW" + i);
        } else {
            this.lobby.client.sendInput("POW" + i);
        }
    }

    /**
     * applique le pouvoir du poney i.
     *
     * @param i poney dont il faut utiliser le pouvoir.
     */
    public void applyPower(int i) {
        PoneyModel pm = model.getPoneyModel(i);
        if (!pm.isIa()) {
            model.getPoneyModel(i).usePower();
        }
    }

    @Override
    public void gamePause() {
        if (this.lobby.isHost) {
            timer.stop();

            for (MainView view : views) {
                view.gamePause();
            }
            //this.lobby.server.sendToAll("INPUT", "PAU");
        }
    }

    /**
     * version utilisée par le client de la pause.
     */
    public void gamePauseClient() {
        timer.stop();
        for (MainView view : views) {
            view.gamePause();
        }
    }

    @Override
    public void gameUnpause() {
        if (this.lobby.isHost) {
            timer.start();
            for (MainView view : views) {
                view.gameUnpause();
            }
            this.lobby.server.sendToAll("INPUT", "CON");
        }
    }

    /**
     * version de unpause utilisée par le client.
     */
    public void gameUnpauseClient() {
        timer.start();
        for (MainView view : views) {
            view.gameUnpause();
        }
    }
}
