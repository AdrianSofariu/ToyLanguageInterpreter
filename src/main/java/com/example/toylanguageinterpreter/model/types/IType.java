package com.example.toylanguageinterpreter.model.types;

import com.example.toylanguageinterpreter.model.values.IValue;

public interface IType {

    boolean equals(IType type);
    IValue defaultValue();
}
