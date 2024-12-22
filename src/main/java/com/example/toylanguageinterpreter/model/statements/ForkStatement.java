package com.example.toylanguageinterpreter.model.statements;

import com.example.toylanguageinterpreter.exceptions.ADTException;
import com.example.toylanguageinterpreter.exceptions.ExpressionException;
import com.example.toylanguageinterpreter.exceptions.HeapException;
import com.example.toylanguageinterpreter.exceptions.StatementException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.adt.MyStack;
import com.example.toylanguageinterpreter.model.state.PrgState;
import com.example.toylanguageinterpreter.model.types.IType;


public class ForkStatement implements IStatement{

    IStatement statement;

    public ForkStatement(IStatement statement){
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException, HeapException {
        return new PrgState(state.getOutputList(), state.getSymTable().deepCopy(), state.getFileTable(),
                new MyStack<IStatement>(), state.getHeap(), statement);
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ADTException, ExpressionException {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    public String toString(){
        return "fork(" + statement + ")";
    }
}
