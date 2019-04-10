package com;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        final List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
//        list.remove(2);
        list.set(2, 8);
        System.out.println(list);
    }
}
