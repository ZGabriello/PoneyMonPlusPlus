package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

/**
 * Thread métier du serveur, gère la communication avec un client.
 * @author Alex.
 */
class ServerToClientProcessor implements Runnable {

    Socket cSocket;
    Server parent;
    PrintWriter writer = null;
    BufferedInputStream reader = null;
    boolean connexionFermeeDemande = false;
    boolean connexionFermee = false;

    public ServerToClientProcessor(Server serveur, Socket client) {
        cSocket = client;
        parent = serveur;
    }

    @Override
    public void run() {

        while (!connexionFermee) {
            try {

                if (connexionFermeeDemande) {
                    System.out.println("connexion fermée");
                    writer = null;
                    reader = null;
                    cSocket.close();
                    connexionFermee = true;
                    break;
                } else {
                    System.out.println("server running ...1");
                    writer = new PrintWriter(cSocket.getOutputStream());
                    reader = new BufferedInputStream(cSocket.getInputStream());
                    System.out.println("server running ...2");
                    String reponse = read();
                    handleClientCommand(reponse);

                }

            } catch (SocketException e) {
                System.err.println("CONNEXION INTERROMPUE !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    String read() throws IOException {
        String reponse;
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        reponse = new String(b, 0, stream);
        return reponse;
    }

    void sendCommand(String byteCode) {
        System.out.println("handling command...");
        writer.write(byteCode);
        writer.flush();
    }

    private void handleClientCommand(String code) {
        String toSend;

        switch (code.toUpperCase()) {

            case "CLOSE":
                parent.nbConnections--;
                toSend = "fin  de communication";
                connexionFermeeDemande = true;
                break;
            default:
                toSend = "commande non reconnue";
                break;
        }

        writer.write(toSend);
        writer.flush();
    }

}
