package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.util.List;

/**
 * Notification envoyée aux Observers pour qu'ils s'initialisent.
 *
 */
public class StartNotification extends Notification {
    public int nbPoneys;
    public List<String> poneyTypes;
    
    /**
     * Constructeur de la notification d'initialisation.
     * @param nbPoneys Nombre de poneys dans la course
     * @param poneyTypes Différents types des poneys dans la course
     */
    public StartNotification(int nbPoneys, List<String> poneyTypes) {
        super("START");
        this.nbPoneys = nbPoneys;
        this.poneyTypes = poneyTypes;
    }
}
