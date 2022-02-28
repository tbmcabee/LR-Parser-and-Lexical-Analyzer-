import java.util.*;
import java.io.*;

public class LexicalAnalyzerProject1TallonMcAbee
{
    public static void main(String [] args) throws IOException
    {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Please input source code file name (Include .txt extension): ");
        String fileName = userInput.next();

        lexicalReader(fileName);
    }

    public static void lexicalReader(String file) throws IOException
    {
        String stringLine;
        int rowCounter = 0;
        int arrayCount = 0;
        int[] upperCase = new int[26];
        int[] lowerCase = new int[26];
        char[] operatorCase = new char[9];
        String[] keywordCase = new String[3];
        BufferedReader codeReader = new BufferedReader(new FileReader(file));
        
        //Keywords: int, double, String (Case sensitive)
        //Operators: = (61) ( (40) ) (41) + (43) - (45) * (42) / (47) , (44) ; (59)
        //Identifier: letter/(letter/digit)
        //int constant digit...
        //double constant digit.digit
        //string constant "string"
        //anything else should be listed as ERROR
        
        //65 - 90 (A), 97 - 122 (a)

        operatorCase[0] = '=';
        operatorCase[1] = '(';
        operatorCase[2] = ')';
        operatorCase[3] = '+';
        operatorCase[4] = '-';
        operatorCase[5] = '*';
        operatorCase[6] = '/';
        operatorCase[7] = ',';
        operatorCase[8] = ';';

        keywordCase[0] = "int";
        keywordCase[1] = "double";
        keywordCase[2] = "String";

        while ((stringLine = codeReader.readLine()) != null)
        {
            int colCount = 0;
            int wordCount = 0;
            boolean maybeConst = false;
            boolean isOperator = false;
            boolean errorCheck = false;
            String runningWord = "";

            for (int i = 0; i < stringLine.length(); i++)
            {
                isOperator = false;

                runningWord += stringLine.charAt(i);

                if (stringLine.charAt(i) == 32) //Checks for space between words, resets the running word and set count for word position
                {
                    if (((runningWord.length()-1) >= 1) && !maybeConst) //Checks if there is an indentifer before hand
                    {
                        identiferCheck(runningWord, rowCounter, wordCount);
                        runningWord = "";
                        wordCount = i+1;
                        continue;
                    }
                    if (maybeConst)
                    {
                        System.out.println("Line " + (rowCounter + 1) + ": " + (i+1) + " error: " + runningWord);
                        runningWord = "";
                        wordCount =  i+1;
                    }
                    else
                    {
                        runningWord = "";
                        continue;
                    }
                }
                if ((stringLine.charAt(i) >= 65 && stringLine.charAt(i) <= 90) || stringLine.charAt(i) == 34) //Check for uppercase, String keyword, or constant String
                {
                    if (runningWord.length() == 1 && stringLine.charAt(i) == 83) //Begins check for String keyword
                    {
                        String tempWord = runningWord;
                        for (int b = 1; b < 7; b++)
                        {
                            tempWord += stringLine.charAt(i + b);
                        }
                        if (tempWord.equals("String ")) //If the word is String
                        {
                            System.out.println("Line " + (rowCounter + 1) + ": " + (wordCount+1) + " keyword: " + tempWord);
                            i += 5;
                            runningWord = "";
                            wordCount =  i+1;
                            continue;
                        }
                        else //If the word is not String
                        {
                             
                            continue;
                        }
                    }
                    if (stringLine.charAt(i) == 34)
                    {
                        if (maybeConst)
                        {
                            System.out.println("Line " + (rowCounter + 1) + ": " + (wordCount+1) + " String Constant: " + runningWord);
                            runningWord = "";
                             
                            wordCount =  i+1;
                            continue;
                        }
                        else
                        {
                            maybeConst = true;
                            continue;
                        }
                    }
                    else
                    {
                        continue;
                    }
                }
                if (stringLine.charAt(i) >= 97 && stringLine.charAt(i) <= 122) //Check for lowercase, int/double keyword, int/double constant
                {
                    if (runningWord.length() == 1 && stringLine.charAt(i) == 105) //Begins check for int keyword
                    {
                        String tempWord = runningWord;
                        for (int b = 1; b < 4; b++)
                        {
                            tempWord += stringLine.charAt(i + b);
                        }
                        if (tempWord.equals("int ")) //If the word is String
                        {
                            System.out.println("Line " + (rowCounter + 1) + ": " + (wordCount+1) + " keyword: " + tempWord);
                            i += 3;
                            runningWord = "";
                            wordCount =  i+1;
                            continue;
                        }
                        else //If the word is not int
                        {
                             
                            continue;
                        }
                    }
                    if (runningWord.length() == 1 && stringLine.charAt(i) == 100) //Begins check for double keyword
                    {
                        String tempWord = runningWord;
                        for (int b = 1; b < 7; b++)
                        {
                            tempWord += stringLine.charAt(i + b);
                        }
                        if (tempWord.equals("double ")) //If the word is double
                        {
                            System.out.println("Line " + (rowCounter + 1) + ": " + (wordCount+1) + " keyword: " + tempWord);
                            i += 5;
                            runningWord = "";
                            wordCount =  i+1;
                            continue;
                        }
                        else //If the word is not double
                        {
                            continue;
                        }
                    }
                    else
                    {
                        continue;
                    }
                }
                if ((stringLine.charAt(i) >= 48 && stringLine.charAt(i) <= 57) || stringLine.charAt(i) == 46)
                {
                    continue;
                }
                else
                {
                    for (int b = 0; b < 9; b++) //Check for operator
                    {
                        if (stringLine.charAt(i) == operatorCase[b])
                        {
                            if ((runningWord.length()-1) >= 1) //Checks if there is an indentifer before hand
                            {
                                identiferCheck(runningWord.substring(0, (runningWord.length()-1)), rowCounter, wordCount);
                                runningWord = "";
                            }

                            System.out.println("Line " + (rowCounter + 1) + ": " +  (i + 1) + " operator: " + stringLine.charAt(i));
                             
                            errorCheck = false;
                            runningWord = "";
                            wordCount =  i+1;
                            isOperator = true;
                            break; //breaks out of loop if operator has been found
                        }  
                    }
                    if (!isOperator)
                    {
                        errorCheck = true;
                    }
                    if (errorCheck)
                    {
                        System.out.println("Line " + (rowCounter + 1) + ": " +  (i + 1) + " error: " + runningWord);
                        runningWord = "";
                        wordCount =  i+1;
                    }
                }
            }
            if (runningWord.length() > 0)
            {
                System.out.println("Line " + (rowCounter + 1) + ": " +  0 + " error: " + runningWord);
            }
            rowCounter++;
        }
    }

