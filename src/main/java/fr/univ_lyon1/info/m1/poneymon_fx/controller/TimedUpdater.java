package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * thread envoyant régulièrement le lobby et le modèle sérialisés au clients.
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TimedUpdater.class.getName()).log(Level.SEVERE, null, ex);
            Thread.currentThread().interrupt();
        }
        while (!isClosed) {
            try {
                Thread.sleep(250);
                byte[] b = parent.lobby.serializeModelBinary();
                //System.out.println("tiU : " + Arrays.toString(b));
                this.parent.sendToAll("DATA", 'f' + new String(b, "ISO-8859-1"));

                this.parent.sendToAll("DATA", 'l' + parent.lobby.serializeLobby());
            } catch (JsonProcessingException | InterruptedException ex) {
                Logger.getLogger(TimedUpdater.class.getName()).log(Level.SEVERE, null, ex);
                Thread.currentThread().interrupt();
            } catch (IOException ex) {
                Logger.getLogger(TimedUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
