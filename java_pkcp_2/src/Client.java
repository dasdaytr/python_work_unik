import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Client {

    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static ObjectOutputStream objectOutputStream;
    public static void main(String[] args) {


        try {
            try {
                // адрес - локальный хост, порт - 4004, такой же как у сервера
                clientSocket = new Socket("localhost", 8080); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                reader = new BufferedReader(new InputStreamReader(System.in));
                // читать соообщения с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                // если соединение произошло и потоки успешно созданы - мы можем
                //  работать дальше и предложить клиенту что то ввести
                // если нет - вылетит исключение



                System.out.println(Text.GET3 + Text.GET6 + Text.GET9 + Text.GET12 + Text.GET30);


                while (true){
                    System.out.println("Выберите задание, которое хотите выполнить");
                    String word = reader.readLine(); // ждём пока клиент что-нибудь
                    // не напишет в консоль
                    Message m = null;
                    if(word.equals("1"))
                        m = new Message(word, getDouble(2));
                    if(word.equals("2"))
                        m = new Message(word, getInt(2));
                    if(word.equals("3"))
                        m = new Message(word, getDouble(1));
                    if(word.equals("4"))
                        m = new Message(word, getInt(3));
                    if(word.equals("5"))
                        m = new Message(word, getDouble(2));
                    objectOutputStream.writeObject(m); // отправляем сообщение на сервер
                    objectOutputStream.flush();
                    String serverWord = in.readLine();
                    // ждём, что скажет сервер
                    System.out.println(serverWord); // получив - выводим на экран
                }
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }


    }


    private static double[] getDouble(int k){
        System.out.println("Введите " + k + " числа") ;
        Scanner scanner = new Scanner(System.in);
        double[] res = new double[k];
        for(int i = 0; i < k; i ++){
            res[i] = scanner.nextDouble();
        }
        return res;
    }
    private static int[] getInt(int k){
        System.out.println("Введите " + k + " числа") ;
        Scanner scanner = new Scanner(System.in);
        int[] res = new int[k];
        for(int i = 0; i < k; i ++){
            res[i] = scanner.nextInt();
        }
        return res;
    }
}
