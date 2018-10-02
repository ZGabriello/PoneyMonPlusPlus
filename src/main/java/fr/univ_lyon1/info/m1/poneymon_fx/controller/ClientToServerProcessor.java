/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Alex
 */
class ClientToServerProcessor implements Runnable {
    Socket sock;
    PrintWriter writer = null;
    BufferedInputStream reader = null;
    boolean connexionFermee = false;
    public ClientToServerProcessor(Socket _sock) {
        sock = _sock;
    }

    @Override
    public void run() {
        while (!sock.isClosed()){
            try{
                writer = new PrintWriter(sock.getOutputStream());
                reader = new BufferedInputStream(sock.getInputStream());
                String reponse = read();
                handleServerCommand(reponse);
                
                if (connexionFermee){
                    System.out.println("connexion fermée");
                    writer = null;
                    reader = null;
                    sock.close();
                    break;
                }
            
            }
            catch (SocketException e){
                System.err.println("CONNEXION INTERROMPUE !");
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    
    private String read() throws IOException{
        String reponse;
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        reponse = new String(b,0,stream);
        return reponse;
    }
    
    private void handleServerCommand(String code) {
        String toSend;
        
        switch (code.toUpperCase()){
            case "CLOSE":
                toSend = "fin  de communication";
                connexionFermee = true;
                break;
            default:
                toSend = "commande non reconnue";
                break;
        }
        
        writer.write(toSend);
        writer.flush();
    }
    
}
