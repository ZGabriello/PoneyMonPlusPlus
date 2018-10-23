/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.PoneyStartNotification;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class EnragedPoneyView extends PoneyView {
    EnragedPoneyView(GraphicsContext gc, int w) {
        super(gc, w);
    }
    
    /**
     * Gère l'initialisation du EnragedPoneyView à partir des données du PoneyModel.
     * @param psn notification pour initialiser le poney
     */
    @Override
    public void initialize(PoneyStartNotification psn) {
        super.initialize(psn);
        
        if (graphicsContext != null) {
            powerImage = new Image("assets/pony-" + color + "-rainbow.gif");    // remplacer par l'image de crunch
        }
    }
    
    
    
    
}
