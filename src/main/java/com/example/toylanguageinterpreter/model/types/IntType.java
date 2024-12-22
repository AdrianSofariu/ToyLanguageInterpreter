package com.example.toylanguageinterpreter.model.types;

import com.example.toylanguageinterpreter.model.values.IValue;
import com.example.toylanguageinterpreter.model.values.IntValue;

public class IntType implements IType{

    @Override
    public boolean equals(IType type) {
        return type instanceof IntType;
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    public String toString(){
        return "int";
    }
}
