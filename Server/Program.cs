using System;
using System.Globalization;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using Models;

namespace Server
{
    class Program
    {
        static void Main(string[] args)
        {
            // Устанавливаем для сокета локальную конечную точку
            IPHostEntry ipHost = Dns.GetHostEntry("localhost");
            IPAddress ipAddr = ipHost.AddressList[0];
            IPEndPoint ipEndPoint = new IPEndPoint(ipAddr, 11000);

            // Создаем сокет Tcp/Ip
            Socket sListener = new Socket(ipAddr.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

            // Назначаем сокет локальной конечной точке и слушаем входящие сокеты
            try
            {
                sListener.Bind(ipEndPoint);
                sListener.Listen(10);

                // Начинаем слушать соединения
                while (true)
                {
                    Console.WriteLine("Ожидаем соединение через порт {0}", ipEndPoint);

                    // Программа приостанавливается, ожидая входящее соединение
                    Socket handler = sListener.Accept();
                    string data = null;

                    // Мы дождались клиента, пытающегося с нами соединиться
                    byte[] clientData = new byte[1024 * 1024 * 50];
                    int receivedBytesLen = handler.Receive(clientData); 
                    Console.WriteLine("data received...");
                    BinaryFormatter formattor = new BinaryFormatter();
                    MemoryStream ms = new MemoryStream(clientData);
                    
                    Message message = (Message)formattor.Deserialize(ms);
                    string reply = "";
                    if(message.text == "1"){
                        double[] resArr = message.arrDouble;
                        reply = (get3(resArr[0],resArr[1])).ToString(CultureInfo.InvariantCulture);
                    }
                    if(message.text == "2"){
                        int[] resArr = message.arrInt;
                        reply = get6(resArr[0],resArr[1]);
                    }
                    if(message.text == "3"){
                        double[] resArr = message.arrDouble;
                        reply = get9(resArr[0]);
                    }
                    if(message.text == "4"){
                        int[] resArr = message.arrInt;
                        reply = get12(resArr[0],resArr[1],resArr[2]);
                    }
                    if(message.text == "5"){
                        double[] resArr = message.arrDouble;
                        reply = get30(resArr[0],resArr[1]);
                    }
                    //data += Encoding.UTF8.GetString(bytes, 0, bytesRec);
                    
                    // Показываем данные на консоли
                    Console.Write("Полученный текст: " + message.text + "\n\n");
                                            
                    // Отправляем ответ клиенту\
                   
                    byte[] msg = Encoding.UTF8.GetBytes(reply);
                    handler.Send(msg);

                   
                    
                    handler.Shutdown(SocketShutdown.Both);
                    handler.Close();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
            finally
            {
                Console.ReadLine();
            }
        }
        [Obsolete("Obsolete")]
        public static Message DeSerialization (byte[] serializedAsBytes)
        {
            MemoryStream stream = new MemoryStream();           
            BinaryFormatter formatter = new BinaryFormatter();          
            stream.Write(serializedAsBytes, 0, serializedAsBytes.Length);           
            stream.Seek(0, SeekOrigin.Begin);           
            return (Message)formatter.Deserialize(stream); 
        }
        private static double g30(double A,double B){
            return (2*A + B* B)/(A*B*2 + B *5);
        }
        private static string get30(double S, double T){
            return (g30(12,S) + g30(2*S-1, S*T)).ToString(CultureInfo.InvariantCulture);
        }

        private static double f3(double A,double B, double C){
            return (2 * A - B - ((Math.Sin(C))/(5 + C)));
        }
        private static double get3(double S, double T){
            return f3(T,2 * S, 1.17) + f3(2.2, T, S - T);
        }
        private static string get6(int x, int y){
            return (Math.Sqrt( x * x + y * y)).ToString(CultureInfo.InvariantCulture);
        }
        private static string get9(double x){
            String res = "";
            for (double i = -6; i <= 4; i += 0.91)
            {
                double z = Math.Sin(i) + Math.Cos(i);
                if (z > -0.2 && z < 0.8)
                    res += "f("+ i +") = " + z +"\n";
            }
            return res;

        }

        private static string getZnak( params int[]  x){
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < x.Length; i++)
            {
                if (x[i] > 0){
                    res.Append('+');
                    continue;
                }

                res.Append('-');
            }
            
            return res.ToString();
        }
        private static string get12(int x,int y,int z){

            return getZnak(x,y,z);
        }
    }
    
    
}