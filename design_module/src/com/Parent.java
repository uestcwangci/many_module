package com;

public class Parent implements Cloneable{
    public String name;



    public int age;

    public Parent() {
    }

    public Parent(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    protected Parent clone() throws CloneNotSupportedException {
        return (Parent) super.clone();
    }

    @Override
    public String toString() {
        return "Parent{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
