package com.example.toylanguageinterpreter;

import com.example.toylanguageinterpreter.model.expressions.*;
import com.example.toylanguageinterpreter.model.statements.*;
import com.example.toylanguageinterpreter.model.types.IntType;
import com.example.toylanguageinterpreter.model.types.RefType;
import com.example.toylanguageinterpreter.model.types.StringType;
import com.example.toylanguageinterpreter.model.values.IntValue;
import com.example.toylanguageinterpreter.model.values.StringValue;
import com.example.toylanguageinterpreter.scenes.MainSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HelloApplication extends Application {

    //ex fork
    //int v; Ref int a; v=10; new(a,22); fork(wH(a,30); v=32; print(v); print(rH(a));); print(v); print(rH(a));
    IStatement exFork = new CompoundStatement(new VarDecStatement("v", new IntType()),
            new CompoundStatement(new VarDecStatement("a", new RefType(new IntType())),
                    new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                            new CompoundStatement(new HeapAllocStatement("a", new ValueExpression(new IntValue(22))),
                                    new CompoundStatement(new ForkStatement(new CompoundStatement(
                                            new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                            new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
                                            new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

    //Ref int a;new(a,20);fork(Ref int b;new(b,30);print(rH(a));print(rH(b)))
    IStatement exFork2 = new CompoundStatement(new VarDecStatement("a", new RefType(new IntType())),
            new CompoundStatement(new HeapAllocStatement("a", new ValueExpression(new IntValue(20))),
                    new ForkStatement(new CompoundStatement(new VarDecStatement("b", new RefType(new IntType())),
                            new CompoundStatement(new HeapAllocStatement("b", new ValueExpression(new IntValue(30))),
                                    new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))),
                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("b")))))))));

    //Ref int a;new(a,20);fork(new(a,30);print(rH(a)));print(rH(a))
    IStatement exFork3 = new CompoundStatement(new VarDecStatement("a", new RefType(new IntType())),
            new CompoundStatement(new HeapAllocStatement("a", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))),
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))));

    //int v; v=4; (while (v) print(v);v=v-1);print(v)
    IStatement terror = new CompoundStatement(new VarDecStatement("v", new IntType()), new CompoundStatement(
            new AssignStatement("v", new ValueExpression(new IntValue(4))),
            new WhileStatement(new VariableExpression("v"), new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                    new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), ArithmeticalOperator.MINUS, new ValueExpression(new IntValue(1))))))));

    //make a statement for if(2 < 2) then print(4) else print(5)
    IStatement ex5 = new IfStatement(new RelationalExpression(new ValueExpression(new IntValue(2)), ComparisonOperator.LESS,
            new ValueExpression(new IntValue(2))), new PrintStatement(new ValueExpression(new IntValue(4))),
            new PrintStatement(new ValueExpression(new IntValue(5))));

    IStatement ex4 = new CompoundStatement(new VarDecStatement("varf", new StringType()),
            new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("input.in"))),
                    new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
                            new CompoundStatement(new VarDecStatement("varc", new IntType()),
                                    new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                            new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                    new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                            new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                    new CloseRFileStatement(new VariableExpression("varf"))))))))));

    IStatement terror2 = new CompoundStatement(new VarDecStatement("v", new IntType()), new CompoundStatement(
            new AssignStatement("v", new ValueExpression(new IntValue(4))),
            new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), ArithmeticalOperator.DIVIDE, new ValueExpression(new IntValue(0))))));


    @Override
    public void start(Stage stage) throws IOException {

        List<IStatement> prgs = new ArrayList<>();
        prgs.add(exFork);
        prgs.add(exFork2);
        prgs.add(exFork3);
        prgs.add(terror);
        prgs.add(ex5);
        prgs.add(ex4);
        prgs.add(terror2);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainScene.fxml"));
        Parent root = fxmlLoader.load();
        MainSceneController controller = fxmlLoader.getController();
        controller.setProgramStatements(prgs);
        controller.setMainStage(stage);
        Scene scene = new Scene(root, 600, 300);
        stage.setTitle("Toy Language Interpreter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}