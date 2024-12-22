package com.example.toylanguageinterpreter.model.expressions;

import com.example.toylanguageinterpreter.exceptions.ADTException;
import com.example.toylanguageinterpreter.exceptions.ExpressionException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.adt.MyIHeap;
import com.example.toylanguageinterpreter.model.types.BoolType;
import com.example.toylanguageinterpreter.model.types.IType;
import com.example.toylanguageinterpreter.model.values.BoolValue;
import com.example.toylanguageinterpreter.model.values.IValue;

public class LogicExpression implements IExpression {

    private IExpression left;
    private IExpression right;
    private LogicalOperator operator;

    public LogicExpression(IExpression left, LogicalOperator operator, IExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }


    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws ADTException, ExpressionException {

        //evaluate both sides of the expression
        IValue evaluatedLeft = left.eval(symTable, heap);
        IValue evaluatedRight = right.eval(symTable, heap);

        //check for both sides to be boolean
        if(!evaluatedLeft.getType().equals(new BoolType())){
            throw new ExpressionException("Left expression is not of bool type");
        }
        if(!evaluatedRight.getType().equals(new BoolType())){
            throw new ExpressionException("Right expression is not of bool type");
        }

        //compute the result of the logical operation depending on the operator
        switch(operator){
            case AND:
                return new BoolValue(((BoolValue)evaluatedLeft).getValue() && ((BoolValue)evaluatedRight).getValue());
            case OR:
                return new BoolValue(((BoolValue)evaluatedLeft).getValue() || ((BoolValue)evaluatedRight).getValue());
            default:
                throw new ExpressionException("Unknown operator");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new LogicExpression(left.deepCopy(), operator, right.deepCopy());
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, ADTException {
        IType t1, t2;
        t1 = left.typeCheck(typeEnv);
        t2 = right.typeCheck(typeEnv);
        if(t1.equals(new BoolType())){
            if(t2.equals(new BoolType())){
                return new BoolType();
            }
            else{
                throw new ExpressionException("second operand is not a boolean");
            }
        }
        else{
            throw new ExpressionException("first operand is not an boolean");
        }
    }

    public String toString(){
        return left + " " + operator + " " + right;
    }
}
