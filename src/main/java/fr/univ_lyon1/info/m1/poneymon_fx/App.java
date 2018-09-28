package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.JfxView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
        FieldModel m = new FieldModel(5); // 5 poneys
        JfxView v = new JfxView(stage, 600, 600); // 600x600 pixels
        Controller c = new Controller();

        c.addView(v);
        c.setModel(m);
        v.setController(c);
        v.setModel(m);

        c.startTimer();
        
        // Secondary view
        Stage s2 = new Stage();
        JfxView v2 = new JfxView(s2, 1000, 600);
        c.addView(v2);
        v2.setController(c);
        v2.setModel(m);
    }

    public static void main(String[] args) {
        // System.out.println( "Hello World!" );
        Application.launch(args);
    }
}
