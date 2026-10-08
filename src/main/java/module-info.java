module com.example.stackvisualizationgrouppowerpuffboys {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.stackvisualizationgrouppowerpuffboys to javafx.fxml;
    exports com.example.stackvisualizationgrouppowerpuffboys;
}