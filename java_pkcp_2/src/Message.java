import java.io.Serializable;

public class Message implements Serializable {
    private String text;
    private int[] arrInt;
    private double[] arrDouble;

    public Message(String text, int[] arrInt) {
        this.text = text;
        this.arrInt = arrInt;
    }
    public Message(String text, double[] arrDouble) {
        this.text = text;
        this.arrDouble = arrDouble;
    }
    public String getText() {
        return text;
    }

    public double[] getArrDouble() {
        return arrDouble;
    }

    public int[] getArrInt() {
        return arrInt;
    }
}
