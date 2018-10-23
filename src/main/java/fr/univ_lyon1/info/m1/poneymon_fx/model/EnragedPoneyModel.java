/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PowerNotification;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.ImStillHereNyanStrategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.NyanStrategy;




public class EnragedPoneyModel extends PoneyModel  {
    
    
    /**
     * Constructeur de EnragedPoney sans modèle et autres paramètres, pour tests.
     */
    public EnragedPoneyModel() {
        super();
        PowerModel p = new PowerHit();
        this.setPower(p);
    }
    
    /**
     * Constructeur de EnragedPoney pour joueur humain.
     * @param color couleur du poney
     * @param position position du poney dans le modèle
     * @param f modèle
     */
    public EnragedPoneyModel(String color, int position) {
        super(color, position);
        PowerModel p = new PowerHit();
        this.setPower(p);
    }
    
    /**
     * Constructeur de Enraged avec IA.
     * @param color couleur du poney
     * @param position position du poney dans le modèle
     * @param strategy stratégie à utiliser pour l'ia
     */
    public EnragedPoneyModel(String color, int position, NyanStrategy strategy) {
        super(color, position, strategy);
        PowerModel p = new PowerHit();
        this.setPower(p);
    }
    
    @Override
    public void usePower(PoneyModel p) {
        
        if (powerState == false && nbPowers < 1) {
            ++nbPowers;
            powerState = true;
            this.getPower().use(p);
            setChanged();
            notifyObservers(new PowerNotification(true));
        }

    }
    
    
    
   
    
};