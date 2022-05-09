# U530 Assignments

These were assignments from my U530 Programming Language Structures course, which includes an LR Parser and Lexical Analyzer.
Both of these projects were programed in JAVA.

# The Lexical Analyzer was based on the following parameters: 

Input: a source code file, (.txt file). Ask user to input the file name.

Output: printing all recognized tokens

The source code accepted tokens include

Keywords:  int   double  String  (case sensitive)

operators:  =   (  )  +  -  *  /  ,  ;

identifier:  letter(letter|digit)*

int constant

double constant

string constant: string constant is enclosed in a pair of "", such as "abc"

anything not recognized is detected as errors

# The LR Parser was based on the following rules:

Grammer Rules:
1. E -> E + T
2. E -> T
3. T -> T * F
4. T -> F
5. F -> (E)
6. F - > id

![image](https://user-images.githubusercontent.com/77416208/167413691-174bd4fb-da22-433f-814a-3aa67bdccf3d.png)
