package com.example.stackvisualizationgrouppowerpuffboys;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StackApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane leftPanel = leftPanel();
        BorderPane rightPanel = rightPanel();
        BorderPane centerPanel = centerPanel();

        // Adds class name to use and modify properties in style.css
        leftPanel.getStyleClass().add("leftPanel");
        rightPanel.getStyleClass().add("rightPanel");
        centerPanel.getStyleClass().add("centerPanel");

        BorderPane root = new BorderPane();
        root.setLeft(leftPanel);
        root.setCenter(centerPanel);
        root.setRight(rightPanel);

        Scene scene = new Scene(root, 800, 800);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setTitle("Stack Visualization");
        stage.setScene(scene);
        stage.show();
    }

    public BorderPane leftPanel(){
        //Code

        BorderPane leftPanel = new BorderPane();
        return leftPanel;
    }

    public BorderPane rightPanel(){
        //Code
        BorderPane rightPanel = new BorderPane();
        return rightPanel;
    }

    public BorderPane centerPanel(){
        //Code
        BorderPane centerPanel = new BorderPane();
        return centerPanel;
    }
}
