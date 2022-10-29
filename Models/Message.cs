using System;

namespace Models
{
    [Serializable]
    public class Message
    {
        public string text;
        public int[] arrInt;
        public double[] arrDouble;

        public Message(string text, int[] arrInt) {
            this.text = text;
            this.arrInt = arrInt;
        }
        public Message(string text, double[] arrDouble) {
            this.text = text;
            this.arrDouble = arrDouble;
        }
       
    }
}