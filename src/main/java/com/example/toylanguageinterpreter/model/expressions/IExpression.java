package com.example.toylanguageinterpreter.model.expressions;

import com.example.toylanguageinterpreter.exceptions.ADTException;
import com.example.toylanguageinterpreter.exceptions.ExpressionException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.adt.MyIHeap;
import com.example.toylanguageinterpreter.model.types.IType;
import com.example.toylanguageinterpreter.model.values.IValue;

public interface IExpression {
    IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws ADTException, ExpressionException;
    IExpression deepCopy();
    IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, ADTException;
}
