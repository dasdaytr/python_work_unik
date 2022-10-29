import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Server {

    private Socket socket; // сокет, через который сервер общается с клиентом,
    // кроме него - клиент и сервер никак не связаны
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out;
    private static ObjectInputStream objectInputStream;
    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(PORT);
        Socket  socket =  server.accept();

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message = null;

        while (true) {
            message = (Message) objectInputStream.readObject();

            if(message.getText().equals("1")){
                double[] resArr = message.getArrDouble();
                send(String.valueOf(get3(resArr[0],resArr[1])));
            }
            if(message.getText().equals("2")){
                int[] resArr = message.getArrInt();
                send(get6(resArr[0],resArr[1]));
            }
            if(message.getText().equals("3")){
                double[] resArr = message.getArrDouble();
                send(get9(resArr[0]));
            }
            if(message.getText().equals("4")){
                int[] resArr = message.getArrInt();
                send(get12(resArr[0],resArr[1],resArr[2]));
            }
            if(message.getText().equals("5")){
                double[] resArr = message.getArrDouble();
                send(get30(resArr[0],resArr[1]));
            }
            System.out.println(message.getText());
        }


    }
    private static void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }


    private static double g30(double A,double B){
        return (2*A + B* B)/(A*B*2 + B *5);
    }
    private static String get30(double S, double T){
        return String.valueOf(g30(12,S) + g30(2*S-1, S*T));
    }

    private static double f3(double A,double B, double C){
        return (2 * A - B - ((sin(C))/(5 + C)));
    }
    private static double get3(double S, double T){
        return f3(T,2 * S, 1.17) + f3(2.2, T, S - T);
    }
    private static String get6(int x, int y){
        return String.valueOf(Math.sqrt( x * x + y * y));
    }
    private static String get9(double x){
        String res = "";
        for (double i = -6; i <= 4; i += 0.91)
        {
            double z = sin(i) + cos(i);
            if (z > -0.2 && z < 0.8)
                res += "f("+ i +") = " + z +"\n";
        }
        return res;

    }

    private static String getZnak(int ... x){
        StringBuilder res = new StringBuilder();
        for(int number: x){
            if (number > 0){
                res.append("+");
                continue;
            }

            res.append("-");
        }
        return res.toString();
    }
    private static String get12(int x,int y,int z){

        return getZnak(x,y,z);
    }
}
