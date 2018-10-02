package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Alex
 */
public class Server {

    int nbConnections = 0;
    ServerSocket sSocket;
    String ip = "127.0.0.1";
    int port = 9000;
    boolean isRunning = false;
    ServerToClientProcessor processor;
    Thread t;

    public Server() {
        try {
            sSocket = new ServerSocket(port,32,Inet4Address.getByName("localhost"));
            ip = sSocket.getInetAddress().toString();
            isRunning = true;
            System.out.println("server online");
        } catch (IOException e) {
            System.err.println("erreur Constructeur serveur");
            e.printStackTrace();
        }
    }

    public Server(String _ipAdress, int _port) {
        try {
            sSocket = new ServerSocket(port);
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
                        nbConnections++;
                        System.out.println("connexion reçue");
                        processor = new ServerToClientProcessor(client);
                        System.out.println(processor.connexionFermee);
                        t = new Thread(processor);
                        t.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                close();
            }
        });
        mainThread.start();
    }

    public void close() {
        isRunning = false;
        try {
            sSocket.close();
        } catch (IOException e) {
            System.out.println("suppression du socket");
            e.printStackTrace();
            sSocket = null;
        }
    }

}
