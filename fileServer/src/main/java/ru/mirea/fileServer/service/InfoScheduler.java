package ru.mirea.fileServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InfoScheduler {
    private final InfoService infoService;

    public InfoScheduler(InfoService infoService) {
        this.infoService = infoService;
    }
    @Scheduled(initialDelay = 1000L, fixedDelay = 3000L)
    public void doWork(){
        infoService.updateInfo();
    }
}
