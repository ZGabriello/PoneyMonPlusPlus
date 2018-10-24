package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
class TimedUpdater implements Runnable{
    boolean isClosed =false;
    Server parent;
    TimedUpdater(Server s){
        parent = s;
    }
    void close() {
        this.isClosed=true;
    }

    @Override
    public void run() {
        while (!isClosed){
            try {
                Thread.sleep(500);
                this.parent.sendToAll("DATA", parent.lobby.serializeModel());
                this.parent.sendToAll("DATA", parent.lobby.serializeLobby());
            } catch (JsonProcessingException | InterruptedException ex) {
                Logger.getLogger(TimedUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}