    public static void identiferCheck(String id, int rowCt, int wordCt)
    {
        boolean isDoubleConstant = false;
        boolean isIdentifer = false;
        boolean isIntConstant = true;
        boolean errorCheck = false;
        int markIndex = 0;

        for (int b = 0; b < id.length(); b++)
        {
            if ((id.charAt(b) >= 65 && id.charAt(b) <= 90) || (id.charAt(b) >= 97 && id.charAt(b) <= 122)) //Checks for identifer
            {
                if (isDoubleConstant) //Checks for a dot before the string character
                {
                    errorCheck = true;
                    isIdentifer = false;
                    isIntConstant = false;
                    isDoubleConstant = false;
                    markIndex = b;
                    break;
                }
                else 
                {
                    isIdentifer = true;
                    isIntConstant = false;
                }
            }
            if (id.charAt(b) == 46) //Checks for double constant
            {
                if (isIdentifer) //Checks for a dot before the string character
                {
                    errorCheck = true;
                    isIdentifer = false;
                    isIntConstant = false;
                    isDoubleConstant = false;
                    markIndex = b;
                    break;
                }
                else 
                {
                    isDoubleConstant = true;
                    isIntConstant = false;
                }
            }
        }
        if (isIdentifer)
        {
            System.out.println("Line " + (rowCt + 1) + ": " + (wordCt+1) + " identifer: " + id);
        }
        if (isDoubleConstant)
        {
            System.out.println("Line " + (rowCt + 1) + ": " + (wordCt+1) + " double constant: " + id);
        }
        if (isIntConstant)
        {
            System.out.println("Line " + (rowCt + 1) + ": " + (wordCt+1) + " int constant: " + id);
        }
        if (errorCheck)
        {
            System.out.println("Line " + (rowCt + 1) + ": " + (wordCt+1) + " error: " + id.substring(0, markIndex+1));
            if (id.length()-1 != markIndex)
            {
                identiferCheck(id.substring(markIndex + 1, id.length()), rowCt, (wordCt + id.substring(0, markIndex).length()));
            }
        }
    }
}