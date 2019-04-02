package leetcode148;

import java.util.Stack;

/**
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 *
 * Valid operators are+,-,*,/. Each operand may be an integer or another expression.
 *
 * Some examples:
 *
 *   ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
 *   ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 */
public class Demo2 {
    public static void main(String[] args) {
        Demo2 test = new Demo2();
        System.out.println(test.evalRPN(new String[]{"2", "1", "+", "3", "*"}));
    }

    public int evalRPN(String[] tokens) {
        int n = tokens.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1){
            return Integer.parseInt(tokens[0]);
        }
        int result = 0;
        Stack<Integer> S = new Stack<>();
        S.push(Integer.valueOf(tokens[0]));
        int i = 1;
        while (!S.isEmpty() && i < n) {
            switch (tokens[i]) {
                case "+":
                    result = S.pop() + S.pop();
                    S.push(result);
                    break;
                case "-":
                    result = -S.pop() + S.pop();
                    S.push(result);
                    break;
                case "*":
                    result = S.pop() * S.pop();
                    S.push(result);
                    break;
                case "/":
                    result = (int) (1.0 / S.pop() * S.pop());
                    S.push(result);
                    break;
                default:
                    S.push(Integer.parseInt(tokens[i]));
                    break;
            }
            i++;
        }
        return result;
    }
}
