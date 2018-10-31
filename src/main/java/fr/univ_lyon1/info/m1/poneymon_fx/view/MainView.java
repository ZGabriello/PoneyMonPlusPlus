package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.Lobby;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.OnlineGameControl;
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
    int id;

    HashMap<String, View> views = new HashMap<>();
    String activeView;

    FieldModel model;
    Controller controller;
    MenuControlesView menuControles;
    MenuResolutionView menuResolution;

    Stage stage;
    Group root;
    Scene scene;

    int width;
    int height;

    /**
     * Constructeur de la vue principale contenant toutes les vues.
     *
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
        stage.setResizable(true);

    }

    /**
     * Resize toutes les vues.
     */
    public void resize(int newWidth, int newHeight) {
        stage.setWidth(newWidth);
        stage.setHeight(newHeight);
        width = newWidth;
        height = newHeight;

        for (View view : views.values()) {
            view.resize(newWidth, newHeight);
        }
    }

    /**
     * Crée et ajoute au cache des vues le menu.
     */
    public void createMenuView() {
        View mv = new MenuView(controller, width, height);
        views.put("MenuView", mv);
    }

    /**
     * Crée et ajoute au cache des vues une partie.
     */
    public void createGameView() {
        View gv = new GameView(model, menuControles, controller, width, height);
        views.put("GameView", gv);
    }

    /**
     * Crée et ajoute au cache des vues les paramètres.
     */
    public void createMenuParametersView() {
        View mp = new MenuParametersView(controller, width, height);
        views.put("MenuParametersView", mp);
    }

    /**
     * Crée et ajoute au cache des vues la resolution.
     */
    public void createMenuResolutionView() {
        View mr = new MenuResolutionView(id, controller, width, height);
        views.put("MenuResolutionView", mr);
    }

    /**
     * Crée et ajoute au cache des vues pour les touches de controles.
     */
    public void createMenuControlesView() {
        menuControles = new MenuControlesView(controller, width, height);
        views.put("MenuControlesView", menuControles);
    }
    
    /**
     * crée la vue d'un lobby client.
     */
    public void createOnlineClientView(){
        View ocv = new OnlineClientView(controller, width, height);
        views.put("OnlineClientView",ocv);
    }
    
    
    /**
     * crée la vue d'un lobby.
     */
    public void createLobbyView(Lobby l){
        View lv = new LobbyView(controller, width, height,l);
        views.put("LobbyView",lv);
    }
    
    /**
     * crée la vue d'un lobby Serveur.
     */
    public void createOnlineServerView(){
        View osv = new OnlineServerView(controller, width, height);
        views.put("OnlineServerView", osv);
    }

    public void deleteView(String view) {
        views.remove(view);
    }

    /**
     * Permet de changer la vue actuellement affichée dans le stage.
     *
     * @param view vue à afficher
     */
    public void setActiveView(String view) {
        if (views.containsKey(view)) {
            if (activeView != null) {
                root.getChildren().remove(views.get(activeView));
            }
            activeView = view;

            Pane pane = views.get(view);
            root.getChildren().add(pane);
            pane.requestFocus();

            stage.show();
        } else {
            System.err.println("The view '" + view + "' doesn't exist in this MainView!");
        }
    }

    public String getActiveView() {
        return activeView;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Mutateur du modèle associé à la vue.
     *
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
     *
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
            GameView gv = (GameView) views.get("GameView");
            gv.pause();
        }
    }

    /**
     * Met à jour la vue de la partie pour montrer la reprise de la partie.
     */
    public void gameUnpause() {
        if (views.containsKey("GameView")) {
            GameView gv = (GameView) views.get("GameView");
            gv.unpause();
        }
    }


    public void setWidth(int newWidth) {
        width = newWidth;
    }

    public void setHeight(int newHeight) {
        width = newHeight;
    }

}
