package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class Lobby {

    List<String> ips = new ArrayList<>();
    @JsonIgnore
    String usedIp;
    String hostIp = null;
    @JsonIgnore
    Server server = null;
    @JsonIgnore
    Client client = null;
    @JsonIgnore
    Controller controller;
    @JsonIgnore
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
        client = new Client(ip, port);
        //client.sendCommand("GIVEHOST");
    }

    public void getRemoteLobby(String ip) {
        client = new Client(ip, 9000);
        //client.sendCommand("GIVEHOST");
    }

    public void starMigration() {
        //TODO 
    }

    void migrate() {
        this.controller.gamePause();
        if (this.ips.get(0).equals(this.usedIp)) {
            setSelfServer();
            openServer();
        } else {
            getRemoteLobby(this.ips.get(0));
        }
        this.controller.gameUnpause();
    }

    void setSelfServer() {
        hostIp = this.usedIp;
        isHost = true;
        server = new Server(this.usedIp, 9000);
    }

    void openServer() {
        if ((this.hostIp.equals(this.usedIp)) && (this.server != null)) {
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
        if (hostIp == null ? usedIp == null : hostIp.equals(usedIp)) {
            server.sendToAll("COMMAND", "UPDATELOBBY");
        }
    }

    String serializeModel() throws JsonProcessingException {
        String toRet;
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.enableDefaultTyping();
        toRet = mapper.writeValueAsString(this.controller.model);
        
        return toRet;
    }

    byte[] serializeModelBinary() throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        try (ObjectOutputStream stream = new ObjectOutputStream(b)) {
            stream.writeObject(this.controller.model);
        }
        byte[] toReturn = b.toByteArray();
        System.out.println("SMB : " + Arrays.toString(toReturn));
        return toReturn;
    }

    String serializeLobby() throws JsonProcessingException {
        String toRet;
        ObjectMapper mapper = new ObjectMapper();
        toRet = mapper.writeValueAsString(this);
        return toRet;
    }

    void getModel(String json) {
        ObjectMapper mapper = new ObjectMapper();
        FieldModel m = null;
        try {
            m = mapper.readValue(json, FieldModel.class);
        } catch (IOException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.controller.model = m;
    }

    void getModelBinary(byte[] data) {
        
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        
        ObjectInputStream o;
        try {
            o = new ObjectInputStream(stream);
            System.out.println("end : " + Arrays.toString(data));
            this.controller.model = (FieldModel) o.readObject();
            
            o.close();
            System.out.println("modèle mis a jour");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    void getLobby(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Lobby l = new Lobby();
        try {
            l = mapper.readValue(json, Lobby.class);
        } catch (IOException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.ips = l.ips;
        this.hostIp = l.hostIp;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isIsHost() {
        return isHost;
    }

    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }

}
