package jingxuan100;

import java.util.Stack;

public class Easy2 {
    public static void main(String[] args) {
        Easy2 test = new Easy2();
        System.out.println(test.isValid("({})"));
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (!stack.isEmpty()) {
                char left = stack.pop();
                if (!isMatch(left, c)) {
                    return false;
                }
            } else {
                return false;
            }

        }
        if (stack.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isMatch(char left, char right) {
        if ((left == '(' && right == ')') || (left == '[' && right == ']')
                || (left == '{' && right == '}')) {
            return true;
        }
        return false;
    }
}
