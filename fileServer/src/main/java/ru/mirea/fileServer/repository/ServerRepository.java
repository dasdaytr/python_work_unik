package ru.mirea.fileServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.fileServer.Model.Server;

import java.util.List;

public interface ServerRepository extends JpaRepository<Server,Integer> {

    Server findByUuid(String uuid);
}
