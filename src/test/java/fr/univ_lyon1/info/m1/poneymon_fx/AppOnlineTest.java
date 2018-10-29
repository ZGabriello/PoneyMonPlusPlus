package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.OnlineController;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MainView;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 * Classe principale de l'application.
 *
 * s'appuie sur javafx pour le rendu
 */
public class AppOnlineTest extends Application {
    /**
    * En javafx start() lance l'application.
    *
    */
    @Override
    public void start(Stage stage) throws Exception {
        MainView v = new MainView(stage, 600, 600);
        Stage s2 = new Stage();
        MainView v2 = new MainView(s2, 1000, 600);
        OnlineController servC = new OnlineController();
        servC.setModel(new FieldModel(5));
        OnlineController clientC = new OnlineController();
        //OnlineController clientC1 = new OnlineController();
        servC.createLobby();
        servC.lobby.server.lobby = servC.lobby;
        clientC.joinLobby(servC.lobby.getHostIp(), 9000);
        clientC.addMainView(v);
        //clientC1.joinLobby(servC.lobby.getHostIp(), 9000);
        //clientC1.addMainView(v2);
        System.out.println("beforeStart");
        clientC.startGame(5);
        //clientC1.startGame(5);
        System.out.println("midStart");
        servC.startGame(5);
        System.out.println("afterStart");
        
        // Secondary view

        //c.addMainView(v2);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
