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
        try {
            lobby = new Lobby();
        } catch (UnknownHostException ex) {
            Logger.getLogger(OnlineController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lobby.setController(this);
        lobby.setSelfServer();
        lobby.openServer();
    }
    
    void joinLobby(String ip,int port){
        try {
            lobby = new Lobby();
        } catch (UnknownHostException ex) {
            Logger.getLogger(OnlineController.class.getName()).log(Level.SEVERE, null, ex);
        }
        lobby.getRemoteLobby(ip, port);
    }
    
    
}
