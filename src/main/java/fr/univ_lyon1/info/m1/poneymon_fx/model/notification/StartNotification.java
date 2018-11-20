package fr.univ_lyon1.info.m1.poneymon_fx.model.notification;

import java.util.List;

/**
 * Notification envoyée aux Observers terrains pour qu'ils s'initialisent.
 *
 */
public class StartNotification extends Notification {

    int nbPoneys;
    List<String> poneyTypes;

    /**
     * Constructeur de la notification d'initialisation du terrain.
     *
     * @param nbPoneys Nombre de poneys dans la course
     * @param poneyTypes Différents types des poneys dans la course
     */
    public StartNotification(int nbPoneys, List<String> poneyTypes) {
        super("START");
        this.nbPoneys = nbPoneys;
        this.poneyTypes = poneyTypes;
    }

    public int getNbPoneys() {
        return nbPoneys;
    }

    public List<String> getPoneyTypes() {
        return poneyTypes;
    }
}
