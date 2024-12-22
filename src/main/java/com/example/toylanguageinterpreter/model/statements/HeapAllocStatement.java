package com.example.toylanguageinterpreter.model.statements;

import com.example.toylanguageinterpreter.exceptions.ADTException;
import com.example.toylanguageinterpreter.exceptions.ExpressionException;
import com.example.toylanguageinterpreter.exceptions.StatementException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.expressions.IExpression;
import com.example.toylanguageinterpreter.model.state.PrgState;
import com.example.toylanguageinterpreter.model.types.IType;
import com.example.toylanguageinterpreter.model.types.RefType;
import com.example.toylanguageinterpreter.model.values.IValue;
import com.example.toylanguageinterpreter.model.values.RefValue;

public class HeapAllocStatement implements IStatement{

    String var;
    IExpression expression;

    public HeapAllocStatement(String var, IExpression expression){
        this.var = var;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        if(!state.getSymTable().contains(var)){
            throw new StatementException("Variable " + var + " is not defined");
        }

        IValue varValue = state.getSymTable().get(var);

        if(!(varValue.getType() instanceof RefType)){
            throw new StatementException("Variable " + var + " is not a reference");
        }

        IValue expValue = expression.eval(state.getSymTable(), state.getHeap());

        //cast varValue to RefValue and check that the locationType is the same with the expression type
        if(!((RefValue)varValue).getLocationType().equals(expValue.getType())){
            throw new StatementException("Expression value is not of the same type as the reference type");
        }

        int addr = state.getHeap().allocate(expValue);
        state.getSymTable().insert(var, new RefValue(addr, ((RefValue) varValue).getLocationType()));

        return null;
    }

    public String toString(){
        return "new(" + var + ", " + expression + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocStatement(var, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ADTException, ExpressionException {
        IType varType = typeEnv.get(var);
        IType expType = expression.typeCheck(typeEnv);

        if(varType.equals(new RefType(expType))){
            return typeEnv;
        }
        else{
            throw new StatementException("HeapAllocStatement: right hand side and left hand side have different types");
        }
    }
}
