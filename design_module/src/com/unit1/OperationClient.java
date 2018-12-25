package com.unit1;


import com.unit1.Beans.AbstractOperatin;

public class OperationClient {
    public static void main(String[] args) {
        AbstractOperatin oper = OperationFactory.createOperate("/");
        oper.numberA = 2;
        oper.numberB = 5;
        System.out.println(oper.GetResult());

    }
}

class AbstractOperatinAdd extends AbstractOperatin {
    @Override
    public double GetResult() {
        result = numberA + numberB;
        return result;
    }
}

class AbstractOperatinSub extends AbstractOperatin {
    @Override
    public double GetResult() {
        result = numberA - numberB;
        return result;
    }
}

class AbstractOperatinMul extends AbstractOperatin {
    @Override
    public double GetResult() {
        result = numberA * numberB;
        return result;
    }
}

class AbstractOperatinDiv extends AbstractOperatin {
    @Override
    public double GetResult() {
        if (numberB == 0.0){
            try {
                throw new Exception("除数不能为0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result = numberA / numberB;
        return result;

    }
}

class OperationFactory {
    public static AbstractOperatin createOperate(String operate) {
        AbstractOperatin oper = null;
        switch (operate) {
            case "+":
                oper = new AbstractOperatinAdd();
                break;
            case "-":
                oper = new AbstractOperatinSub();
                break;
            case "*":
                oper = new AbstractOperatinMul();
                break;
            case "/":
                oper = new AbstractOperatinDiv();
                break;
            default:
                break;
        }
        return oper;
    }
}
