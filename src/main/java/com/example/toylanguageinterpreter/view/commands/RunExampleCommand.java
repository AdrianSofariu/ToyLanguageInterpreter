package com.example.toylanguageinterpreter.view.commands;

import com.example.toylanguageinterpreter.controller.Controller;
import com.example.toylanguageinterpreter.exceptions.*;

public class RunExampleCommand extends Command{

    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller){
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute(){
        try{
            controller.allStep();
        } catch (ControllerException e){
            System.out.println(e.getMessage());
        }
    }


}
