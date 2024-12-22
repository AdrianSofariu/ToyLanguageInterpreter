package com.example.toylanguageinterpreter.model.types;

import com.example.toylanguageinterpreter.model.values.IValue;
import com.example.toylanguageinterpreter.model.values.StringValue;

public class StringType implements IType{

    public StringType() {
    }

    public String toString() {
        return "String";
    }

    @Override
    public boolean equals(IType type) {
        return type instanceof StringType;
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }
}
