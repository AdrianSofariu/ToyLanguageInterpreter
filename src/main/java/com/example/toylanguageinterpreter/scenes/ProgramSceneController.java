package com.example.toylanguageinterpreter.scenes;

import com.example.toylanguageinterpreter.controller.Controller;
import com.example.toylanguageinterpreter.model.adt.MyDictionary;
import com.example.toylanguageinterpreter.model.adt.MyHeap;
import com.example.toylanguageinterpreter.model.adt.MyList;
import com.example.toylanguageinterpreter.model.adt.MyStack;
import com.example.toylanguageinterpreter.model.state.PrgState;
import com.example.toylanguageinterpreter.model.statements.IStatement;
import com.example.toylanguageinterpreter.model.values.IValue;
import com.example.toylanguageinterpreter.model.values.StringValue;
import com.example.toylanguageinterpreter.repository.IRepository;
import com.example.toylanguageinterpreter.repository.MyRepository;
import com.example.toylanguageinterpreter.viewClasses.HeapValue;
import com.example.toylanguageinterpreter.viewClasses.SymTableValue;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.util.List;

public class ProgramSceneController{

    @FXML
    private Label nrStatesLabel;

    @FXML
    private ListView<String> outView;

    @FXML
    private ListView<String> prgStatesView;

    @FXML
    private ListView<String> fileTableView;

    @FXML
    private TableView<SymTableValue> symTableView;

    @FXML
    TableColumn<SymTableValue,String> nameColumn = new TableColumn<>("Name");

    @FXML
    TableColumn<SymTableValue,IValue> valColumn = new TableColumn<>("Value");

    @FXML
    private ListView<String> exeStackView;

    @FXML
    private TableView<HeapValue> heapView;

    @FXML
    TableColumn<HeapValue, String> addrColumn = new TableColumn<>("Address");

    @FXML
    TableColumn<HeapValue, Integer> valueColumn = new TableColumn<>("Value");


    @FXML
    private Button runButton;


    InvalidationListener listener = new InvalidationListener() {
        @Override
        public void invalidated(javafx.beans.Observable observable) {
            update();
        }
    };

    IRepository repository;
    MyRepository repo;
    Controller controller;
    IStatement initialStatement;
    PrgState currentPrgState;

    public void setInitialStatement(IStatement initialStatement) {
        this.initialStatement = initialStatement;
    }

    public void update(){

        if(currentPrgState == null)
            return;

        //update the views
        outView.getItems().setAll(currentPrgState.getOutputList().toList());
        fileTableView.getItems().setAll(currentPrgState.getFileTable().toString());
        exeStackView.getItems().setAll(currentPrgState.getExecStack().getAsStringStack());

        //update the number of program states
        nrStatesLabel.setText("Number of program states: " + repository.getStates().size());

        if(repository.getStates().isEmpty()){
            //empty the views
            prgStatesView.getItems().clear();
            fileTableView.getItems().clear();
            exeStackView.getItems().clear();
            heapView.getItems().clear();
            symTableView.getItems().clear();
            return;
        }

        //update the program states view
        List<String> states = repository.getStates().stream().map(PrgState::getId).toList();
        prgStatesView.getItems().setAll(states);

        if(currentPrgState == null)
            return;
        //update the heap view
        List<HeapValue> HeapValues = currentPrgState.getHeap().getContent().entrySet().stream().map(e -> new HeapValue(e.getKey(), e.getValue())).toList();
        heapView.getItems().setAll(HeapValues);

        //update the symbol table view
        List<SymTableValue> SymTableValues = currentPrgState.getSymTable().getMap().entrySet().stream().map(e -> new SymTableValue(e.getKey(), e.getValue())).toList();
        symTableView.getItems().setAll(SymTableValues);

        //clear the selected item
        prgStatesView.getSelectionModel().select(0);

    }

    private void onChangePrgState(){
        //update the symTableView and the exeStackView
        if(currentPrgState == null)
            return;

        List<SymTableValue> SymTableValues = currentPrgState.getSymTable().getMap().entrySet().stream().map(e -> new SymTableValue(e.getKey(), e.getValue())).toList();
        symTableView.getItems().setAll(SymTableValues);

        List<String> exeStack = currentPrgState.getExecStack().getAsStringStack();
        exeStackView.getItems().setAll(exeStack);
    }

    //stop execution on window close
    public void stopExecution(){
        controller.stopExecution();
    }

    @FXML
    public void oneStep(ActionEvent event){
        try{
            controller.oneStepGUI();
        } catch (Exception e){
            //print error in a modal window
            Stage errorWindow = new Stage();

            //make scene match the size of the label
            errorWindow.setTitle("Error");
            errorWindow.setScene(new javafx.scene.Scene(new javafx.scene.layout.StackPane(new javafx.scene.control.Label(e.getMessage())), 200, 300));

            errorWindow.initModality(javafx.stage.Modality.WINDOW_MODAL);
            errorWindow.initOwner(runButton.getScene().getWindow());
            errorWindow.show();
            errorWindow.setOnCloseRequest(v -> {
                //stop the execution of the program when the window is closed
                stopExecution();
                //close this windows and the parent window
                ((Stage)runButton.getScene().getWindow()).close();
            });
        }
    }


    @FXML
    public void initialize(){
        Platform.runLater(() -> {
            repository = new MyRepository("log.txt");
            repo = (MyRepository) repository;
            currentPrgState = new PrgState(new MyList<String>(), new MyDictionary<String, IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyStack<IStatement>(), new MyHeap(), initialStatement);
            repository.addPrgState(currentPrgState);
            repository.addListener(listener);
            controller = new Controller(repository);

            //prepare ui
            addrColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            valColumn.setCellValueFactory(new PropertyValueFactory<>("value"));


            //add listener to the program states view
            prgStatesView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    final int selectedIndex = prgStatesView.getSelectionModel().getSelectedIndex();
                    currentPrgState = repository.getState(selectedIndex);
                    onChangePrgState();
                }
            });

            //initialize the views
            update();

            //prepare the controller for execution
            Exception e = controller.prepareForExecution();
            if(e != null){
                //print error in a modal window
                Stage errorWindow = new Stage();
                errorWindow.setTitle("Error");
                errorWindow.setScene(new javafx.scene.Scene(new javafx.scene.layout.StackPane(new javafx.scene.control.Label(e.getMessage())), 200, 200));
                errorWindow.initModality(javafx.stage.Modality.WINDOW_MODAL);
                errorWindow.initOwner(runButton.getScene().getWindow());
                errorWindow.show();
            }


        });
    }
}
