package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class OnlineController extends Controller{
    Lobby lobby;
    void createLobby() {
        lobby = new Lobby();
        
        lobby.setController(this);
        lobby.setSelfServer();
        lobby.openServer();
    }
    
    void joinLobby(String ip,int port){
        lobby = new Lobby();
        lobby.getRemoteLobby(ip, port);
    }
    
    
}
