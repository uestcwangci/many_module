package com.unit1.Beans;

public class OperationDiv extends Operation {
    @Override
    public double GetResult() {
        if (_numberB == 0.0){
            try {
                throw new Exception("除数不能为0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result = _numberA / _numberB;
        return result;

    }
}
