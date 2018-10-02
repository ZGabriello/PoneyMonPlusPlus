package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Alex
 */
public class Server {

    ServerSocket sSocket;
    String ip = "127.0.0.1";
    int port = 9000;
    boolean isRunning = false;

    public Server() {
        try {
            sSocket = new ServerSocket(port);
            ip = sSocket.getInetAddress().toString();
            isRunning = true;
        } catch (IOException e) {
            System.err.println("le port 9000 est déja utilisé");
        }
    }

    public Server(String _ipAdress, int _port) {
        try {
            sSocket = new ServerSocket(port, 10, InetAddress.getByName(_ipAdress));
            ip = sSocket.getInetAddress().toString();
        } catch (IOException e) {
            System.err.println("le port 9000 est déja utilisé");
        }
    }

    public void open() {
        Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        Socket client = sSocket.accept();
                        System.out.println("connexion reçue");
                        Thread t = new Thread(new ServerToClientProcessor(client));
                        t.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                
                try {
                    sSocket.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                    sSocket = null;
                }
            }
        });
    }
    
    public void close(){
        isRunning = false;
    }

}
