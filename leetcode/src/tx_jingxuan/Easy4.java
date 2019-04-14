package tx_jingxuan;

import java.util.Stack;

public class Easy4 {
    public static void main(String[] args) {
        Easy4 test = new Easy4();
        System.out.println(test.isValid("()[]{}"));


    }
    public boolean isValid(String s) {
        if (s.isEmpty()) {
            return true;
        }
        if (s.length() % 2 == 1) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        char[] charArr = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (isLeft(charArr[i])) {
                stack.push(charArr[i]);
            } else {
                if (stack.isEmpty()) {
                    return false;
                } else {
                    if (!isMatch(stack.pop(), charArr[i])) {
                        return false;
                    }
                }
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isMatch(char pop, char c) {
        if ((pop == '(' && c == ')') || (pop == '[' && c == ']') || (pop == '{' && c == '}')) {
            return true;
        }
        return false;
    }

    private boolean isLeft(char c) {
        if (c == '(' || c == '[' || c == '{') {
            return true;
        }
        return false;
    }
}
