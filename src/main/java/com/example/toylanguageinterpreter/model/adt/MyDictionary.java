package com.example.toylanguageinterpreter.model.adt;

import com.example.toylanguageinterpreter.exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K,V> implements MyIDictionary<K,V>{

    Map<K,V> map;

    public MyDictionary() {
        map = new HashMap<>();
    }

    public MyDictionary(Map<K,V> map) {
        this.map = new HashMap<>(map);
    }
    
    @Override
    public synchronized void insert(K key, V value) {
        map.put(key, value);
    }

    @Override
    public synchronized void remove(K key) throws KeyNotFoundException {
        if(map.containsKey(key))
            map.remove(key);
        else
            throw new KeyNotFoundException("Key not found");
    }

    @Override
    public synchronized boolean contains(K key) {
        return map.containsKey(key);
    }

    @Override
    public synchronized V get(K key) throws KeyNotFoundException {
        if(map.containsKey(key))
            return map.get(key);
        else
            throw new KeyNotFoundException("Key not found");
    }

    @Override
    public synchronized Set<K> getKeys() {
        return map.keySet();
    }

    @Override
    public Map<K, V> getMap() {
        return map;
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        return new MyDictionary<K, V>(map);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for(K key : map.keySet())
            str.append(key).append(" -> ").append(map.get(key)).append("\n");
        return str.toString();
    }


}
