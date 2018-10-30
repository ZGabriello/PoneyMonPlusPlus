package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.Notification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.ProgressNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.StartNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.WinNotification;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * Classe gérant l'affichage du Field.
 *
 */
public class FieldView extends Canvas implements Observer {
    int nbPoneys = -1;
    List<PoneyView> poneys = new ArrayList<>();

    FieldModel model;
    Controller controller;

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

        model.addObserver(this);
    }

    /**
     * Initialisation du terrain de course et de ses PoneyView.
     * @param sn Notification d'initialisation
     */
    public void initialize(StartNotification sn) {
        nbPoneys = sn.getNbPoneys();
        List<String> poneyTypes = sn.getPoneyTypes();

        /* On initialise le terrain de course */
        for (int i = 0; i < nbPoneys; i++) {
            PoneyView newPoney = null;

            switch (poneyTypes.get(i)) {
                case "NyanPoneyModel":
                    newPoney = new NyanPoneyView(gc, width);
                    break;
                default:
                    newPoney = new PoneyView(gc, width);
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
        List<Double> progresses = pn.getProgresses();

        for (int i = 0; i < nbPoneys; i++) {
            poneys.get(i).setX(progresses.get(i));
        }
    }

    public void displayWinner(WinNotification wn) {
        middleText = "The " + wn.getWinnerColor() + " poney has won the race !";
    }

    /**
     * Appel des différents traitements suivant la notification reçue.
     */
    @Override
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
            gc.fillRect(0, (height - Math.round(height / 5.0)) / 2,
                    width, Math.round(height / 5.0));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            gc.strokeText(
                    middleText,
                    Math.round(width / 2.0),
                    Math.round(height / 2.0)
            );
        }
    }
}