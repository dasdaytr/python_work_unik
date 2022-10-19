package Enums;

public enum TypeFile {
    XML("xml"),
    JSON("json"),
    XLS("xls");

    private final String type;

    TypeFile(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
