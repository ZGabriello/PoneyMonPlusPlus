package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
/**
 *
 * @author Alex
 */
class ClientProcessor implements Runnable {
    Socket cSocket;
    PrintWriter writer = null;
    BufferedInputStream reader = null;
    boolean connexionFermee = false;
    public ClientProcessor(Socket client) {
        cSocket = client;
    }

    @Override
    public void run() {
        
        while (!cSocket.isClosed()){
            try{
                writer = new PrintWriter(cSocket.getOutputStream());
                reader = new BufferedInputStream(cSocket.getInputStream());
                String reponse = read();
                handleClientCommand(reponse);
                
                if (connexionFermee){
                    System.out.println("connexion fermée");
                    writer = null;
                    reader = null;
                    cSocket.close();
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

    private void handleClientCommand(String code) {
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
