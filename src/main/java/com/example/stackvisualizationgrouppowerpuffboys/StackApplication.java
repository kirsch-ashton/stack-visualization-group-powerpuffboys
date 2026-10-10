package com.example.stackvisualizationgrouppowerpuffboys;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import javafx.geometry.Pos;

public class StackApplication extends Application {
    public static class dataTable{
        String operation;
        int value;
        String stack;

        public dataTable(String operation, int value, String stack){
            this.operation = operation;
            this.value = value;
            this.stack = stack;
        }

        String getOperation(){
            return this.operation;
        }
        int getValue(){
            return this.value;
        }
        String getStack(){
            return this.stack;
        }

        void setOperation(String x){
            operation = x;
        }
        void setValue(int x){
            value = x;
        }
        void setStack(String x){
            stack = x;
        }
    }
    ObservableList<dataTable> data = FXCollections.observableArrayList();
    private Stack stack = new Stack();
    private VBox stackBox = new VBox(5);

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
        rightPanel.setTop(new Label("Operation History"));
        TableView<dataTable> stackTable = new TableView<>();

        TableColumn<dataTable, String> operationColumn = new TableColumn<>("Operation");
        operationColumn.setCellValueFactory(new PropertyValueFactory<>("operation"));

        TableColumn<dataTable, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<dataTable, String> stackColumn = new TableColumn<>("Stack");
        stackColumn.setCellValueFactory(new PropertyValueFactory<>("stack"));

        stackTable.getColumns().add(operationColumn);
        stackTable.getColumns().add(valueColumn);
        stackTable.getColumns().add(stackColumn);

        stackTable.setItems(data);

        ObservableList<dataTable> logs = FXCollections.observableArrayList(
                new dataTable("PUSH", 10, "yey"),
                new dataTable("PUSH", 20, "no")
        );
        stackTable.setItems(logs);

        rightPanel.setCenter(stackTable);
        return rightPanel;
    }

    public BorderPane centerPanel(){
        //Code
         BorderPane centerPanel = new BorderPane();
        Label title = new Label("Stack");
        stackBox.setAlignment(Pos.CENTER);

        for(int i = stack.getSize() - 1; i >= 0; i--){
            StackPane box = new StackPane();

            Label value = new Label(
                    String.valueOf(stack.getValue(i))
            );
            box.getChildren().add(value);
            stackBox.getChildren().add(box);
        }

        centerPanel.setTop(title);
        centerPanel.setCenter(stackBox);

        return centerPanel;
    }
}
