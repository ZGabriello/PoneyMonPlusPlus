package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import java.util.HashMap;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Vue principale de l'application, contient toutes les autres vues.
 *
 */
public class MainView {
    HashMap<String, Pane> views = new HashMap<>();
    String currentView;
    
    FieldModel model;
    Controller controller;
    
    Stage stage;
    Group root;
    Scene scene;
    
    final int width;
    final int height;
    
    
    /**
     * Constructeur de la vue principale contenant toutes les vues.
     * @param s stage dans lequel afficher les vues
     * @param w largeur de la vue
     * @param h hauteur de la vue
     */
    public MainView(Stage s, int w, int h) {
        width = w;
        height = h;
        
        root = new Group();
        scene = new Scene(root);
        
        // Nom de la fenetre
        stage = s;
        stage.setTitle("Poneymon");
        
        // On ajoute la scene a la fenetre
        stage.setScene(scene);
        
        // Pour empêcher de changer la taille de la fenêtre
        stage.setResizable(false);
    }
    
    /**
     * Crée et ajoute au cache des vues le menu.
     */
    public void createMenuView() {
        MenuView mv = new MenuView(controller, width, height);
        views.put("MenuView", mv);    
        
        setActiveView("MenuView");
    }
    
    /**
     * Crée et ajoute au cache des vues une partie.
     */
    public void createGameView() {
        GameView gv = new GameView(model, controller, width, height);
        views.put("GameView", gv);
        
        setActiveView("GameView");
    }
    
    public void deleteView(String view) {
        views.remove(view);
    }
    
    /**
     * Permet de changer la vue actuellement affichée dans le stage.
     * @param view vue à afficher
     */
    public void setActiveView(String view) {
        if (views.containsKey(view)) {
            if (currentView != null) {
                root.getChildren().remove(views.get(currentView));
            }
            currentView = view;
            
            Pane pane = views.get(view);
            root.getChildren().add(pane);
            pane.requestFocus();
            
            stage.show();
        } else {
            System.err.println("The view '" + view + "' doesn't exist in this MainView!");
        }
    }
    
    /**
     * Mutateur du modèle associé à la vue.
     * @param m modèle
     */
    public void setModel(FieldModel m) {
        if (m != null) {
            if (model == null) {
                model = m;
            } else {
                System.err.println("This MainView is already linked to a model !");
            }
        } else {
            model = null;
        }
    }
    
    /**
     * Mutateur du contrôleur associé à la vue.
     * @param c contrôleur
     */
    public void setController(Controller c) {
        if (c != null) {
            if (controller == null) {
                controller = c;
            } else {
                System.err.println("This MainView is already linked to a controller !");
            }
        } else {
            controller = null;
        }
    }
    
    /**
     * Met à jour la vue de la partie pour montrer la pause.
     */
    public void gamePause() {
        if (views.containsKey("GameView")) {
            GameView gv = (GameView)views.get("GameView");
            gv.pause();
        }
    }
    
    /**
     * Met à jour la vue de la partie pour montrer la reprise de la partie.
     */
    public void gameUnpause() {
        if (views.containsKey("GameView")) {
            GameView gv = (GameView)views.get("GameView");
            gv.unpause();
        }
    }
}