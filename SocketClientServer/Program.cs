using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using Models;

namespace SocketClientServer
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                Console.WriteLine(Text.GET3 + Text.GET6 + Text.GET9 + Text.GET12 + Text.GET30);
                SendMessageFromSocket(11000);
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

        static void SendMessageFromSocket(int port)
        {
            // Буфер для входящих данных
            byte[] bytes = new byte[1024];

            // Соединяемся с удаленным устройством
            
            // Устанавливаем удаленную точку для сокета
            IPHostEntry ipHost = Dns.GetHostEntry("localhost");
            IPAddress ipAddr = ipHost.AddressList[0];
            IPEndPoint ipEndPoint = new IPEndPoint(ipAddr, port);
            
            Socket sender = new Socket(ipAddr.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
            
            // Соединяем сокет с удаленной точкой
            sender.Connect(ipEndPoint);

            Console.Write("Выберите задание, которое хотите выполнить");
            string message = Console.ReadLine();
            
            Message m = null;
            if(message == "1")
                m = new Message(message, getDouble(2));
            if(message == "2")
                m = new Message(message, getInt(2));
            if(message == "3")
                m = new Message(message, getDouble(1));
            if(message == "4")
                m = new Message(message, getInt(3));
            if(message == "5")
                m = new Message(message, getDouble(2));
            
            Console.WriteLine("Сокет соединяется с {0} ", sender.RemoteEndPoint.ToString());
            byte[] msg = Serialization(m);
            
            // Отправляем данные через сокет
            int bytesSent = sender.Send(msg);
            
            // Получаем ответ от сервера
            int bytesRec = sender.Receive(bytes);
            
            Console.WriteLine("\nОтвет от сервера: {0}\n\n", Encoding.UTF8.GetString(bytes, 0, bytesRec));

            // Используем рекурсию для неоднократного вызова SendMessageFromSocket()
            if (message.IndexOf("<TheEnd>") == -1)
                SendMessageFromSocket(port);
            
            // Освобождаем сокет
            sender.Shutdown(SocketShutdown.Both);
            sender.Close();
        }
        public static byte[] Serialization (Message  obj)
        {
            BinaryFormatter formatter = new BinaryFormatter();          
            MemoryStream stream = new MemoryStream();           
            formatter.Serialize(stream, obj);           
            byte[] msg = stream.ToArray();
            return msg;
        }
        private static double[] getDouble(int k){
            Console.WriteLine($"Введите {k} числа") ;
            double[] res = new double[k];
            for(int i = 0; i < k; i ++){
                
                res[i] = Convert.ToDouble(Console.ReadLine());
            }
            return res;
        }
        private static int[] getInt(int k){
            Console.WriteLine($"Введите {k} числа") ;
            int[] res = new int[k];
            for(int i = 0; i < k; i ++){
                res[i] = Convert.ToInt32(Console.ReadLine());
            }
            return res;
        }
    }
}