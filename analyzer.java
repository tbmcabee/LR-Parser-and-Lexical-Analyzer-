import java.util.*;
import java.io.*;

public class analyzer
{
    public static void main(String [] args) throws IOException
    {

    }

    public static void matrixReaderFunc(String file) throws IOException
    {
        String stringLine;
        int rowCounter = 0;
        BufferedReader codeReader = new BufferedReader(new FileReader(file));
        
        //Keywords: int, double, String (Case sensitive)
        //

        while ((stringLine = codeReader.readLine()) != null)
        {
            int colCount = 0;
            for (int i = 0; i < stringLine.length(); i++)
            {
                
                if (stringLine.charAt(i) == 49 || stringLine.charAt(i) == 48)
                {
                    colCount++;
                }
            }
            rowCounter++;
        }
    }

    public static void keywordFunc()
    {

    }

    public static void identifierFunc()
    {

    }

    public static void 
}