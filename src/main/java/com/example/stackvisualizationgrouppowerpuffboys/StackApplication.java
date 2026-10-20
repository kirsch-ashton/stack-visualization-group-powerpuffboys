package com.example.stackvisualizationgrouppowerpuffboys;
import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


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

        public dataTable(String operation, int value){
            this.operation = operation;
            this.value = value;
        }

        public String getOperation(){
            return this.operation;
        }
        public int getValue(){
            return this.value;
        }
        public String getStack(){
            return this.stack;
        }
        
        public void setOperation(String x){
            operation = x;
        }
        public void setValue(int x){
            value = x;
        }
        public void setStack(String x){
            stack = x;
        }
    }
    ObservableList<dataTable> data = FXCollections.observableArrayList();
    private Stack stack = new Stack();
    private VBox stackBox = new VBox(5);
    private Label statusMessage = new Label();
    private boolean pressedRandom = false;
    private boolean peekActive = false;

    private boolean pressedRandom;
    void setRandom(boolean x){this.pressedRandom = x;}
    // void setRandom(boolean x){this.setRandom(x);}
    // ^ the line above this will cause a infinite recursion, so kung mao ni, uncomment nalang if so.

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane leftPanel = leftPanel();
        BorderPane rightPanel = rightPanel();
        BorderPane centerPanel = centerPanel();

        // Adds class name to use and modify properties in style.css
        leftPanel.getStyleClass().add("leftPanel");
        rightPanel.getStyleClass().add("rightPanel");
        centerPanel.getStyleClass().add("centerPanel");

        // comment
        BorderPane root = new BorderPane();
        root.setLeft(leftPanel);
        root.setCenter(centerPanel);
        root.setRight(rightPanel);

        Scene scene = new Scene(root, 900, 700);
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
        Button checkButton = new Button("Check");
        Button capacityButton = new Button("Capacity");
        Label title = new Label("Stacks");
        title.getStyleClass().add("lp-title");

        pushbutton.setOnAction(event -> {
            try{
                int fieldValue = Integer.parseInt(field.getText());
                field.clear();
                stack.push(fieldValue);
                addData(pushbutton.getText(),  fieldValue);
                refreshStackDisplay();
            } catch (NumberFormatException e){
                System.out.println("Enter a number");
            }
        });
        peekbutton.setOnAction(event ->{
            stack.peek();
            peekActive = true;
            refreshStackDisplay();
            peekActive = false;
        });
        
        checkButton.setOnAction(event ->{
         isCheckPressed();
         
        });
        
    
        
        //Push, pop, peek, clear, random
        popbutton.setOnAction( event -> {
            checkStack();
            int num = stack.pop();
            refreshStackDisplay();
        });
        randombutton.setOnAction(event ->{
            pressedRandom = true;
            refreshStackDisplay(); 
            pressedRandom = false;
        });
        clearbutton.setOnAction(event ->{
            isClearPressed();
        });

        VBox buttonBox = new VBox(20,field, pushbutton, popbutton, peekbutton, clearbutton, randombutton, checkButton, capacityButton);
        buttonBox.setAlignment(Pos.CENTER);

        BorderPane leftPanel = new BorderPane();
        BorderPane.setAlignment(title, Pos.CENTER);
        leftPanel.setCenter(buttonBox);
        leftPanel.setTop(title);
        return leftPanel;
    }

    public BorderPane rightPanel(){
        BorderPane rightPanel = new BorderPane();
        Label rpTitle = new Label("Operation History");
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
        stackTable.setMaxWidth(300);
        stackTable.setMaxHeight(500);

        rightPanel.setCenter(stackTable);
        rightPanel.setBottom(statusMessage);
        rightPanel.setTop(rpTitle);
        statusMessage.setStyle("-fx-translate-y: -80px");
        rpTitle.setStyle("-fx-translate-y: 80px");
        BorderPane.setAlignment(statusMessage, Pos.CENTER);
        BorderPane.setAlignment(rpTitle, Pos.CENTER);
        return rightPanel;
    }

    public BorderPane centerPanel(){
        BorderPane centerPanel = new BorderPane();
        Label title = new Label("Stack");
        stackBox.setAlignment(Pos.CENTER);

        centerPanel.setTop(title);
        centerPanel.setCenter(stackBox);

        return centerPanel;
    }

    public void refreshStackDisplay(){
        stackBox.getChildren().clear();

        for (int i = stack.getSize() - 1; i >= 0; i--) {
            StackPane box = new StackPane();
            Label value;

            if(pressedRandom){
                value = new Label(
                        String.valueOf((int)(Math.random() * (100 + 1)))
                );
            } else {
                value = new Label(
                        String.valueOf(stack.getValue(i))
                );
            }
            if (peekActive) {
                if (i == stack.getSize() - 1){
                    box.getStyleClass().add("stack-top");
                } else {
                    box.getStyleClass().add("stack-below");
                }
            }

            box.getChildren().add(value);

            box.getStyleClass().add("stack-box");
            value.getStyleClass().add("stack-value");
            

            stackBox.getChildren().add(box);
        }
    }

    public void isCheckPressed(){
        if(stack.isEmpty()){
            statusMessage.setText("The stack is Empty!");
        }
        else{
            statusMessage.setText("The stack is not Empty!");
        }
    }

    // Clear Button
    public void isClearPressed(){
        stackBox.getChildren().clear();
        for(int i = stack.getSize() - 1; i >= 0; i--){
            stack.pop();
        }

    }

    public void capacitySelector(){
        TextInputDialog dialog = new TextInputDialog("10"); // Default value
        dialog.setTitle("Change Stack Capacity");
        dialog.setHeaderText("Set a new maximum capacity for your stack:");
        dialog.setContentText("Capacity:");
        dialog.setGraphic(null);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent( input -> {
            try {
                int newCapacity = Integer.parseInt(input);
                if (newCapacity > 0) {
                    System.out.println("Updating stack capacity to:" + newCapacity);
                } else {
                    System.out.println("Capacity must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        });
    }

    public void addData(String operation, int value){
        data.add(new dataTable(operation, value));
    }
    public void checkStack(){
        if(stack.isFull()){ statusMessage.setText("STACK OVERFLOW: Stack is full"); }
        if(stack.isEmpty()) {statusMessage.setText("STACK UNDERFLOW: Stack is empty"); }
    }
}
