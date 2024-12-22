package com.example.toylanguageinterpreter.model.statements;

import com.example.toylanguageinterpreter.exceptions.ADTException;
import com.example.toylanguageinterpreter.exceptions.ExpressionException;
import com.example.toylanguageinterpreter.exceptions.StatementException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.expressions.IExpression;
import com.example.toylanguageinterpreter.model.state.PrgState;
import com.example.toylanguageinterpreter.model.types.IType;
import com.example.toylanguageinterpreter.model.values.IValue;

public class AssignStatement implements IStatement {
    private String variableName;
    private IExpression expression;

    public AssignStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {

        //check if the variable already exists in the symbol table
        if(!state.getSymTable().contains(variableName)){
            throw new StatementException("Variable not found");
        }

        //if it does, get its value
        IValue value = state.getSymTable().get(variableName);

        //evaluate the new value
        IValue expValue = expression.eval(state.getSymTable(), state.getHeap());

        //check if the types of the 2 values are compatible
        if(!value.getType().equals(expValue.getType())){
            throw new StatementException("Value type mismatch");
        }

        //update the variable in the symbol table
        state.getSymTable().insert(variableName, expValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(variableName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ADTException, ExpressionException {
        IType typeVar = typeEnv.get(variableName);
        IType typeExp = expression.typeCheck(typeEnv);
        if(!typeVar.equals(typeExp)){
            throw new StatementException("Assignment: right hand side and left hand side have different types");
        }
        return typeEnv;
    }

    public String toString() {
        return variableName + " = " + expression;
    }
}
