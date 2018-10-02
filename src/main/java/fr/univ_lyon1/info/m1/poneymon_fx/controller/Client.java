package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Alex
 */
public class Client {
    Socket sock;
    Thread t;
    ClientToServerProcessor processor;
    public Client(String _addresse, int _port){
        try{
            sock = new Socket(InetAddress.getByName(_addresse),_port);
            processor = new ClientToServerProcessor(sock);
            t = new Thread(processor);
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
