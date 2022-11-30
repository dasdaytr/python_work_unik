package ru.mirea.practic_6.practic_6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.practic_6.practic_6.Model.Server;

import java.util.List;

public interface ServerRepository extends JpaRepository<Server,Integer> {

    Server findByUuid(String uuid);
    List<Server> findAll();
    List<Server> findByServerNameAndIsLive(String serverName,boolean isAlive);

    Server findByUuidAndIsLive(String uuid, boolean isAlive);
}
