package ru.mirea.practic_6.practic_6.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StorageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    private String uuid;

    private String fileName;


    public StorageFile(){}
    public StorageFile(int id, String uuid, String fileName) {
        this.id = id;
        this.uuid = uuid;
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
