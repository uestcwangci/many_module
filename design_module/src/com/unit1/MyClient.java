package com.unit1;


import com.unit1.Beans.Operation;
import com.unit1.Beans.OperationAdd;
import com.unit1.Beans.OperationFactory;

public class MyClient {
    public static void main(String[] args) {
        Operation oper = OperationFactory.createOperate("/");
        oper._numberA = 2;
        oper._numberB = 0;
        System.out.println(oper.GetResult());

    }
}
