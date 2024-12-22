package com.example.toylanguageinterpreter.repository;

import com.example.toylanguageinterpreter.exceptions.RepositoryException;
import com.example.toylanguageinterpreter.model.adt.MyIDictionary;
import com.example.toylanguageinterpreter.model.state.PrgState;
import com.example.toylanguageinterpreter.model.values.IValue;
import javafx.beans.InvalidationListener;

import java.util.List;

public interface IRepository {

    List<PrgState> getStates();
    PrgState getState(int index);
    void setPrgList(List<PrgState> prgList);
    void addPrgState(PrgState prgState);
    void logPrgStateExec(PrgState state) throws RepositoryException;
    void clearLogFile() throws RepositoryException;
    public List<MyIDictionary<String, IValue>> symTables();
    void addListener(InvalidationListener listener);
    void notifyListeners();
}
