package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
class TimedUpdater implements Runnable {

    boolean isClosed = false;
    Server parent;

    TimedUpdater(Server s) {
        parent = s;
    }

    void close() {
        this.isClosed = true;
    }

    @Override
    public void run() {
        while (!isClosed) {
            try {
                Thread.sleep(100);
                System.out.println("TimedUpdaterEnvoie");
                this.parent.sendToAll("DATA", 'f' + new String(parent.lobby.serializeModelBinary()));
                this.parent.sendToAll("DATA",'l' + parent.lobby.serializeLobby());
            } catch (JsonProcessingException | InterruptedException ex) {
                Logger.getLogger(TimedUpdater.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TimedUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
