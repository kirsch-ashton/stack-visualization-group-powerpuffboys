package com.example.stackvisualizationgrouppowerpuffboys;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.IOException;
import javafx.geometry.Pos;

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

        Scene scene = new Scene(root, 900, 900);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setTitle("Stack Visualization");
        stage.setScene(scene);
        stage.show();
    }

    public BorderPane leftPanel(){
        TextField field = new TextField();
        Button pushbutton = new Button("Push");
        Button popbutton = new Button("Pop");
        Button peekbutton = new Button("Peek");
        Button clearbutton = new Button("Clear");
        Button randombutton = new Button("Random");
        Label title = new Label("Stacks");
        
      
        
        //Push, pop, peek, clear, random
        
        VBox Buttonbox = new VBox(20,pushbutton,popbutton,peekbutton,clearbutton,randombutton);
        Buttonbox.setAlignment(Pos.CENTER);
        

        
        BorderPane leftPanel = new BorderPane();
        BorderPane.setAlignment(title, Pos.CENTER);
        leftPanel.setCenter(Buttonbox);
        leftPanel.setTop(title);
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
