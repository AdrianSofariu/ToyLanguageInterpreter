module com.example.toylanguageinterpreter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.toylanguageinterpreter to javafx.fxml;
    opens com.example.toylanguageinterpreter.scenes to javafx.fxml;
    opens com.example.toylanguageinterpreter.viewClasses to javafx.base;
    exports com.example.toylanguageinterpreter;
}