package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Objet gérant la connetion du client et la communication avec le serveur.
 * @author Alex.
 */
public class Client {
    Lobby lobby;
    Socket sock;
    Thread t;
    ClientToServerProcessor processor;

    /**
     * Constructeur client avec addresse ip et port.
     * @param addresse addresse ip sur laquelle le client doit se connecter.
     * @param port port sur lequel le client doit se connecter.
     */
    public Client(String addresse, int port) {
        try {
            sock = new Socket(InetAddress.getByName(addresse), port);
            processor = new ClientToServerProcessor(sock,this);
            t = new Thread(processor);
            t.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ferme le client , coupant ainsi la connexion avec le serveur.
     */
    public void close() {
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
            sock = null;
        }
    }
    
    
    void setLobby(Lobby l){
        this.lobby = l;
    }
    
    public void sendCommand(String command){
        processor.sendCommand(command.toUpperCase());
    }
    
    public void sendInput(String inputCode){
        processor.sendInput(inputCode);
    }
    
    public void sendData(String data){
        processor.sendData(data);
    }
    
}
