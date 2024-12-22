package com.example.toylanguageinterpreter.model.types;

import com.example.toylanguageinterpreter.model.values.IValue;
import com.example.toylanguageinterpreter.model.values.RefValue;

public class RefType implements IType{

    IType inner;

    public RefType(IType inner){
        this.inner = inner;
    }

    public IType getInner(){
        return inner;
    }

    @Override
    public boolean equals(IType type) {
        return type instanceof RefType && inner.equals(((RefType)type).getInner());
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    public String toString(){
        return "Ref(" + inner.toString() + ")";
    }
}
