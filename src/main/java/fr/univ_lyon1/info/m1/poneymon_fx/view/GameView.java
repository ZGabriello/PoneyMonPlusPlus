package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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

    final int widthGame;
    final int heightGame;

    /**
     * Les boutons.
     */
    HBox buttons;
    Button menuButton;
    Button pauseButton;

    int timeBeforeStartOfGame = 3;
    int timeBeforeGoVanishes = 2;

    /** Inputs pour activer le pouvoir des poneys. */
    KeyCode[] powerInputs =
        new KeyCode[]{KeyCode.NUMPAD1, KeyCode.NUMPAD2, KeyCode.NUMPAD3,
                      KeyCode.NUMPAD4, KeyCode.NUMPAD5};

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

        this.getChildren().addAll(fview.getBackground(), fview.getTrackView(),
                                  fview.getPoneyground(), fview.getForeground());

        addButtons();

        setOnKeyReleasedEvent();

        startTimer();
    }

    private void startTimer() {
        Label timerLabel = new Label();
        this.getChildren().add(timerLabel);
        // Configure the Label
        timerLabel.setText(Integer.toString(timeBeforeStartOfGame));
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em;");

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                  new EventHandler<ActionEvent>() {
                    // KeyFrame event handler
                    public void handle(ActionEvent event) {
                        --timeBeforeStartOfGame;
                        // update timerLabel
                        if (timeBeforeStartOfGame > 0) {
                            timerLabel.setText(Integer.toString(timeBeforeStartOfGame));
                        } else if (timeBeforeStartOfGame == 0) {
                            timerLabel.setText("GOOOO !!!");
                            controller.gameUnpause();
                        } else {
                            --timeBeforeGoVanishes;
                        }
                        if (timeBeforeGoVanishes == 0) {
                            timeline.stop();
                            GameView.this.getChildren().remove(timerLabel);
                        }
                    }
                }));
        timeline.playFromStart();
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

        final String Continuer = "Continuer";
        pauseButton = new Button(Continuer);
        pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                if (pauseButton.getText().equals("Pause")) {
                    controller.gamePause();
                } else if (pauseButton.getText().equals(Continuer)) {
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
     * Event Listener du clavier. quand une touche est pressee
     *
     */
    public void setOnKeyPressedEvent() {

        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                ; // TODO
            }
        });
    }

    /**
     * Event Listener du clavier. quand une touche est relachee
     *
     */
    public void setOnKeyReleasedEvent() {
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                Map<String, KeyCode> controls = menuControles.hmControles;
                for (String control : controls.keySet()) {
                    if (controls.get(control).equals(e.getCode())) {
                        switch (control) {
                            case "Pouvoir NyanPoney":
                                controller.usePower(1, "NyanPoneyModel");
                                break;
                            case "Pouvoir EnragedPoney à gauche":
                                controller.usePower(1, "EnragedPoneyModel");
                                break;
                            case "Pouvoir EnragedPoney à droite":
                                controller.usePower(1, "EnragedPoneyModel");
                                break;
                            case "Aller sur la voie de gauche":
                                controller.goToLeftLane(1);
                                break;
                            case "Aller sur la voie de droite":
                                controller.goToRightLane(1);
                                break;
                            default:
                        }
                    }
                }
            }
        });
    }
}
