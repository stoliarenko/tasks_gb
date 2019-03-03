package ru.stoliarenkoas.gb.etc;

import ru.stoliarenkoas.gb.structures.MyStack;

public class ReverseStringOrder {

    public static String reverse(String original) {
        if (original.length() == 0) return original;
        StringBuilder sb = new StringBuilder();
        MyStack<Character> stack = new MyStack<Character>();

        for (int i = 0; i < original.length(); i++) {
            stack.push(original.charAt(i));
        }

        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.toString();
    }

}
