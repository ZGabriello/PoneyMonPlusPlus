package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Alex
 */
public class Lobby {

    List<String> ips = new ArrayList<>();
    String usedIp;
    String hostIp = null;
    Server server = null;
    Client client = null;
    Controller controller;
    boolean isHost = false;
    // trouver un moyen d'avoir les ips dans le même ordre sur toutes les machines.

    public Lobby() {
        try {
            usedIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        ips.add(usedIp);
    }

    public void setController(Controller c) {
        this.controller = c;
    }

    public String getUsedIp() {
        return usedIp;
    }

    public void setHost(String ip) {
        this.hostIp = ip;
    }

    
    
    public void getRemoteLobby(String ip, int port) {
        client = new Client(ip ,port );
        client.sendCommand("GIVEHOST");
    }

    public void starMigration() {
        //TODO 
    }

    void migrate() {
        this.controller.gamePause();
        if (this.ips.get(0).equals(this.usedIp)){
            setSelfServer();
            openServer();
        }
        else{
            getRemoteLobby(this.ips.get(0), 9000);
        }
        this.controller.gameUnpause();
    }

    void setSelfServer() {
        hostIp = this.usedIp;
        isHost = true;
        server = new Server(this.usedIp, 9000);
    }
    
    void openServer(){
        if ((this.hostIp.equals(this.usedIp)) && (this.server!=null)){
            this.server.open();
        }
    }

    public void launchGame() {
        if (hostIp == null ? usedIp == null : hostIp.equals(usedIp)) {
            server.sendToAll("COMMAND", "STARTGAME");
        }
    }

    void addConnection(Socket client) {
        ips.add(client.getInetAddress().getHostAddress());
        if (hostIp == null ? usedIp == null : hostIp.equals(usedIp)){
            server.sendToAll("COMMAND", "UPDATELOBBY");
        }
    }
    
    String serializeModel() throws JsonProcessingException{
        String toRet;
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        toRet = mapper.writeValueAsString(this.controller.model);
        return toRet;
    }
    
    String serializeLobby() throws JsonProcessingException{
        String toRet;
        ObjectMapper mapper = new ObjectMapper();
        toRet = mapper.writeValueAsString(this);
        return toRet;
    }
    
    void getModel(String json){
        ObjectMapper mapper = new ObjectMapper();
        FieldModel m = null;
        try {
            m = mapper.readValue(json, FieldModel.class);
        } catch (IOException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.controller.model = m;
    } 

    void getLobby(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Lobby l = null;
        try {
            l = mapper.readValue(json, Lobby.class);
        } catch (IOException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.ips=l.ips;
        this.hostIp = l.hostIp;        
    }
}