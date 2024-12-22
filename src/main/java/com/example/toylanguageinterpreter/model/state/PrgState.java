package com.example.toylanguageinterpreter.model.state;

import com.example.toylanguageinterpreter.exceptions.*;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.adt.MyIHeap;
import com.example.toylanguageinterpreter.model.adt.MyIList;
import com.example.toylanguageinterpreter.model.adt.MyIStack;
import com.example.toylanguageinterpreter.model.statements.IStatement;
import com.example.toylanguageinterpreter.model.values.IValue;
import com.example.toylanguageinterpreter.model.values.StringValue;


import java.io.BufferedReader;

public class PrgState{

    private static int lastIndex = 0;
    private int id;
    private MyIList<String> outputList;
    private MyIDictionary<String, IValue> symTable;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIStack<IStatement> execStack;
    private IStatement initialState;
    private MyIHeap heap;

    private static synchronized int getNextId() {
        return lastIndex++;
    }

    public PrgState(MyIList<String> outputList, MyIDictionary<String, IValue> symTable, MyIDictionary<StringValue, BufferedReader> fileTable, MyIStack<IStatement> execStack, MyIHeap heap, IStatement initialState) {
        this.id = getNextId();
        this.outputList = outputList;
        this.symTable = symTable;
        this.execStack = execStack;
        this.fileTable = fileTable;
        this.heap = heap;
        this.initialState = initialState.deepCopy();
        this.execStack.push(this.initialState);
    }

    public IStatement getInitialState() {
        return initialState;
    }

    public void setInitialState(IStatement initialState) {
        this.initialState = initialState;
    }

    public MyIList<String> getOutputList() {
        return outputList;
    }

    public void setOutputList(MyIList<String> outputList) {
        this.outputList = outputList;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public MyIStack<IStatement> getExecStack() {
        return execStack;
    }

    public void setExecStack(MyIStack<IStatement> execStack) {
        this.execStack = execStack;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public String getId() {
        return "State " + id;
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap heap) {
        this.heap = heap;
    }

    public String FileTableToString() {
        StringBuilder text = new StringBuilder();
        text.append("File Table:\n");
        for(StringValue key : fileTable.getKeys()) {
            text.append(key).append("\n");
        }
        return text.toString();
    }

    @Override
    public String toString() {
        String output = "-------Program State " + id + "-------\n";
        output += "Symbol Table:\n" + symTable +
                "\nExecution Stack:\n" + execStack+
                "\nOutput List:\n" + outputList + "\n" + FileTableToString() +
                "\nHeap:\n" + heap;
        output += "\n---------------------------\n";
        return output;
    }

    public boolean notCompleted(){
        return !execStack.isEmpty();
    }

    public PrgState oneStep() throws StatementException, ExpressionException, ADTException, HeapException{
        if(execStack.isEmpty())
            throw new EmptyStackException("Execution stack is empty");
        IStatement currentStatement = execStack.pop();
        return currentStatement.execute(this);
    }
}
