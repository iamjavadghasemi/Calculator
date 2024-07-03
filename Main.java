import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List list = new List();
        while(true) {
            System.out.println("Enter an expression or a value of variable>>>");
            String input = in.nextLine();
            if(!hasSpace(input)) {
                if(!hasCapital(input)) {
                    if(!input.equals("exit")) {
                        if(!input.equals("show")) {
                            if(hasEqual(input)) {
                                String[] string = input.split("=", 2);
                                if(!string[0].isEmpty()) {
                                    if(hasCorrectNameOfVariable(string[0])) {
                                        if(hasCorrectParentheses(string[1])) {
                                            if(hasCorrectOperation(string[1])) {
                                                try {
                                                    String convert = replace(string[1], list);
                                                    int result = evaluate(convert);
                                                    if(result >= 0) {
                                                        if(list.isExist(string[0])) {
                                                            list.find(string[0]).setValue(result);
                                                        } else {
                                                            Variable variable = new Variable();
                                                            variable.setName(string[0]);
                                                            variable.setValue(result);
                                                            list.add(variable);
                                                        }
                                                    } else {
                                                        throw new EmptyStackException();
                                                    }
                                                } catch(EmptyStackException e1) {
                                                    System.out.println("The variable must be more than zero!!!");
                                                }  catch(RuntimeException e) {
                                                    System.out.println(e.getMessage());
                                                }
                                            } else {
                                                System.out.println("The order of operations is incorrect!!!");
                                            }
                                        } else {
                                            System.out.println("The number of parentheses is incorrect!!!");
                                        }
                                    } else {
                                        System.out.println("The name of variable is incorrect!!!");
                                    }
                                } else {
                                    System.out.println("You must enter the name of a variable before equal!!!");
                                }
                            } else {
                                if(hasCorrectParentheses(input)) {
                                    if(hasCorrectOperation(input)) {
                                        try {
                                            String convert = replace(input, list);
                                            int result = evaluate(convert);
                                            System.out.println(result);
                                        } catch(Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } else {
                                        System.out.println("The order of operations is incorrect!!!");
                                    }
                                } else {
                                    System.out.println("The number of parentheses is incorrect!!!");
                                }
                            }
                        } else {
                            list.showAll();
                        }
                    } else {
                        break;
                    }
                } else {
                    System.out.println("You can not enter uppercase letters!!!");
                }
            } else {
                System.out.println("You can not enter space!!!");
            }
        }
    }
    public static boolean hasEqual(String input) {
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == '=') {
                return true;
            }
        }
        return false;
    }
    public static boolean hasCorrectParentheses(String input) {
        MyStack stack = new MyStack();
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == '(') {
                stack.push();
            } else if(input.charAt(i) == ')') {
                try {
                    stack.pop();
                } catch(Exception exception) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
    public static boolean hasCapital(String input) {
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) >= 'A' && input.charAt(i) <= 'Z') {
                return true;
            }
        }
        return false;
    }
    public static boolean hasSpace(String input) {
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == ' ') {
                return true;
            }
        }
        return false;
    }
    public static boolean hasCorrectNameOfVariable(String input) {
        return input.length() == 1 && (input.charAt(0) < '0' || input.charAt(0) > '9');
    }
    public static boolean hasCorrectOperation(String input) {
        if(input.charAt(input.length() - 1) == '*' || input.charAt(input.length() - 1) == '/' || input.charAt(input.length() - 1) == '+' || input.charAt(input.length() - 1) == '-') {
            return false;
        }
        for(int i = 0; i < input.length() - 1; i++) {
            if(input.charAt(i) == '*' || input.charAt(i) == '/' || input.charAt(i) == '+' || input.charAt(i) == '-') {
                if(input.charAt(i + 1) == '*' || input.charAt(i + 1) == '/' || input.charAt(i + 1) == '+' || input.charAt(i + 1) == '-') {
                    return false;
                }
            }
        }
        return true;
    }
    public static String replace(String input, List list) throws RuntimeException {
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) >= 'a' && input.charAt(i) <= 'z') {
                String name = String.valueOf(input.charAt(i));
                if(list.isExist(name)) {
                    String value = String.valueOf(list.find(name).getValue());
                    input = input.replaceAll(name, value);
                } else {
                    throw new RuntimeException("The variables are not defined!!!");
                }
            }
        }
        return input;
    }
    public static int evaluate(String expression) {
        char[] tokens = expression.toCharArray();
        Stack<Integer> values = new Stack<Integer>();
        Stack<Character> ops = new Stack<Character>();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuffer sbuf = new StringBuffer();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
                i--;
            } else if (tokens[i] == '(')
                ops.push(tokens[i]);
            else if (tokens[i] == ')') {
                while (ops.peek() != '(') {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            } else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/')	{
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(tokens[i]);
            }
        }
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }
    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }
    public static int applyOp(char op, int b, int a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
}