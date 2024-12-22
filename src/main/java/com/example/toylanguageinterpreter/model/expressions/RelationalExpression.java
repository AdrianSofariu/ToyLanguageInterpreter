package com.example.toylanguageinterpreter.model.expressions;

import com.example.toylanguageinterpreter.exceptions.ADTException;
import com.example.toylanguageinterpreter.exceptions.ExpressionException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.adt.MyIHeap;
import com.example.toylanguageinterpreter.model.types.BoolType;
import com.example.toylanguageinterpreter.model.types.IType;
import com.example.toylanguageinterpreter.model.types.IntType;
import com.example.toylanguageinterpreter.model.values.BoolValue;
import com.example.toylanguageinterpreter.model.values.IValue;
import com.example.toylanguageinterpreter.model.values.IntValue;

public class RelationalExpression implements IExpression{

    private IExpression left;
    private IExpression right;
    private ComparisonOperator operator;

    public RelationalExpression(IExpression left, ComparisonOperator operator, IExpression right){
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws ADTException, ExpressionException {

        //evaluate both sides of the expression
        IValue leftVal = left.eval(symTable, heap);
        IValue rightVal = right.eval(symTable, heap);

        //check if they are both numeric values
        if(!leftVal.getType().equals(new IntType())){
            throw new ExpressionException("left expression is not a numeric value");
        }
        if(!rightVal.getType().equals(new IntType())){
            throw new ExpressionException("right expression is not a numeric value");
        }
        IntValue leftIntVal = (IntValue) leftVal;
        IntValue rightIntVal = (IntValue) rightVal;

        //compute the relational operation
        boolean result;
        switch (operator){
            case LESS:
                result = leftIntVal.getValue() < rightIntVal.getValue();
                break;
            case LESS_OR_EQUAL:
                result = leftIntVal.getValue() <= rightIntVal.getValue();
                break;
            case EQUAL:
                result = leftIntVal.getValue() == rightIntVal.getValue();
                break;
            case NOT_EQUAL:
                result = leftIntVal.getValue() != rightIntVal.getValue();
                break;
            case GREATER:
                result = leftIntVal.getValue() > rightIntVal.getValue();
                break;
            case GREATER_OR_EQUAL:
                result = leftIntVal.getValue() >= rightIntVal.getValue();
                break;
            default:
                throw new ExpressionException("operator not recognized");
        }

        return new BoolValue(result);
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(left.deepCopy(), operator, right.deepCopy());
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, ADTException {
        IType t1, t2;
        t1 = left.typeCheck(typeEnv);
        t2 = right.typeCheck(typeEnv);
        if(t1.equals(new IntType())){
            if(t2.equals(new IntType())){
                return new BoolType();
            }
            else{
                throw new ExpressionException("second operand is not an integer");
            }
        }
        else{
            throw new ExpressionException("first operand is not an integer");
        }
    }

    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }
}
