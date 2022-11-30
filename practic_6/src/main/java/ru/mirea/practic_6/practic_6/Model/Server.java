package ru.mirea.practic_6.practic_6.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    private String serverName;

    private String uuid;

    private int serverPort;

    private String serverUrl;

    private boolean isLive;


    public Server(){}
    public Server(int id, String serverName, String uuid, int serverPort, String serverUrl, boolean isLive) {
        this.id = id;
        this.serverName = serverName;
        this.uuid = uuid;
        this.serverPort = serverPort;
        this.serverUrl = serverUrl;
        this.isLive = isLive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", serverName='" + serverName + '\'' +
                ", uuid='" + uuid + '\'' +
                ", serverPort=" + serverPort +
                ", serverUrl='" + serverUrl + '\'' +
                ", isLive=" + isLive +
                '}';
    }
}
