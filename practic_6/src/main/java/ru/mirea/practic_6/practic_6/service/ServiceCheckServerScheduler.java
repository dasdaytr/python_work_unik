package ru.mirea.practic_6.practic_6.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ServiceCheckServerScheduler {

    private ServiceCheckServer serviceCheckServer;

    public ServiceCheckServerScheduler(ServiceCheckServer serviceCheckServer) {
        this.serviceCheckServer = serviceCheckServer;
    }

    @Scheduled(initialDelay = 1000L,fixedDelay = 3000L)
    public void doWork(){
        serviceCheckServer.checkServer();
    }
}
