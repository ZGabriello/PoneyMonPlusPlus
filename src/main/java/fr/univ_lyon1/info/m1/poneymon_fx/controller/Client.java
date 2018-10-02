package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Alex
 */
public class Client {
    Socket sock;
    public Client(String _addresse, int _port){
        try{
            sock = new Socket(_addresse,_port);
            Thread t = new Thread(new ClientToServerProcessor(sock));
            t.start();
        }
        catch (UnknownHostException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void close(){
        try{
            sock.close();     
        }
        catch (IOException e){
            e.printStackTrace();
            sock = null;
        }
    }
    
    
}
