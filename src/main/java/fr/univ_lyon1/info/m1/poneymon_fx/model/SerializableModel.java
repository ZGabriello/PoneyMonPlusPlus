/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.power.PowerModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.state.State;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.Strategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class SerializableModel implements Serializable{
    public List<SerializablePoney> poneys = new ArrayList();
    public String trackName;
    
    class SerializablePoney implements Serializable{
        public double progress;
        public int nbTurns;
        public double speed;
        public Strategy strategy;
        public boolean powerState;
        public PowerModel power;
        public boolean ia;
        public String color;
        public List<State> states;
        public double acceleration;
        public int position;
        public final double[] infos;
        double distance;
        int lanesPassed;
        public SerializablePoney(PoneyModel p){
            progress = p.progress;
            position = p.position;
            speed = p.speed;
            acceleration = p.acceleration;
            nbTurns = p.nbTurns;
            states = p.states;
            color = p.color;
            ia = p.ia;
            power = p.power;
            powerState = p.powerState;
            infos = p.infos;
            lanesPassed = p.lanesPassed;
            distance = p.distance;
            
            //strategy = p.strategy;

        }
    }
    
    public SerializableModel(FieldModel m){
        trackName = m.trackName;
        
        for (int i=0;i<m.nbPoneys;i++){
            PoneyModel currentPoney = m.getPoneys().get(i);
            poneys.add(new SerializablePoney(currentPoney));
        }
    }
}
