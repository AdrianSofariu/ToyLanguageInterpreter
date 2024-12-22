package com.example.toylanguageinterpreter.model.values;

import com.example.toylanguageinterpreter.model.types.IType;
import com.example.toylanguageinterpreter.model.types.RefType;

public class RefValue implements IValue{

    int addr;
    IType locationType;

    public RefValue(int addr, IType locationType){
        this.addr = addr;
        this.locationType = locationType;
    }

    public int getAddr(){
        return addr;
    }

    public IType getLocationType(){
        return locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    public String toString(){
        return "(" + addr + ", " + locationType + ")";
    }

    public boolean equals(IValue other) {
        return other instanceof RefValue && ((RefValue) other).getLocationType().equals(locationType) && ((RefValue) other).getAddr() == addr;
    }

}
