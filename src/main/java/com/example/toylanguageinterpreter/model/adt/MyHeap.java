package com.example.toylanguageinterpreter.model.adt;

import com.example.toylanguageinterpreter.exceptions.HeapException;
import com.example.toylanguageinterpreter.model.values.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap{

    Map<Integer, IValue> map;
    int lastAddress = 0;

    public MyHeap(){
        map = new HashMap<>();
    }

    @Override
    public synchronized int allocate(IValue value) {
        lastAddress++;
        map.put(lastAddress, value);
        return lastAddress;
    }

    @Override
    public synchronized IValue getValue(int address) throws HeapException{
        IValue res;
        if((res = map.get(address)) != null)
            return res;
        else
            throw new HeapException("Address not found");
    }

    @Override
    public synchronized boolean contains(int address) {
        return map.containsKey(address);
    }

    @Override
    public void set(int address, IValue value) throws HeapException {
        if(map.containsKey(address))
            map.put(address, value);
        else
            throw new HeapException("Address not found");
    }

    @Override
    public synchronized Map<Integer, IValue> getContent() {
        return map;
    }

    @Override
    public synchronized void setContent(Map<Integer, IValue> newContent) {
        map = newContent;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        for(Integer key : map.keySet()){
            result.append(key).append(" -> ").append(map.get(key)).append("\n");
        }
        return result.toString();
    }
}
