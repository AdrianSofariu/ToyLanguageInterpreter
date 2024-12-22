package com.example.toylanguageinterpreter.model.expressions;

import com.example.toylanguageinterpreter.exceptions.ExpressionException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.adt.MyIHeap;
import com.example.toylanguageinterpreter.model.types.IType;
import com.example.toylanguageinterpreter.model.values.IValue;

public class ValueExpression implements IExpression {
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap){
        return value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(this.value);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }


}
