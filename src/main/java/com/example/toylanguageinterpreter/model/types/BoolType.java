package com.example.toylanguageinterpreter.model.types;

import com.example.toylanguageinterpreter.model.values.BoolValue;
import com.example.toylanguageinterpreter.model.values.IValue;

public class BoolType implements IType{


    @Override
    public boolean equals(IType type) {
        return type instanceof BoolType;
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    public String toString(){
        return "bool";
    }
}
