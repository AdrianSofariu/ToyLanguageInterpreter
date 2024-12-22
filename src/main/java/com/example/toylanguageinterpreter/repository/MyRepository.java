package com.example.toylanguageinterpreter.repository;

import com.example.toylanguageinterpreter.exceptions.RepositoryException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;

import com.example.toylanguageinterpreter.model.state.PrgState;
import com.example.toylanguageinterpreter.model.values.IValue;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ArrayChangeListener;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.Observable;


public class MyRepository implements IRepository, Observable {

    private List<PrgState> prgStateList;
    private List<InvalidationListener> listeners ;
    private String filename;
    private int currentIndex;

    public MyRepository(String filename) {
        listeners = new ArrayList<>();
        prgStateList = new ArrayList<>();
        this.filename = filename;
        currentIndex = 0;
    }

    @Override
    public List<PrgState> getStates() {
        return this.prgStateList;
    }

    @Override
    public PrgState getState(int index) {
        if(index > 0)
            return prgStateList.get(index);
        return prgStateList.getFirst();
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.prgStateList = prgList;
    }

    @Override
    public void addPrgState(PrgState prgState) {
        prgStateList.add(prgState);
    }


    @Override
    public void logPrgStateExec(PrgState state) throws RepositoryException {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
            logFile.println(state);
            logFile.close();
        }
        catch (IOException e){
            throw new RepositoryException("Could not open log file");
        }
    }

    @Override
    public void clearLogFile() throws RepositoryException {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(filename, false)));
            logFile.close();
        }
        catch (IOException e){
            throw new RepositoryException("Could not open log file");
        }
    }

    //return a list of all symTables
    public List<MyIDictionary<String, IValue>> symTables(){
        List<MyIDictionary<String, IValue>> symTables = new ArrayList<>();
        for(PrgState prg : prgStateList){
            symTables.add(prg.getSymTable());
        }
        return symTables;
    }

    public void addListener(InvalidationListener listener){
        listeners.add(listener);
    }

    public void removeListener(InvalidationListener listener){
        listeners.remove(listener);
    }

    public void notifyListeners(){
        for(InvalidationListener listener : listeners){
            listener.invalidated((javafx.beans.Observable) this);
        }
    }

}
