package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Vue d'une partie.
 *
 */
public class GameView extends StackPane {
    FieldModel model;
    Controller controller;
    
    FieldView fview;
    
    Group root;
    
    final int width;
    final int height;
    
    /** Les boutons. */
    HBox buttons;
    Button menuButton;
    Button pauseButton;
    
    Map<KeyCode, Button> hm = new LinkedHashMap<>();
    
    /** Inputs pour activer le pouvoir des poneys. */
    KeyCode[] powerInputs =
        new KeyCode[]{KeyCode.NUMPAD1, KeyCode.NUMPAD2, KeyCode.NUMPAD3,
                      KeyCode.NUMPAD4, KeyCode.NUMPAD5};
    
    /**
     * Constructeur de JfxView.    
     * @param m Modèle de la partie
     * @param c Contrôleur
     * @param w largeur de la vue
     * @param h hauteur de la vue
     */
    public GameView(FieldModel m, Controller c, int w, int h) {
        model = m;
        controller = c;
        width = w;
        height = h;

        fview = new FieldView(model, controller, width, height);
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

        pauseButton = new Button("Pause");
        pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                if (pauseButton.getText().equals("Pause")) {
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
     * Event Listener du clavier.
     * quand une touche est pressee
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
     * Event Listener du clavier.
     * quand une touche est relachee
     *
     */
    public void setOnKeyReleasedEvent() {
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                System.out.println(e.getCode());
                for (int i = 0; i < powerInputs.length; i++) {
                    hm.put(powerInputs[i],new Button("e.getCode()"));
                    Set<Entry<KeyCode, Button>> setHm = hm.entrySet();
                    Iterator<Entry<KeyCode, Button>> it = setHm.iterator();
                    while(it.hasNext()){
                        Entry<KeyCode, Button> entry = it.next();
                        if (entry.getKey().equals(e.getCode())) {
                            controller.usePower(i);
                        }
                    }
                }
            }
        });
    }
}