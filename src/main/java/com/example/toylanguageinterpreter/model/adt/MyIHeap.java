package com.example.toylanguageinterpreter.model.adt;

import com.example.toylanguageinterpreter.exceptions.HeapException;
import com.example.toylanguageinterpreter.model.values.IValue;

import java.util.Map;

public interface MyIHeap {

    int allocate(IValue value);
    IValue getValue(int address) throws HeapException;
    boolean contains(int address);
    void set(int address, IValue value) throws HeapException;
    Map<Integer, IValue> getContent();
    void setContent(Map<Integer, IValue> newContent);
}
