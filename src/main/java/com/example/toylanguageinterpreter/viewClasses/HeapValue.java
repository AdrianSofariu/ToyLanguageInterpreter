package com.example.toylanguageinterpreter.viewClasses;

import com.example.toylanguageinterpreter.model.values.IValue;

public class HeapValue {

    private String address;
    private String value;

    public HeapValue(int addr, IValue val) {
        this.address = Integer.toString(addr);
        this.value = val.toString();
    }

    public String getAddress() {
        return address;
    }

    public String getValue() {
        return value;
    }
}
