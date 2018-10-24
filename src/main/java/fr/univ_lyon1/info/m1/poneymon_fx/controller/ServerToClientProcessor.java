package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread métier du serveur, gère la communication avec un client.
 *
 * @author Alex.
 */
class ServerToClientProcessor extends Processor {

    Socket cSocket;
    Server parent;

    public ServerToClientProcessor(Server serveur, Socket client) {
        try {
            cSocket = client;
            parent = serveur;
            writer = new PrintWriter(cSocket.getOutputStream());
            reader = new BufferedInputStream(cSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerToClientProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        while (!connexionFermee) {
            try {
                String reponse = read();
                parseMessage(reponse);
                if (connexionFermeeDemande) {
                    System.out.println("connexion fermée");
                    writer = null;
                    reader = null;
                    cSocket.close();
                    connexionFermee = true;
                    break;
                }

            } catch (SocketException e) {
                System.err.println("CONNEXION INTERROMPUE ! (serveur)");
                connexionFermee = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClientCommand(String code) {
        String toSend = "";

        switch (code.toUpperCase()) {

            case "CLOSE":
                parent.nbConnections--;
                toSend = "fin  de communication";
                connexionFermeeDemande = true;
                break;
            case "PAUSE":
                //TODO
                break;
            case "RESTART":
                //TODO
                break;
            case "GIVELOBBY":
                //TODO
                break;
            case "GIVEMODEL":
                //TODO
                break;
            default:
                toSend = "commande non reconnue";
                break;
        }

        writer.write(toSend);
        writer.flush();
    }
}
