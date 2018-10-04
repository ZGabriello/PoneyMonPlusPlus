package fr.univ_lyon1.info.m1.poneymon_fx.model.strategy;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

public class ImStillHereNyanStrategy extends NyanStrategy {

    public ImStillHereNyanStrategy(FieldModel f, NyanPoneyModel p, int i) {
        super(f, p, i);
    }
    
    @Override
    public void checkPower() {
        if (myPoney.getProgress() == 0.0) {
            // Si l'on rattrape le poney en tête ou que c'est le dernier tour
            if (catchingUp() || (myPoney.getNbTours() == field.getWinAt() - 1)) {
                myPoney.usePower();
            }
        }
    }
    
    /**
     * Retourne le poney en tête.
     *
     */
    PoneyModel premierponey() {
        PoneyModel temp;
        temp = field.getPoneyModel(position);
        for (int i = 0; i < field.getNbPoneys(); i++) {
            if (myPoney.distanceTo(field.getPoneyModel(i)) > myPoney.distanceTo(temp)) {
                temp = field.getPoneyModel(i);
            }
        }
        return temp;
    }
    
    /**
     * Renvoie vrai si l'on peut réduire la distance avec le poney en tête de 0,5
     * avec l'hypothèse qu'il garde la même vitesse.
     *
     */
    boolean catchingUp() {
        PoneyModel premier = premierponey();
        
        // nombre de step pour finir le tour avec le boost
        double nbStepsToFinishTurn = 1.0 / (myPoney.getSpeed() * SPEED_MULTIPLIER);
        
        double initProgressPremier = premier.getProgress();
        double newProgressPremier = premier.getSpeed() * nbStepsToFinishTurn;
        
        // on avance de 1.0 progress pendant que le premier avance
        // de (newProgressPremier - initProgressPremier)
        double distanceGain = 1.0 - (newProgressPremier - initProgressPremier);
        
        return distanceGain > 0.5;
    }
}
