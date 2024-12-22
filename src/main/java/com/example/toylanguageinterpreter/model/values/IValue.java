package com.example.toylanguageinterpreter.model.values;

import com.example.toylanguageinterpreter.model.types.IType;

public interface IValue {

    IType getType();
    String toString();
}
