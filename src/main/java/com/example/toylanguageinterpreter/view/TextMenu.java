package com.example.toylanguageinterpreter.view;

import com.example.toylanguageinterpreter.controller.Controller;
import com.example.toylanguageinterpreter.exceptions.ADTException;
import com.example.toylanguageinterpreter.exceptions.ExpressionException;
import com.example.toylanguageinterpreter.exceptions.StatementException;
import com.example.toylanguageinterpreter.model.adt.MyDictionary;
import com.example.toylanguageinterpreter.model.adt.MyHeap;
import com.example.toylanguageinterpreter.model.adt.MyList;
import com.example.toylanguageinterpreter.model.adt.MyStack;
import com.example.toylanguageinterpreter.model.state.PrgState;
import com.example.toylanguageinterpreter.model.statements.IStatement;

import com.example.toylanguageinterpreter.repository.IRepository;
import com.example.toylanguageinterpreter.repository.MyRepository;
import com.example.toylanguageinterpreter.view.commands.Command;
import com.example.toylanguageinterpreter.view.commands.ExitCommand;
import com.example.toylanguageinterpreter.view.commands.RunExampleCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {

    private Map<String, Command> map;

    public TextMenu(){
        this.map = new HashMap<>();
    }

    //constructor added for testing purposes
    //create a Command to run each statement and typecheck it
    public TextMenu(List<IStatement> statements){

        this.map = new HashMap<>();
        this.addCommand(new ExitCommand("0", "Exit"));

        //iterate
        for(int i = 0; i < statements.size(); i++){

            IStatement stmt = statements.get(i);
            int idx = i + 1;

            //type check the statement
            try{
                stmt.typeCheck(new MyDictionary<>());
            } catch (StatementException | ADTException | ExpressionException e) {
                System.out.println("Program " + idx + " did not pass the type checker.\n" + e.getMessage());
                continue;
            }

            //create a command for each statement
            IRepository repo = new MyRepository("log" + i + ".txt");
            repo.addPrgState(new PrgState(new MyList<>(), new MyDictionary<>(), new MyDictionary<>(), new MyStack<>(), new MyHeap(), statements.get(i)));
            Controller ctrl = new Controller(repo);
            Command cmd = new RunExampleCommand(Integer.toString(idx),"Run example " + idx, ctrl);
            //add the command to the map
            map.put(cmd.getKey(), cmd);
        }
    }

    public void addCommand(Command command){
        map.put(command.getKey(), command);
    }

    private void printMenu(){
        for(Command command : map.values()){
            String line = String.format("%4s : %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner sc = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.print("Input the option: ");
            String key = sc.nextLine();
            Command command = map.get(key);
            if(command == null){
                System.out.println("Invalid option");
                continue;
            }

            try {
                command.execute();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }


}
