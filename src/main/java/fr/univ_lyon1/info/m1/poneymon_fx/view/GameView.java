package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Vue d'une partie.
 *
 */
public class GameView extends View {

    FieldModel model;
    MenuControlesView menuControles;
    Controller controller;

    FieldView fview;

    Group root;

    private final int widthGame;
    private final int heightGame;

    /**
     * Les boutons.
     */
    HBox buttons;
    Button menuButton;
    Button pauseButton;

    /**
     * Constructeur de GameView.
     *
     * @param m Modèle de la partie
     * @param mc Menu Controles contenant les touches de controle
     * @param c Contrôleur
     * @param w largeur de la vue
     * @param h hauteur de la vue
     */
    public GameView(FieldModel m, MenuControlesView mc, Controller c, int w, int h) {
        model = m;
        menuControles = mc;
        controller = c;
        widthGame = w;
        heightGame = h;

        fview = new FieldView(model, controller, widthGame, heightGame);
        this.getChildren().add(fview);

        addButtons();

        setOnKeyReleasedEvent();
    }

    /**
     * Boutons du jeu.
     */
    public void addButtons() {
        buttons = new HBox(); // Boite où ranger les éléments horizontalement

        menuButton = new Button("Menu principal");
        menuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                controller.menuFromGame();
            }
        });

        final String pause = "Pause";
        pauseButton = new Button(pause);
        pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                if (pauseButton.getText().equals(pause)) {
                    controller.gamePause();
                } else if (pauseButton.getText().equals("Continuer")) {
                    controller.gameUnpause();
                }
            }
        });

        buttons.getChildren().add(menuButton);
        buttons.getChildren().add(pauseButton);
        this.getChildren().add(buttons);
    }

    public void pause() {
        pauseButton.setText("Continuer");
    }

    public void unpause() {
        pauseButton.setText("Pause");
    }

    /**
     * Event Listener du clavier. quand une touche est relachee
     *
     */
    public void setOnKeyReleasedEvent() {
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                for (int i = 0; i < menuControles.hmControles.size(); i++) {
                    if (menuControles.hmControles.values().toArray()[i].equals(e.getCode())) {
                        controller.usePower(i);
                    }
                }
            }
        });
    }
}
