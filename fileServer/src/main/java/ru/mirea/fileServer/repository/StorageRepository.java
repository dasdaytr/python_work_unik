package ru.mirea.fileServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.fileServer.Model.StorageFile;

public interface StorageRepository extends JpaRepository<StorageFile, Integer> {

    StorageFile findByFileName(String fileName);
}
