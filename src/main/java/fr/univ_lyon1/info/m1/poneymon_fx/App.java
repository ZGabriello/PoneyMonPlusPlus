package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MainView;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principale de l'application.
 *
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application {
    /**
    * En javafx start() lance l'application.
    *
    */
    @Override
    public void start(Stage stage) throws Exception {
        MainView v = new MainView(stage, 1680, 1050);
        Controller c = new Controller();

        c.addMainView(v);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
