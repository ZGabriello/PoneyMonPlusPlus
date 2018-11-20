package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * classe serializable et légère permettent de récupérer les infos d'un poney.
 * @author Alex
 */
public class SerializableModel implements Serializable {

    public List<SerializablePoney> poneys = new ArrayList();
    public String trackName;

    class SerializablePoney implements Serializable {

        public double progress;
        public int nbTurns;
        public double speed;
        public boolean powerState;
        public double acceleration;
        public int position;
        public final double[] infos;
        double distance;
        int lanesPassed;

        public SerializablePoney(PoneyModel p) {
            progress = p.progress;
            position = p.position;
            speed = p.speed;
            acceleration = p.acceleration;
            nbTurns = p.nbTurns;
            powerState = p.powerState;
            infos = p.infos;
            lanesPassed = p.lanesPassed;
            distance = p.distance;
        }
    }

    /**
     * constructeur.
     * @param m poneyModel a copier.
     */
    public SerializableModel(FieldModel m) {
        trackName = m.trackName;

        for (int i = 0; i < m.nbPoneys; i++) {
            PoneyModel currentPoney = m.getPoneys().get(i);
            poneys.add(new SerializablePoney(currentPoney));
        }
    }
}
