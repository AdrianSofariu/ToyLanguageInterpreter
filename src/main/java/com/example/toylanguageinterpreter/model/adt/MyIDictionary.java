package com.example.toylanguageinterpreter.model.adt;

import com.example.toylanguageinterpreter.exceptions.KeyNotFoundException;

import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K, V> {

    void insert(K key, V value);
    void remove(K key) throws KeyNotFoundException;
    boolean contains(K key);
    V get(K key) throws KeyNotFoundException;
    Set<K> getKeys();
    Map<K, V> getMap();
    MyIDictionary<K, V> deepCopy();
}
