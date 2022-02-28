import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LRParserProject2TallonMcAbee
{
    public static void main(String [] args) throws IOException
    {   
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Input a LR Parser sentence (With no spaces between characters, EX: id+id$): ");
        String userSentence = userInput.readLine();

        LRParser(userSentence);
    }

    public static void LRParser(String input)
    {
        String currentInput = input;
        String currentStack = "0";

        String[] grammarOperators = {"E","T","F","id"};

        String[][] actionTable = {
            {"s5","na","na","s4","na","na"},
            {"na","s6","na","na","na","acc"},
            {"na","r2","s7","na","r2","r2"},
            {"na","r4","r4","na","r4","r4"},
            {"s5","na","na","s4","na","na"},
            {"na","r6","r6","na","r6","r6"},
            {"s5","na","na","s4","na","na"},
            {"s5","na","na","s4","na","na"},
            {"na","s6","na","na","s11","na"},
            {"na","r1","s7","na","r1","r1"},
            {"na","r3","r3","na","r3","r3"},
            {"na","r5","r5","na","r5","r5"}
        };

        String[][] gotoTable = {
            {"1","2","3"},
            {"na","na","na"},
            {"na","na","na"},
            {"na","na","na"},
            {"8","2","3"},
            {"na","na","na"},
            {"na","9","3"},
            {"na","na","10"},
            {"na","na","na"},
            {"na","na","na"},
            {"na","na","na"},
            {"na","na","na"}
        };

        String[] inputSplit = input.split("");
        boolean isDoubleDigit = false;

        System.out.println("Stack || Input");
        System.out.println("0 || " + currentInput);

        int i = 0;
        while (i < inputSplit.length)
        {
            String[] stackSplit = currentStack.split("");
            String finalStackPiece = stackSplit[stackSplit.length-1];
            StringBuffer stringBuff = new StringBuffer();
            
            //check for number case (0,1,2,3,etc), action table
            if (finalStackPiece.charAt(0) >= 48 && finalStackPiece.charAt(0) <= 57)
            {
                int stateIndex = Integer.parseInt(finalStackPiece);

                if (isDoubleDigit)
                {
                    stateIndex = Integer.parseInt(stackSplit[stackSplit.length-2] + finalStackPiece);
                    isDoubleDigit = false;
                }

                int actionIndex = -1;
                
                if (inputSplit[i].equals("i"))
                {
                   actionIndex = 0;
                }
                if (inputSplit[i].equals("+"))
                {
                    actionIndex = 1;
                }
                if (inputSplit[i].equals("*"))
                {
                    actionIndex = 2;
                }
                if (inputSplit[i].equals("("))
                {
                    actionIndex = 3;
                }
                if (inputSplit[i].equals(")"))
                {   
                    actionIndex = 4;
                }
                if (inputSplit[i].equals("$"))
                {
                    actionIndex = 5;
                }

                if(actionTable[stateIndex][actionIndex].equals("na") || actionIndex == -1)
                {
                    System.out.println("Grammar Error || Does not follow rules");
                    break;
                }
                else if(actionTable[stateIndex][actionIndex].equals("acc"))
                {
                    System.out.println(currentStack + " || acc");
                    break;
                }
                else
                {
                    String[] actionSplit = actionTable[stateIndex][actionIndex].split("");
                    if (actionSplit[0].equals("r"))
                    {
                        stackSplit = reduceOperation(actionSplit, stackSplit);

                        for (int b = 0; b < stackSplit.length; b++)
                        {
                            stringBuff.append(stackSplit[b]);
                        }
                        currentStack = stringBuff.toString();

                        stringBuff.delete(0, stringBuff.length());

                        for (int b = 0; b < inputSplit.length; b++)
                        {
                            stringBuff.append(inputSplit[b]);
                        }
                        currentInput = stringBuff.toString();

                        stringBuff.delete(0, stringBuff.length());

                        // System.out.println(currentStack + " || " + currentInput);

                        continue;
                    }
                    else
                    {
                        if (inputSplit[i].equals("i"))
                        {
                            String[] tempArray = new String[stackSplit.length+3];

                            for (int b = 0; b < stackSplit.length; b++)
                            {
                                tempArray[b] = stackSplit[b];
                            }

                            stackSplit = tempArray;

                            stackSplit[stackSplit.length-3] = inputSplit[i];
                            stackSplit[stackSplit.length-2] = inputSplit[i+1];
                            stackSplit[stackSplit.length-1] = actionSplit[1];

                            inputSplit = Arrays.copyOfRange(inputSplit, i+2, inputSplit.length);

                            for (int b = 0; b < stackSplit.length; b++)
                            {
                                stringBuff.append(stackSplit[b]);
                            }
                            currentStack = stringBuff.toString();

                            stringBuff.delete(0, stringBuff.length());

                            for (int b = 0; b < inputSplit.length; b++)
                            {
                                stringBuff.append(inputSplit[b]);
                            }
                            currentInput = stringBuff.toString();

                            stringBuff.delete(0, stringBuff.length());

                            System.out.println(currentStack + " || " + currentInput);

                            continue;
                        }
                        else 
                        {
                            if (actionSplit.length > 2)
                            {
                                String[] tempArray = new String[stackSplit.length+3];

                                for (int b = 0; b < stackSplit.length; b++)
                                {
                                    tempArray[b] = stackSplit[b];
                                }

                                stackSplit = tempArray;

                                stackSplit[stackSplit.length-3] = inputSplit[i];
                                stackSplit[stackSplit.length-2] = actionSplit[1];
                                stackSplit[stackSplit.length-1] = actionSplit[2];

                                inputSplit = Arrays.copyOfRange(inputSplit, i+1, inputSplit.length);

                                for (int b = 0; b < stackSplit.length; b++)
                                {
                                    stringBuff.append(stackSplit[b]);
                                }
                                currentStack = stringBuff.toString();

                                stringBuff.delete(0, stringBuff.length());

                                for (int b = 0; b < inputSplit.length; b++)
                                {
                                    stringBuff.append(inputSplit[b]);
                                }
                                currentInput = stringBuff.toString();

                                stringBuff.delete(0, stringBuff.length());

                                System.out.println(currentStack + " || " + currentInput);

                                isDoubleDigit = true;

                                continue;
                            }
                            else 
                            {
                                String[] tempArray = new String[stackSplit.length+2];

                                for (int b = 0; b < stackSplit.length; b++)
                                {
                                    tempArray[b] = stackSplit[b];
                                }
    
                                stackSplit = tempArray;
    
                                stackSplit[stackSplit.length-2] = inputSplit[i];
                                
                                stackSplit[stackSplit.length-1] = actionSplit[1];
    
                                inputSplit = Arrays.copyOfRange(inputSplit, i+1, inputSplit.length);
    
                                for (int b = 0; b < stackSplit.length; b++)
                                {
                                    stringBuff.append(stackSplit[b]);
                                }
                                currentStack = stringBuff.toString();
    
                                stringBuff.delete(0, stringBuff.length());
    
                                for (int b = 0; b < inputSplit.length; b++)
                                {
                                    stringBuff.append(inputSplit[b]);
                                }
                                currentInput = stringBuff.toString();
    
                                stringBuff.delete(0, stringBuff.length());
    
                                System.out.println(currentStack + " || " + currentInput);
    
                                continue;
                            }
                        }
                    }
                }
            }
            else //check for variable case (E,T,F,id), goTo table
            {
                int actionIndex = -1;
                int stateIndex = -1;

                if (finalStackPiece.equals("E") || finalStackPiece.equals("T") || finalStackPiece.equals("F"))
                {
                    if (finalStackPiece.equals("E"))
                    {
                        actionIndex = 0;
                    }
                    if (finalStackPiece.equals("T"))
                    {
                        actionIndex = 1;
                    }
                    if (finalStackPiece.equals("F"))
                    {
                        actionIndex = 2;
                    }

                    stateIndex = Integer.parseInt(stackSplit[stackSplit.length-2]);

                    if(gotoTable[stateIndex][actionIndex].equals("na") || actionIndex == -1)
                    {
                        System.out.println("Grammar Error || Does not follow rules");
                        break;
                    }
                    else
                    {
                        if (gotoTable[stateIndex][actionIndex].length() == 2)
                        {
                            String[] tempArray = new String[stackSplit.length+1];

                            for (int b = 0; b < stackSplit.length; b++)
                            {
                                tempArray[b] = stackSplit[b];
                            }

                            stackSplit = tempArray;

                            stackSplit[stackSplit.length-1] = gotoTable[stateIndex][actionIndex];

                            for (int b = 0; b < stackSplit.length; b++)
                            {
                                stringBuff.append(stackSplit[b]);
                            }
                            currentStack = stringBuff.toString();

                            stringBuff.delete(0, stringBuff.length());

                            System.out.println(currentStack + " || " + currentInput);

                            isDoubleDigit = true;

                            continue;
                        }
                        else 
                        {
                            String[] tempArray = new String[stackSplit.length+1];

                            for (int b = 0; b < stackSplit.length; b++)
                            {
                                tempArray[b] = stackSplit[b];
                            }

                            stackSplit = tempArray;

                            stackSplit[stackSplit.length-1] = gotoTable[stateIndex][actionIndex];

                            for (int b = 0; b < stackSplit.length; b++)
                            {
                                stringBuff.append(stackSplit[b]);
                            }
                            currentStack = stringBuff.toString();

                            stringBuff.delete(0, stringBuff.length());

                            System.out.println(currentStack + " || " + currentInput);

                            continue;
                        }
                    }
                }
                else
                {
                    System.out.println("Grammar Error || Does not follow rules");
                    break;
                }
            }
            
        }


    }

    public static String[] reduceOperation(String[] actionSplit, String[] inputSplit)
    {
        int breakIndex = 5000;
        if (actionSplit[1].equals("1"))
        {
            for (int b = inputSplit.length-1; b >= 0; b--)
            {
                if (inputSplit[b].equals("E"))
                {
                    breakIndex = b;
                    break;
                }
            }
            inputSplit = Arrays.copyOfRange(inputSplit, 0, breakIndex + 1);
            
            return inputSplit;
        }
        else if (actionSplit[1].equals("2"))
        {
            for (int b = inputSplit.length-1; b >= 0; b--)
            {
                if (inputSplit[b].equals("T"))
                {
                    breakIndex = b;
                    break;
                }
            }
            inputSplit = Arrays.copyOfRange(inputSplit, 0, breakIndex + 1);
            inputSplit[breakIndex] = "E";

            return inputSplit;
        }
        else if (actionSplit[1].equals("3"))
        {
            for (int b = inputSplit.length-1; b >= 0; b--)
            {
                if (inputSplit[b].equals("T"))
                {
                    breakIndex = b;
                    break;
                }
            }
            inputSplit = Arrays.copyOfRange(inputSplit, 0, breakIndex + 1);

            return inputSplit;
        }
        else if (actionSplit[1].equals("4"))
        {
            for (int b = inputSplit.length-1; b >= 0; b--)
            {
                if (inputSplit[b].equals("F"))
                {
                    breakIndex = b;
                    break;
                }
            }
            inputSplit = Arrays.copyOfRange(inputSplit, 0, breakIndex + 1);
            inputSplit[breakIndex] = "T";

            return inputSplit;
        }
        else if (actionSplit[1].equals("5"))
        {
            for (int b = inputSplit.length-1; b >= 0; b--)
            {
                if (inputSplit[b].equals("("))
                {
                    breakIndex = b;
                    break;
                }
            }
            inputSplit = Arrays.copyOfRange(inputSplit, 0, breakIndex + 1);
            inputSplit[breakIndex] = "F";

            return inputSplit;
        }
        else 
        {
            for (int b = inputSplit.length-1; b >= 0; b--)
            {
                if (inputSplit[b].equals("i"))
                {
                    breakIndex = b;
                    break;
                }
            }
            inputSplit = Arrays.copyOfRange(inputSplit, 0, breakIndex + 1);
            inputSplit[breakIndex] = "F";

            return inputSplit;
        }
    }
}