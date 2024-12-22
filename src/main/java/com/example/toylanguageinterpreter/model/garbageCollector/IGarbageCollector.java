package com.example.toylanguageinterpreter.model.garbageCollector;


import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.values.IValue;


import java.util.List;
import java.util.Map;

public interface IGarbageCollector {
    Map<Integer, IValue> collect(List<MyIDictionary<String, IValue>> symTables, Map<Integer, IValue> heap);
}
