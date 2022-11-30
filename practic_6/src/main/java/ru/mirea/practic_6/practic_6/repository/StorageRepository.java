package ru.mirea.practic_6.practic_6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.practic_6.practic_6.Model.StorageFile;

public interface StorageRepository extends JpaRepository<StorageFile, Integer> {

    StorageFile findByFileName(String fileName);
}
