package com.example.toylanguageinterpreter.model.adt;

import com.example.toylanguageinterpreter.exceptions.IndexOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    private List<T> list;

    public MyList() {
        list = new ArrayList<>();
    }

    @Override
    public synchronized void add(T element) {
        list.add(element);
    }

    @Override
    public synchronized List<T> getAll() {
        return list;
    }

    @Override
    public synchronized T get(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index >= list.size())
            throw new IndexOutOfBoundsException("Index out of bounds");
        return list.get(index);
    }

    @Override
    public List<T> toList() {
        return list;
    }

    @Override
    public synchronized String toString() {
        StringBuilder str = new StringBuilder();
        for(T elements : list)
            str.append(elements).append("\n");
        return str.toString();
    }
}
