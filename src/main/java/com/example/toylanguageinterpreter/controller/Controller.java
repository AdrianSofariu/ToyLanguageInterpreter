package com.example.toylanguageinterpreter.controller;

import com.example.toylanguageinterpreter.exceptions.*;
import com.example.toylanguageinterpreter.model.garbageCollector.GarbageCollector;
import com.example.toylanguageinterpreter.model.garbageCollector.IGarbageCollector;
import com.example.toylanguageinterpreter.model.state.PrgState;
import com.example.toylanguageinterpreter.repository.IRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller{

    IRepository repo;
    boolean displayFlag = true;
    IGarbageCollector garbageCollector;
    ExecutorService executor;
    List<PrgState> prgList;

    public Controller(IRepository repo){
        this.repo = repo;
        this.garbageCollector = new GarbageCollector();

    }

    public void setDisplayFlag(){
        displayFlag = !displayFlag;
    }

    public void setCurrentProgram(PrgState prg){
        repo.addPrgState(prg);
    }

    public void allStep() throws ControllerException {

        try {
            repo.clearLogFile();
        }
        catch (RepositoryException e){
            throw new ControllerException(e.getMessage());
        }

        executor = Executors.newFixedThreadPool(2);

        //remove the completed programs
        prgList = removeCompletedPrg(repo.getStates());

        while(!prgList.isEmpty()){
            //call garbage collector for each program
            //prgList.forEach(prg -> {garbageCollector.collect(prg.getSymTable(), prg.getHeap());});
            try{
                garbageCollector.collect(repo.symTables(), prgList.getFirst().getHeap().getContent());
                oneStepForAllPrg(prgList);
            }
            catch (RuntimeException | InterruptedException e){
                throw new ControllerException(e.getMessage());
            }
            prgList = removeCompletedPrg(repo.getStates());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public Exception prepareForExecution(){

        try {
            repo.clearLogFile();
        } catch (RepositoryException e) {
            return e;
        }
        executor = Executors.newFixedThreadPool(2);
        prgList = removeCompletedPrg(repo.getStates());
        return null;
    }

    public void stopExecution(){
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        //log state before execution
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        });

        //execute one step for each program
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .toList();

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e.getCause());
                    }
                    catch (InterruptedException e){
                        throw new RuntimeException(e);
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        //add the new programs to the list
        prgList.addAll(newPrgList);

        //log state after execution
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        });

        //update the repository
        repo.setPrgList(prgList);
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::notCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepGUI() throws ControllerException {
        if(!prgList.isEmpty()){
            try{
                garbageCollector.collect(repo.symTables(), prgList.getFirst().getHeap().getContent());
                oneStepForAllPrg(prgList);
            }
            catch (RuntimeException | InterruptedException e){
                throw new ControllerException(e.getMessage());
            }
            prgList = removeCompletedPrg(repo.getStates());
            repo.notifyListeners();
        }
        else{
            executor.shutdownNow();
            repo.setPrgList(prgList);
            repo.notifyListeners();
        }
    }
}