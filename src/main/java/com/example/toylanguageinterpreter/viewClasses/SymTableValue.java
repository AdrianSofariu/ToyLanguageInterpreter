package com.example.toylanguageinterpreter.viewClasses;

import com.example.toylanguageinterpreter.model.values.IValue;

public class SymTableValue {

    public String name;
    public String value;

    public SymTableValue(String name, IValue value){
        this.name = name;
        this.value = value.toString();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
