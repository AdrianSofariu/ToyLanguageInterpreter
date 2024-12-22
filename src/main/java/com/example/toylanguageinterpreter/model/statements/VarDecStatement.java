package com.example.toylanguageinterpreter.model.statements;

import com.example.toylanguageinterpreter.exceptions.ADTException;
import com.example.toylanguageinterpreter.exceptions.ExpressionException;
import com.example.toylanguageinterpreter.exceptions.StatementException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.state.PrgState;
import com.example.toylanguageinterpreter.model.types.IType;

public class VarDecStatement implements IStatement{

    String variableName;
    IType typ;

    public VarDecStatement(String variableName, IType typ) {
        this.variableName = variableName;
        this.typ = typ;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        state.getSymTable().insert(variableName, typ.defaultValue());
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDecStatement(variableName, typ);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ADTException, ExpressionException {
        typeEnv.insert(variableName, typ);
        return typeEnv;
    }

    public String toString() {
        return typ + " " + variableName;
    }
}
