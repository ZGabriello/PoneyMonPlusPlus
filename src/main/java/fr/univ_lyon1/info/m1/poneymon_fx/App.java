package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MainView;
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
        MainView v = new MainView(stage, 600, 600);
        Controller c = new Controller();

        c.addMainView(v);

        // Secondary view
        Stage s2 = new Stage();
        MainView v2 = new MainView(s2, 1000, 600);

        c.addMainView(v2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
