using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApp1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {

            try
            {
                int A = int.Parse(this.A30.Text);
                int B = int.Parse(this.B30.Text);
                int C = int.Parse(this.C30.Text);
                int X = int.Parse(this.X30.Text);
                int Y = int.Parse(this.Y30.Text);
                this.Res30.Text = get30(A, B, C, X, Y);
            }
            catch(FormatException ex)
            {
                this.Res30.Text = "Error format";
            }
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            
        }

        private static string get30(int a1, int a2, int b1, int b2, int R)
        {
            if (((a1 * a1 + b1 * b1) <= R * R) && ((a2 * a2 + b2 * b2) <= R * R))
                return "Points lies within the circle";

            return "One or two points lie outside the circle";
        }

        private static string get3(int a, int b, int c, int x, int y)
        {
            if (((a <= x) && (b <= y)) ||
                ((b <= x) && (a <= y)) ||
                ((a <= x) && (c <= y)) ||
                ((c <= x) && (a <= y)) ||
                ((c <= x) && (b <= y)) ||
                ((b <= x) && (c <= y)))
                return "Оно вошло";
            return "Оно не вошло";
        }

        private static int get6(int a, int b, int c) {
            if (b > a && b < c)
                return b + c;
            return c -b;
        }

        private static string get9(int a, int r) {

            if ((a == 2 * r))
                return "Окружность вписана в квадрат";
            else
            {
                if (Math.Pow((a * a + a * a), 2) == r)
                {
                    return "Окружность описана вокруг квадрата";
                }
                else
                {
                    if (a < r * 2)
                        return "Квадрат лежит внутри окружности";
                    else
                        return "Окружность лежит не лежит внутри квадрата";
                }
                  
            }
               
        }

        private static int get12(int a, int b) {

            if (a > 0 && b > 0)
                return a * a + b * b;


            if (a > 0)
                return a * a;

            return b * b;

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void label28_Click(object sender, EventArgs e)
        {

        }

        private void Btn3_Click(object sender, EventArgs e)
        {
            try
            {
                int A = int.Parse(this.A3.Text);
                int B = int.Parse(this.B3.Text);
                int C = int.Parse(this.C3.Text);
                int X = int.Parse(this.X3.Text);
                int Y = int.Parse(this.Y3.Text);
                this.Res3.Text = get3(A, B, C, X, Y);
            }
            catch (FormatException ex)
            {
                this.Res3.Text = "Error format";
            }
        }

        private void Btn6_Click(object sender, EventArgs e)
        {
            try
            {
                int A = int.Parse(this.A6.Text);
                int B = int.Parse(this.B6.Text);
                int C = int.Parse(this.C6.Text);
               
                this.Res6.Text = Convert.ToString( get6(A, B, C));
            }
            catch (FormatException ex)
            {
                this.Res6.Text = "Error format";
            }
        }

        private void Btn9_Click(object sender, EventArgs e)
        {
            try
            {
                int R = int.Parse(this.R9.Text);
                int A = int.Parse(this.A9.Text);

                this.Res9.Text = Convert.ToString(get9(A,R));
            }
            catch (FormatException ex)
            {
                this.Res6.Text = "Error format";
            }
        }

        private void Btn12_Click(object sender, EventArgs e)
        {


            try
            {
                int A = int.Parse(this.A12.Text);
                int B = int.Parse(this.B12.Text);

                this.Res12.Text = Convert.ToString(get12(A, B));
            }
            catch (FormatException ex)
            {
                this.Res6.Text = "Error format";
            }


        }
    }
}
