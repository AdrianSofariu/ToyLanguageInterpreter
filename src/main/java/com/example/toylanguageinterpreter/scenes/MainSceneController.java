package com.example.toylanguageinterpreter.scenes;

import com.example.toylanguageinterpreter.HelloApplication;
import com.example.toylanguageinterpreter.exceptions.ADTException;
import com.example.toylanguageinterpreter.exceptions.ExpressionException;
import com.example.toylanguageinterpreter.exceptions.StatementException;
import com.example.toylanguageinterpreter.model.adt.MyDictionary;
import com.example.toylanguageinterpreter.model.statements.IStatement;
import com.example.toylanguageinterpreter.model.types.IType;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainSceneController {

    @FXML
    private ListView<String> listView;

    @FXML
    private Button runButton;

    @FXML
    private Label label;

    private Stage mainStage;


    List<IStatement> programStatements = new ArrayList<>();
    IStatement currentStatement;

    public void setProgramStatements(List<IStatement> programStatements) {
        this.programStatements = programStatements;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    //open a new window to run the program
    @FXML
    public void handleRunButton(ActionEvent event) {

        //load the program scene
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("programScene.fxml"));
        Scene secondScene;
        try{

            //typecheck the program statements
            currentStatement.typeCheck(new MyDictionary<String, IType>());

            Parent root = fxmlLoader.load();
            ProgramSceneController controller = fxmlLoader.getController();
            controller.setInitialStatement(currentStatement);
            secondScene = new Scene(root, 800, 600);


        } catch (IOException | StatementException | ADTException | ExpressionException e) {
            //if the scene cannot be loaded, print an error message in a modal window
            Stage errorWindow = new Stage();
            errorWindow.setTitle("Error");
            errorWindow.setScene(new Scene(new StackPane(new Label("Error loading the program scene: " + e.getMessage())), 200, 200));
            errorWindow.initModality(javafx.stage.Modality.WINDOW_MODAL);
            errorWindow.initOwner(mainStage);
            errorWindow.show();
            return;
        }

        //new window
        Stage newWindow = new Stage();
        newWindow.setTitle("Running the program");
        newWindow.setScene(secondScene);
        newWindow.setOnCloseRequest(e -> {
            //stop the execution of the program when the window is closed
            ProgramSceneController controller = fxmlLoader.getController();
            controller.stopExecution();
        });
        newWindow.initModality(javafx.stage.Modality.WINDOW_MODAL);
        newWindow.initOwner(mainStage);

        //set spawnpoint
        newWindow.setX(mainStage.getX() + 200);
        newWindow.setY(mainStage.getY() + 100);

        newWindow.show();
    }


    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            //add the program statements to the list view
            for (IStatement programStatement : programStatements) {
                listView.getItems().add(programStatement.toString());
            }

            //add a listener to the list view
            //when a statement is selected, display it in the label and set it as the current statement
            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    final int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                    label.setText("Program statement: " + selectedIndex);
                    currentStatement = programStatements.get(selectedIndex);
                }
            });

            //select the first statement by default
            listView.getSelectionModel().select(0);

            //add event listener to the run button
            runButton.setOnAction(this::handleRunButton);

        });


    }
}
