package Model;

import java.util.HashMap;

public class FileInfo {

    private String fileName;

    private byte[] fileByte;


    public FileInfo(String fileName, byte[] fileByte) {
        this.fileName = fileName;
        this.fileByte = fileByte;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getFileByte() {
        return fileByte;
    }

    public void setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
    }
}
