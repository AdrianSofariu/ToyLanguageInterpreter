package com.example.toylanguageinterpreter.model.adt;

import com.example.toylanguageinterpreter.exceptions.EmptyStackException;

import java.util.Queue;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{

    private final Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public void push(T element) {
        stack.push(element);
    }

    @Override
    public T pop() throws EmptyStackException {
        if(stack.isEmpty())
            throw new EmptyStackException("Stack is empty");
        return stack.pop();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Stack<String> getAsStringStack() {
        Stack<String> strStack = new Stack<>();

        //preserve the order of the elements
        for(int i = stack.size() - 1; i >= 0; i--){
            strStack.push(stack.get(i).toString());
        }

        return strStack;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        //iterate from top to bottom
        for (int i = stack.size() - 1; i >= 0; i--) {
            str.append(stack.get(i).toString()).append("\n");
        }
        return str.toString();
    }
}
