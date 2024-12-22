package com.example.toylanguageinterpreter.model.adt;

import java.util.List;
import com.example.toylanguageinterpreter.exceptions.IndexOutOfBoundsException;

public interface MyIList<T> {

    void add(T element);
    List<T> getAll();
    T get(int index) throws IndexOutOfBoundsException;
    List<T> toList();
}
