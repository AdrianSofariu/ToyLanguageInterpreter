module com.example.toylanguageinterpreter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.toylanguageinterpreter to javafx.fxml;
    exports com.example.toylanguageinterpreter;
}