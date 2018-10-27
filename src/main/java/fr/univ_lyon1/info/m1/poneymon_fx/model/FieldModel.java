package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.ImStillHereNyanStrategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.NotEnoughSpeedNyanStrategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.MoreSpeedNyanStrategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.ProgressNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.StartNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.WinNotification;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe gérant la logique du Field.
 *
 */
public class FieldModel extends Observable {
    /** Circuit chargé. */
    TrackModel track;
    
    /** Tableau des joueurs réels. */
    int[] players = new int[] { 0, 1 };
    
    /** Poneys. */
    int nbPoneys;
    List<PoneyModel> poneys = new ArrayList<>();
    List<Double> progresses = new ArrayList<>();
    String[] colorMap =
    new String[] {"blue", "green", "orange", "purple", "yellow"};
    
    /** Nombre de tours pour gagner. */
    final int winAt = 3;
    int winner = -1;
    
    /**
     * Constructeur du FieldModel.
     * @param filename nom du fichier du circuit à charger
     * @param nbPoneys Nombre de PoneyModel à instancier
     */
    public FieldModel(String filename, int nbPoneys) {
        track = new TrackModel(filename);
        
        this.nbPoneys = nbPoneys;
        /* On initialise le terrain de course */
        for (int i = 0; i < nbPoneys; i++) {
            poneys.add(new NyanPoneyModel(colorMap[i % 5], i, this));
            progresses.add(0.0);
        }
        
        // Tant qu'il n'y a pas de menus permettant de choisir les poneys
        // on part d'une partie avec 3 ia sur les poneys centraux, et 5 poneys au total
        if (nbPoneys == 5) {
            NyanPoneyModel p = (NyanPoneyModel)poneys.get(1);
            p.setStrategy(new MoreSpeedNyanStrategy(this, p, 1));
            
            p = (NyanPoneyModel)poneys.get(2);
            p.setStrategy(new NotEnoughSpeedNyanStrategy(this, p, 2));
            
            p = (NyanPoneyModel)poneys.get(3);
            p.setStrategy(new ImStillHereNyanStrategy(this, p, 3));
        }
    }

    /**
     *Avancée des différents poneys. 
     */
    public void step() {
        for (int i = 0; i < nbPoneys; i++) {
            // la fonction step() dans PoneyModel renvoie le nouveau progrès après mise à jour
            progresses.set(i, poneys.get(i).step());
            
            if (poneys.get(i).getNbTours() == winAt && winner == -1) {
                winner = i;
                setChanged();
                notifyObservers(new WinNotification(winner, poneys.get(i).getColor()));
            }
        }
        
        setChanged();
        notifyObservers(new ProgressNotification(progresses));
    }
    
    /**
     * Initialisation des observeurs du modèle du terrain.
     */
    @Override
    public void addObserver(Observer obs) {
        super.addObserver(obs);
        
        List<String> poneyTypes = new ArrayList<>();
        for (int i = 0; i < nbPoneys; i++) {
            poneyTypes.add(poneys.get(i).getClass().getSimpleName());
        }
        
        setChanged();
        notifyObservers(new StartNotification(nbPoneys, poneyTypes));
    }
    
    public int getNbPoneys() {
        return nbPoneys;
    }
    
    public PoneyModel getPoneyModel(int i) {
        return poneys.get(i);
    }
    
    public int getWinAt() {
        return winAt;
    }
    
    public TrackModel getTrackModel() {
        return track;
    }
}