package com.example.toylanguageinterpreter.model.values;

import com.example.toylanguageinterpreter.model.types.IType;
import com.example.toylanguageinterpreter.model.types.StringType;

public class StringValue implements IValue{

    private String val;

    public StringValue(String val){
        this.val = val;
    }

    public String getVal(){
        return val;
    }

    public void setValue(String value){
        this.val = value;
    }

    public String toString(){
        return val;
    }


    @Override
    public IType getType() {
        return new StringType();
    }

    public boolean equals(IValue other){
        return other instanceof StringValue && this.val.equals(((StringValue) other).val);
    }
}
