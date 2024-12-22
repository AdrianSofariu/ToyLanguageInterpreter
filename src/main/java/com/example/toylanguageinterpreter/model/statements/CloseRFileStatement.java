package com.example.toylanguageinterpreter.model.statements;

import com.example.toylanguageinterpreter.exceptions.ADTException;
import com.example.toylanguageinterpreter.exceptions.ExpressionException;
import com.example.toylanguageinterpreter.exceptions.KeyNotFoundException;
import com.example.toylanguageinterpreter.exceptions.StatementException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.expressions.IExpression;
import com.example.toylanguageinterpreter.model.state.PrgState;
import com.example.toylanguageinterpreter.model.types.IType;
import com.example.toylanguageinterpreter.model.types.StringType;
import com.example.toylanguageinterpreter.model.values.IValue;
import com.example.toylanguageinterpreter.model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement{

    IExpression exp;

    public CloseRFileStatement(IExpression exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {

        IValue result = exp.eval(state.getSymTable(), state.getHeap());

        //check if the expression evaluates to a string
        if(!result.getType().equals(new StringType())){
            throw new StatementException("File name is not a string");
        }

        //search for the file in the file table and get the bufferedReader
        if(!state.getFileTable().contains((StringValue) result)){
            throw new StatementException("File not opened");
        }

        //close the reader
        try{
            BufferedReader reader = state.getFileTable().get((StringValue) result);
            reader.close();
        }
        catch (IOException | KeyNotFoundException e){
            throw new StatementException(e.getMessage());
        }

        //remove the file from the file table
        state.getFileTable().remove((StringValue) result);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ADTException, ExpressionException {
        IType expType = exp.typeCheck(typeEnv);
        if(!expType.equals(new StringType())){
            throw new StatementException("File name is not a string");
        }
        return typeEnv;
    }

    public String toString(){
        return "closeRFile(" + exp + ")";
    }
}
