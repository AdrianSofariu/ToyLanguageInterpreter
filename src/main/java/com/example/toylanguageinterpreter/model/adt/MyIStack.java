package com.example.toylanguageinterpreter.model.adt;

import com.example.toylanguageinterpreter.exceptions.EmptyStackException;

import java.util.Stack;

public interface MyIStack<T> {

    void push(T element);
    T pop() throws EmptyStackException;
    int size();
    boolean isEmpty();
    public Stack<String> getAsStringStack();
}
