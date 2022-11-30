package ru.mirea.fileServer.service;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;
import ru.mirea.fileServer.Model.Server;
import ru.mirea.fileServer.Model.ServerInfo;
import ru.mirea.fileServer.repository.ServerRepository;

@Component
public class InfoService implements IInfoService{

    private final ServerRepository serverRepository;
    private final ServerInfo serverInfo;
    private final ServerProperties serverProperties;
    public InfoService(ServerRepository serverRepository, ServerInfo serverInfo, ServerProperties serverProperties) {
        this.serverRepository = serverRepository;
        this.serverInfo = serverInfo;
        this.serverProperties = serverProperties;
    }

    @Override
    public void updateInfo() {

        Server server = serverRepository.findByUuid(serverInfo.getUuid());
        if(server == null){
            System.out.println("Обновляем информацию о себе");
            Server newServer = new Server();
            newServer.setServerPort(serverInfo.getPort());
            newServer.setServerName("fileServer");
            newServer.setServerUrl(serverInfo.getAddress());
            newServer.setLive(true);
            newServer.setUuid(serverInfo.getUuid());
            serverRepository.save(newServer);
        }
        System.out.println(server);
    }
}
