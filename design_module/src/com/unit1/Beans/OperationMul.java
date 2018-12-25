package com.unit1.Beans;

public class OperationMul extends Operation {
    @Override
    public double GetResult() {
        result = _numberA * _numberB;
        return result;
    }
}
