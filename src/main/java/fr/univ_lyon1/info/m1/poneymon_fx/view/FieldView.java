package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.Notification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ProgressNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.StartNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.WinNotification;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * Classe gérant l'affichage du Field.
 *
 */
public class FieldView extends Canvas implements View, Observer {
    int nbPoneys = -1;
    List<PoneyView> poneys = new ArrayList<>();
    
    FieldModel model;
    Controller controller;
    
    /** Couleurs possibles. */
    String[] colorMap =
        new String[] {"blue", "green", "orange", "purple", "yellow"};
    /** Inputs pour activer le pouvoir des poneys. */
    KeyCode[] inputs =
        new KeyCode[]{KeyCode.NUMPAD1, KeyCode.NUMPAD2, KeyCode.NUMPAD3,
                      KeyCode.NUMPAD4, KeyCode.NUMPAD5};

    String middleText;
    
    final GraphicsContext gc;
    final int width;
    final int height;
    
    /**
     * Canvas dans lequel on va dessiner le jeu.
     *
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
    public FieldView(FieldModel model, Controller controller, int w, int h) {
        super(w, h);
        
        this.model = model;
        this.controller = controller;

        width = w;
        height = h;

        /*
         * Permet de capturer le focus et donc les evenements clavier et
         * souris
         */
        this.setFocusTraversable(true);

        gc = this.getGraphicsContext2D();
        
        setOnKeyReleasedEvent();
        
        model.addObserver(this);
    }
    
    /**
     * Initialisation du terrain de course et de ses PoneyView.
     * @param sn Notification d'initialisation
     */
    public void initialize(StartNotification sn) {      
        nbPoneys = sn.nbPoneys;
        
        /* On initialise le terrain de course */
        for (int i = 0; i < nbPoneys; i++) {
            PoneyView newPoney = null;
            switch (sn.poneyTypes.get(i)) {
                case "NyanPoneyModel":
                    newPoney = new NyanPoneyView(gc, colorMap[i], i * 110, width);
                    break;
                default:
                    newPoney = new PoneyView(gc, colorMap[i], i * 110, width);
            }
            poneys.add(newPoney);
            model.getPoneyModel(i).addObserver(newPoney);
        }
    }
    
    /**
     * Mise à jour des positions de la vue sur notification du modèle.
     * @param pn notification de l'avancement des poneys du modèle
     */
    public void progress(ProgressNotification pn) {
        for (int i = 0; i < nbPoneys; i++) {
            poneys.get(i).setX(pn.progresses.get(i));
        }
    }

    public void displayWinner(WinNotification wn) {
        middleText = "The " + colorMap[wn.winner] + " poney has won the race !";
    }
    
    /**
     * Appel des différents observeurs suivant la notification reçue.
     */
    public void update(Observable obs, Object o) {
        Notification n = (Notification) o;
        
        switch (n.name) {
            case "START":
                if (nbPoneys == -1) {
                    initialize((StartNotification) n);
                }
                break;
            case "PROGRESS":
                progress((ProgressNotification) n);
                break;
            case "WIN":
                displayWinner((WinNotification) n);
                break;
            default:
                System.err.println("Erreur : Notification de nom '" + n.name + "' inconnue !");
        }
        
        display();
    }
    
    /**
     * Renouvellement de l'affichage du terrain et des poneys.
     */
    public void display() {
        // On nettoie le canvas a chaque frame
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, width, height);
        
        for (PoneyView poney : poneys) {
            poney.display();
        }
        
        displayMiddleText();
    }
    
    /**
     * Affichage du texte central.
     */
    public void displayMiddleText() {
        if (middleText != null) {
            gc.setFill(Color.VIOLET);
            gc.fillRect(0, (height - Math.round(height / 5)) / 2,
                    width, Math.round(height / 5));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            gc.strokeText(
                    middleText,
                    Math.round(width / 2),
                    Math.round(height / 2)
            );
        }
    }
    
    /**
     * Event Listener du clavier.
     * quand une touche est pressee on la rajoute a la liste d'input
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
     * quand une touche est relachee on l'enleve de la liste d'input
     *
     */
    public void setOnKeyReleasedEvent() {

        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                System.out.println(e.getCode());
                for (int i = 0; i < inputs.length; i++) {
                    if (inputs[i].equals(e.getCode())) {
                        controller.usePower(i);
                    }
                }
            }
        });
    }
}