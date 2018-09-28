package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe gérant la logique du Field.
 *
 */
public class FieldModel extends Observable {
    /** Tableau des joueurs réels. */
    int[] players = new int[] { 0, 1 };
    
    /** Poneys. */
    int nbPoneys;
    ArrayList<PoneyModel> poneys = new ArrayList<>();
    String[] colorMap =
    new String[] {"blue", "green", "orange", "purple", "yellow"};
    
    /** Nombre de tours pour gagner. */
    final int winAt = 3;
    int winner = -1;
    
    /**
     * Constructeur du FieldModel.
     *
     * @param nbPoneys Nombre de PoneyModel à instancier
     */
    public FieldModel(int nbPoneys) {
        this.nbPoneys = nbPoneys;
        /* On initialise le terrain de course */
        for (int i = 0; i < nbPoneys; i++) {
            poneys.add(new NyanPoneyModel(colorMap[i]));
            
        }
        poneys.get(1).addStrategy(new MoreSpeedStrategy(this,1));
        poneys.get(2).addStrategy(new NotEnoughSpeedStrategy(this,2));
    }
    
    public void usePower(int i) {
        poneys.get(i).usePower();
    }
    
    /**
     *Avancée des différents poneys. 
     */
    public void step() {
        List<Double> progresses = new ArrayList<>();
        for (int i = 0; i < nbPoneys; i++) {
            // la fonction step() dans PoneyModel renvoie le nouveau progrès après mise à jour
            progresses.add(poneys.get(i).step());
            
            if (poneys.get(i).getNbTours() == winAt && winner == -1) {
                winner = i;
                setChanged();
                notifyObservers(new WinNotification(winner));
            }
        }
        
        setChanged();
        notifyObservers(new ProgressNotification(progresses));
    }
    
    /**
     * Initialisation des observeurs du modèle du terrain.
     */
    public void addObserver(Observer obs) {
        super.addObserver(obs);
        
        List<String> poneyTypes = new ArrayList<>();
        for (int i = 0; i < nbPoneys; i++) {
            poneyTypes.add(poneys.get(i).getClass().getSimpleName());
        }
        
        setChanged();
        notifyObservers(new StartNotification(nbPoneys, poneyTypes));
    }
    
    public PoneyModel getPoneyModel(int i) {
        return poneys.get(i);
    }
}