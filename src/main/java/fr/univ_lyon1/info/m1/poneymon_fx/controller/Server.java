package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe implémentant le serveur et gérant les communicatoin avec les clients.
 *
 * @author Alex.
 */
public class Server {

    Thread mainThread;
    public Lobby lobby;
    int nbConnections = 0;
    ServerSocket sSocket;
    String ip = "127.0.0.1";
    int port = 9000;
    boolean isRunning = false;
    List<ServerToClientProcessor> processors = new ArrayList<>();
    Thread t;
    TimedUpdater updater;
    List<Socket> clients = new ArrayList<>();

    /**
     * contructeur par défaut, se connecte sur localHost sur le port 9000 par
     * défaut.
     */
    public Server() {
        try {
            sSocket = new ServerSocket(port, 32, Inet4Address.getByName("localhost"));
            ip = sSocket.getInetAddress().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * constructeur spécialisé, se connecte sur l'addresse ip et le port
     * indiqués.
     *
     * @param ipaddress addresse sur laquelle le serveur doit se connecter.
     * @param iport port sur lequel le serveur doit se connecter.
     */
    public Server(String ipaddress, int iport) {
        try {
            sSocket = new ServerSocket(iport);
            ip = sSocket.getInetAddress().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ouvre le serveur en lançant un thread qui attends les connexions client.
     */
    public void open() {
        if (sSocket == null) {
            try {
                sSocket = new ServerSocket(port, 32, Inet4Address.getByName("localhost"));
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isRunning = true;

        if (lobby != null) {
            updater = new TimedUpdater(this);
        }
        mainThread = new Thread(new Runnable() {
            @Override
            public void run() {

                Thread updaterThread = new Thread(updater);
                updaterThread.setDaemon(true);
                updaterThread.start();
                while (isRunning) {
                    try {

                        Socket client = sSocket.accept();
                        nbConnections++;
                        if (lobby != null) {
                            lobby.addConnection(client);
                        }
                        System.out.println("connexion reçue");
                        ServerToClientProcessor processor;
                        processor = new ServerToClientProcessor(Server.this, client);

                        processors.add(processor);
                        t = new Thread(processor);
                        t.setDaemon(true);
                        clients.add(client);
                        t.start();
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                close();
            }
        });
        mainThread.setDaemon(true);
        mainThread.start();
    }

    public void setLobby(Lobby l) {
        this.lobby = l;
    }

    /**
     * ferme le serveur.
     */
    public void close() {
        isRunning = false;
        System.out.println("closed server");
        mainThread.interrupt();
        try {
            sSocket.close();
            if (updater != null) {
                updater.close();
            }

        } catch (IOException e) {
            System.out.println("suppression du socket");
            e.printStackTrace();
            sSocket = null;
            updater = null;
        }
    }

    /**
     * renvoie les ips des sockets clients associés.
     *
     * @return la liste d'ips.
     */
    public List<String> getIpsClients() {
        List<String> toRet = new ArrayList<String>();
        for (int i = 0; i < clients.size(); i++) {
            toRet.add(clients.get(i).getInetAddress().getHostAddress());
        }
        return toRet;
    }

    /**
     * envoie à tous les clients.
     *
     * @param type type de message.
     * @param message contenu du message.
     */
    public void sendToAll(String type, String message) {
        switch (type) {
            case "COMMAND":
                for (ServerToClientProcessor client : processors) {
                    client.sendCommand(message);
                }
                break;
            case "INPUT":
                for (ServerToClientProcessor client : processors) {
                    client.sendInput(message);
                }
                break;
            case "DATA":
                for (ServerToClientProcessor client : processors) {
                    client.sendData(message);
                }
                break;
            default:
                break;
        }

    }

}
