package com.example.toylanguageinterpreter.model.garbageCollector;

import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.values.IValue;
import com.example.toylanguageinterpreter.model.values.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public class GarbageCollector implements IGarbageCollector{

    public GarbageCollector() {
    }

    //eliminate all addresses from the heap that are not referenced by other heap entries or by the symbol table
    public Map<Integer, IValue> collect(List<MyIDictionary<String, IValue>> symTables, Map<Integer, IValue> heap){

        Collection<IValue> symTableValues = symTableKeys(symTables);
        List<Integer> symTableAddresses = getAddrFromSymTable(symTableValues);
        List<Integer> indirectRefAddr = getIndirectlyReferencedAddresses(symTableAddresses, heap);
        return heap.entrySet().stream()
                .filter(e -> symTableAddresses.contains(e.getKey()) || indirectRefAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    //extract all values from the symbol tables
    private Collection<IValue> symTableKeys(List<MyIDictionary<String, IValue>> symTables){
        return symTables.stream()
                .map(MyIDictionary::getMap)
                .map(Map::values)
                .flatMap(Collection::stream)
                .toList();
    }

    //get all addresses from reference values in the symbol table
    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    //given a list of addresses, return a list of all other addresses that are indirectly referenced by the given addresses
    private List<Integer> getIndirectlyReferencedAddresses(List<Integer> symTableAddr, Map<Integer, IValue>heap){

        Set<Integer> visited = new HashSet<>();

        //go through all addresses in the sym table
        //for each of them with a RefValue as value go recursively
        //to the referenced addresses until the value is not a ref
        //add all these addresses to the set
        symTableAddr.forEach(addr -> {
            IValue val = heap.get(addr);
            while(val instanceof RefValue refVal){
                int refAddr = refVal.getAddr();
                visited.add(refAddr);
                val = heap.get(refAddr);
            }
        });
        return visited.stream().toList();
    }
}
