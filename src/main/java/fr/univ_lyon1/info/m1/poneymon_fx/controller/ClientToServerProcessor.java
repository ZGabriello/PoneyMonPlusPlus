package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * thread du client communiquant avec le serveur.
 *
 * @author Alex.
 */
class ClientToServerProcessor extends Processor {

    Socket sock;
    Client parent;

    public ClientToServerProcessor(Socket isock, Client c) {
        sock = isock;
        parent = c;
        try {
            writer = new PrintWriter(sock.getOutputStream());
            reader = new BufferedInputStream(sock.getInputStream());
            System.out.println("coucou");
        } catch (IOException ex) {
            Logger.getLogger(ClientToServerProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (!connexionFermee) {
            try {

                String reponse = read();
                parseMessage(reponse);

                if (connexionFermeeDemande) {
                    System.out.println("le client ferme");
                    writer = null;
                    reader = null;
                    sock.close();
                    connexionFermee = true;
                    break;
                }

            } catch (SocketException e) {
                System.err.println("CONNEXION INTERROMPUE ! (client)");
                connexionFermee = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleServerCommand(String code) {
        String toSend;

        switch (code.toUpperCase()) {
            case "CLOSE":
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

    // les messages commandes sont de la forme "TypeDeDonnes(sur 1 caractère)+Json"
    @Override
    void parseData(String data) {
        //System.out.println("parsing datatatatatatata");
        switch (data.charAt(0)) {
            case 'f': //fieldmodel
                //System.out.println("tis a field");
        
                try {
                    parent.lobby.getModelBinary(data.substring(1).getBytes("ISO-8859-1"));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ClientToServerProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
        
                break;
            case 'p': // poneyModel
                break;
            case 'l': // Lobby
                parent.lobby.getLobby(data.substring(1));
                break;
            default:
                System.out.print("i dunno");
                break;
        }
    }
    
    @Override
    public void parseInput(String substring){
        switch (substring.substring(0,3)){
            case "POW":
                this.parent.lobby.controller.applyPower(Integer.parseInt(substring.substring(3)) );
                break;
            case "PAU":
                this.parent.lobby.controller.gamePauseClient();
        }
    }
}
