package com.example.stackvisualizationgrouppowerpuffboys;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.Stack;

public class StackApplication extends Application {
    ObservableList<dataTable> data = FXCollections.observableArrayList();

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
        BorderPane leftPanel = new BorderPane();
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
        return centerPanel;
    }
}
