package ru.mirea.fileServer.Model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ServerInfo {

    private final ServerProperties serverProperties;
    private int port;
    @Value("${server.address}")
    private String address;

    @Value("${service.uuid}")
    private String uuid;

    public ServerInfo(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
        this.port = serverProperties.getPort();
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
