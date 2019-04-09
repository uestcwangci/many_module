package com;

import com.Parent;

public class Test {
    public static void main(String[] args) {
        Parent p1 = new Parent("张三", 48);
//        p1.name = "李四";
//        p1.age = 37;
        try {
            Parent p2 = p1.clone();
//            Child c1 = (Child) p1.clone();
            p2.name = "李四";
            p2.age = 37;
//            c1.name = "didi";
//            c1.age = 18;
            System.out.println(p1.name);
            System.out.println(p1.age);
//            System.out.println(c1.name);
//            System.out.println(c1.age);

            System.out.println(p2.name);
            System.out.println(p2.age);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
