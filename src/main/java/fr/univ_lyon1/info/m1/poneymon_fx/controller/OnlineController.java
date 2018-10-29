package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MainView;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;

/**
 *
 * @author Alex
 */
public class OnlineController extends Controller {

    public Lobby lobby;

    public OnlineController() {
        timer = new AnimationTimer() {
            /**
            * Boucle principale du jeu.
            *
            * handle() est appelee a chaque rafraichissement de frame
            * soit environ 60 fois par seconde.
            */
            @Override
            public void handle(long currentNanoTime) {
                
                if (OnlineController.this.lobby.isHost){
                    model.step();
                }
                else{
                    model.copy(model);
                }
            }
        };
    }
    
    
    public void createLobby() {
        lobby = new Lobby();

        lobby.setController(this);
        lobby.setSelfServer();
        lobby.openServer();
    }

    public void joinLobby(String ip, int port) {
        lobby = new Lobby();
        lobby.getRemoteLobby(ip, port);
        lobby.client.lobby = lobby;
        lobby.setController(this);
    }
    
    @Override
    public void startGame(int nbPoneys) {
        if (model == null){
            model = new FieldModel(nbPoneys);
        }
        for (MainView view : views) {
            view.setModel(model);
            view.createGameView();
        }
        
        gameUnpause(); // La partie démarre en pause
    }
    
    public void setModel(FieldModel f){
        model = f;
    }

}
