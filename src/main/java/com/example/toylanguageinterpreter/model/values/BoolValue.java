package com.example.toylanguageinterpreter.model.values;

import com.example.toylanguageinterpreter.model.types.BoolType;
import com.example.toylanguageinterpreter.model.types.IType;

public class BoolValue implements IValue{

    private boolean value;

    public BoolValue(boolean value){
        this.value = value;
    }

    public boolean getValue(){
        return value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    public boolean equals(IValue other){
        return other instanceof BoolValue && this.value == ((BoolValue) other).value;
    }

    public String toString(){
        return Boolean.toString(value);
    }
}
