package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Vue principale du jeu.
 *
 */
public class JfxView implements View {
    FieldModel model;
    Controller controller;
    
    FieldView fview;
    
    Stage stage;
    Group root;
    Scene scene;
    
    final int width;
    final int height;
    
    /**
     * Constructeur de JfxView.    
     * @param s fenêtre
     * @param w largeur de la vue
     * @param h hauteur de la vue
     */
    public JfxView(Stage s, int w, int h) {
        width = w;
        height = h;
        
        stage = s;
        root = new Group();
        scene = new Scene(root);
        
        // Nom de la fenetre
        stage.setTitle("Poneymon");
    }
    
    /**
     * Menu du jeu.   
     */
    public void addButtons() {
        HBox hb = new HBox(); // Boite où ranger les éléments horizontalement

        Button hello = new Button("Say hello");
        hello.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                System.out.println("Hello");
            }
        });

        Button goodBye = new Button("Say good bye");
        goodBye.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                System.out.println("Good bye");
            }
        });

        hb.getChildren().add(hello);
        hb.getChildren().add(goodBye);
        root.getChildren().add(hb);
    }
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    /**
     * Creation de la vue liée au modèle.
     * @param model modèle du jeu
     */
    public void setModel(FieldModel model) {
        if (controller != null) {
            // On cree le terrain de jeu et on l'ajoute a la racine de la scene
            fview = new FieldView(model, controller, width, height);

            root.getChildren().add(fview);

            addButtons();

            // On ajoute la scene a la fenetre et on affiche
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("Error, controller must be set first !");
        }
    }
    
    @Override
    public void display() {
    }
}