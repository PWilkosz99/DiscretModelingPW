using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace DiscretModeling
{
    class Program
    {
        static int[] getBitArray(string filePath)
        {
            StreamReader streamReader = new StreamReader(filePath);
            string sourceString = streamReader.ReadToEnd();
            streamReader.Close();
            var wordsarr = sourceString.Split(new string[] { "\r\n", "\n", "\t" }, StringSplitOptions.None);
            return Array.ConvertAll(wordsarr, s => int.Parse(s));
        }

        static void saveToTxtFile(int[] bits)
        {
            string text = "";
            int i = 0;
            foreach (var b in bits)
            {
                text += (b + "\t");
                if (i > 40)
                {
                    text += "\r\n";
                }
                i++;
            }
            File.WriteAllText("ColorMap.txt", text);
            Console.WriteLine("Data saved");
        }

        static int[] Brithening(int[] bits, int b)
        {
            int i = 0, iline = 0;
            int maxval = 255;
            foreach (int bt in bits)
            {
                var a = bt + b;
                if (a < 0)
                {
                    bits[i] = 0;
                }
                else if (a > maxval)
                {
                    bits[i] = maxval;
                }
                else
                {
                    bits[i] = a;
                }
                i++;
            }
            return bits;
        }

        static void Main(string[] args)
        {
            var bits = getBitArray("Mapa_MD_no_terrain_low_res_Gray.txt");
            var res = Brithening(bits, -1000);
            saveToTxtFile(res);
        }
    }
}
