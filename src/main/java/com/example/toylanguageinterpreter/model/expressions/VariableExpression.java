package com.example.toylanguageinterpreter.model.expressions;

import com.example.toylanguageinterpreter.exceptions.ADTException;
import com.example.toylanguageinterpreter.exceptions.KeyNotFoundException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.adt.MyIHeap;
import com.example.toylanguageinterpreter.model.types.IType;
import com.example.toylanguageinterpreter.model.values.IValue;

public class VariableExpression implements IExpression {
    private String var;

    public VariableExpression(String var) {
        this.var = var;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws ADTException {
        return symTable.get(var);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(this.var);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws KeyNotFoundException {
        return typeEnv.get(var);
    }

    @Override
    public String toString() {
        return var;
    }
}
