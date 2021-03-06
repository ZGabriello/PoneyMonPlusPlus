package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.SerializableModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe centrale du module online, elle centralise la quasi totalité du
 * fonctionnement en ligne.
 *
 * @author Alex
 */
public class Lobby {

    List<String> ips = new ArrayList<>();
    List<Integer> ports = new ArrayList<>();
    @JsonIgnore
    String usedIp;
    String hostIp = null;
    @JsonIgnore
    public Server server = null;
    @JsonIgnore
    Client client = null;
    @JsonIgnore
    OnlineGameControl controller;
    @JsonIgnore
    boolean isHost = false;
    @JsonIgnore
    int portUsed = 9000;
    // trouver un moyen d'avoir les ips dans le même ordre sur toutes les machines.

    /**
     * contructeur par défaut.
     */
    public Lobby() {
        try {
            usedIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        ips.add(usedIp);
        ports.add(portUsed);
    }

    /**
     * contructeur avec un port en paramètre.
     *
     * @param port port spécifiue à utiliser.
     */
    public Lobby(int port) {
        portUsed = port;
        try {
            usedIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        ips.add(usedIp);
        ports.add(port);
    }

    public void setController(OnlineGameControl c) {
        this.controller = c;
    }

    public String getUsedIp() {
        return usedIp;
    }

    public void setHost(String ip) {
        this.hostIp = ip;
    }

    /**
     * Se connecte a un serveur distant. 
     * @param ip @ip du serveur distant.
     * @param port port utilisé par le serveur distant.
     */
    public void getRemoteLobby(String ip, int port) {
        client = new Client(ip, port);
        this.portUsed = client.sock.getLocalPort();
        System.out.println(portUsed);
        client.setLobby(this);
    }

    /**
     * Se connecte a un serveur distant avec un numéro de port par défaut (9000).
     * @param ip adresse ip du serveur distant.
     */
    public void getRemoteLobby(String ip) {
        client = new Client(ip, 9000);
        this.portUsed = client.sock.getLocalPort();
        System.out.println(portUsed);
        client.setLobby(this);
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
            getRemoteLobby(this.ips.get(0), this.ports.get(0));
        }
        this.controller.gameUnpause();
    }

    void setSelfServer() {
        hostIp = this.usedIp;
        isHost = true;
        server = new Server(this.usedIp, this.portUsed);
        server.setLobby(this);
    }

    void setSelfServer(String ip, int port) {
        this.usedIp = ip;
        this.portUsed = port;

        isHost = true;
        server = new Server(this.usedIp, this.portUsed);
        server.setLobby(this);
    }

    void openServer() {
        if (this.isHost && this.server != null) {
            this.server.open();
        }
    }

    /**
     * si serveur, lance la partie.
     */
    public void launchGame() {
        if (isHost) {
            server.sendToAll("COMMAND", "STARTGAME");
        }
    }

    void addConnection(Socket client) {
        ips.add(client.getInetAddress().getHostAddress());
        ports.add(client.getPort());
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
            SerializableModel toSend = new SerializableModel(this.controller.model);
            stream.writeObject(toSend);
        }
        byte[] toReturn = b.toByteArray();
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
    }

    void getModelBinary(byte[] data) {

        ByteArrayInputStream stream = new ByteArrayInputStream(data);

        ObjectInputStream o;
        try {
            o = new ObjectInputStream(stream);
            SerializableModel m = (SerializableModel) o.readObject();
            if (this.controller.model == null) {
                this.controller.model = new FieldModel(m.trackName, m.poneys.size());
                this.controller.model.copy(m);
            } else {
                this.controller.model.copy(m);
            }
            o.close();

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
        this.ports = l.ports;
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

    public List<Integer> getPorts() {
        return this.ports;
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
