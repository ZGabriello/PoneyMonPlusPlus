package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.track.TrackModel;
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
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * Classe gérant l'affichage du Field.
 *
 */
public class FieldView implements Observer {
    int nbPoneys = -1;
    List<PoneyView> poneys = new ArrayList<>();
    
    FieldModel field;
    TrackModel track;
    Controller controller;

    String backgroundResource = "grass1.jpg";
    Color backgroundColor = null;
    Image backgroundImage = null;
    Canvas background;
    TrackView tview;
    Pane poneyground;
    Canvas foreground;
    
    String middleText;
    
    final int width;
    final int height;
    
    double scale;
    double xOffset;
    double yOffset;
    
    /**
     * Canvas dans lequel on va dessiner le jeu.
     *
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
    public FieldView(FieldModel fieldModel, Controller controller, int w, int h) {
        this.field = fieldModel;
        this.controller = controller;
        width = w;
        height = h;
        
        if (backgroundResource.startsWith("#")) {
            backgroundColor = Color.web(backgroundResource);
        } else if (backgroundColor == null) {
            backgroundImage = new Image("assets/" + backgroundResource, width , height, false, false);
        }
        background = new Canvas(w, h);
        poneyground = new Pane();
        foreground = new Canvas(w, h);
        
        track = fieldModel.getTrackModel();
        tview = new TrackView(track, width, height);
        
        scale = tview.getScale();
        xOffset = tview.getxOffset();
        yOffset = tview.getyOffset();
        
        field.addObserver(this);
    }
    
    /**
     * Initialisation du terrain de course et de ses PoneyView.
     * @param sn Notification d'initialisation
     */
    public void initialize(StartNotification sn) {
        poneyground.setTranslateX(xOffset * scale);
        poneyground.setTranslateY(height - yOffset * scale);
        
        nbPoneys = sn.getNbPoneys();
        List<String> poneyTypes = sn.getPoneyTypes();
        
        /* On initialise le terrain de course */
        for (int i = 0; i < nbPoneys; i++) {
            PoneyView newPoney = null;
            
            switch (poneyTypes.get(i)) {
                case "NyanPoneyModel":
                    newPoney = new NyanPoneyView(scale);
                    break;
                default:
                    newPoney = new PoneyView(scale);
            }
            poneys.add(newPoney);
            field.getPoneyModel(i).addObserver(newPoney);
            poneyground.getChildren().add(newPoney.getPoneyImage());
            poneyground.getChildren().add(newPoney.getPowerImage());
        }
    }
    
    /**
     * Mise à jour des positions de la vue sur notification du modèle.
     * @param pn notification de l'avancement des poneys du modèle
     */
    public void progress(ProgressNotification pn) {
        List<double[]> coords = pn.getCoords();
        double[] angles = pn.getAngles();
        
        for (int i = 0; i < nbPoneys; i++) {
            poneys.get(i).setPos(coords.get(i), angles[i]);
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
                display();
                break;
            case "WIN":
                displayWinner((WinNotification) n);
                displayMiddleText();
                break;
            default:
                System.err.println("Erreur : Notification de nom '" + n.name + "' inconnue !");
        }
    }
    
    /**
     * Renouvellement de l'affichage du terrain et des poneys.
     */
    public void display() {
        displayBackground();
        tview.display();
        
        displayPoneyground();
    }
    
    public void displayBackground() {
        GraphicsContext gc = background.getGraphicsContext2D();
        
        if (backgroundColor != null) {
            gc.setFill(backgroundColor);
            gc.fillRect(0, 0, width, height);
        } else if (backgroundImage != null) {
            gc.drawImage(backgroundImage, 0 , 0);
        } else {
            System.err.println("There's no background !");
        }
    }
    
    public void displayPoneyground() {
        for (PoneyView poney : poneys) {
            poney.display();
        }
    }
    
    /**
     * Affichage du texte central.
     */
    public void displayMiddleText() {
        GraphicsContext gc = foreground.getGraphicsContext2D();
        
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
    
    public Canvas getBackground() {
        return background;
    }
    
    public TrackView getTrackView() {
        return tview;
    }
    
    public Pane getPoneyground() {
        return poneyground;
    }
    
    public Canvas getForeground() {
        return foreground;
    }
}