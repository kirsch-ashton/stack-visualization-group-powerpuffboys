package com.example.stackvisualizationgrouppowerpuffboys;

/* Description: Handles the JavaFX GUI, button events, stack visualization, operation history, and status messages.
    Programmed by: PowerPuff Boys BSIT 48079 Data Structures and Algorithms
    Last Modified: 10/26/2026
    [Acknowledgements:
    https://jenkov.com/tutorials/javafx/index.html
    https://dev.java/learn/javafx/core/layout/
    https://www.tutorialspoint.com/javafx/index.htm
    https://www.geeksforgeeks.org/java/javafx-tutorial/

        ChatGPT:
        "What JavaFX classes can I use to store/display user actions, values, and stack?"
        Answered:
        TableView and it's related classes
    ]
 */

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
        Integer value;
        String stack;

        public dataTable(String operation, Stack stack){
        this.operation = operation;
        this.value = null;
        this.stack = stack.toString();
}
        public dataTable(String operation, int value, Stack stack){
            this.operation = operation;
            this.value = value;
            this.stack = stack.toString();
        }

        public dataTable(String operation, int value){
            this.operation = operation;
            this.value = value;
        }

        public dataTable(String operation){
            this.operation = operation;
            this.value = null;
        }

        public String getOperation(){
            return this.operation;
        }
        public Integer getValue(){
            return this.value;
        }
        public String getStack(){
            return this.stack;
        }

    }
    ObservableList<dataTable> data = FXCollections.observableArrayList();
    private Stack stack = new Stack();
    private VBox stackBox = new VBox(5);
    private Label statusMessage = new Label();
    private boolean pressedRandom = false;
    private boolean peekActive = false;
    public void setPeekActive(boolean x){ this.peekActive = x; }
    private Button lastButtonPressed = new Button();
    void setRandom(boolean x){this.pressedRandom = x;}
    public void setStatusMessage(String label){ this.statusMessage.setText(label); }

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane leftPanel = leftPanel();
        BorderPane rightPanel = rightPanel();
        BorderPane centerPanel = centerPanel();

        leftPanel.getStyleClass().add("leftPanel");
        rightPanel.getStyleClass().add("rightPanel");
        centerPanel.getStyleClass().add("centerPanel");

        BorderPane root = new BorderPane();
        root.setLeft(leftPanel);
        root.setCenter(centerPanel);
        root.setRight(rightPanel);

        Scene scene = new Scene(root, 1400, 800);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setTitle("Stack Visualization");
        stage.setScene(scene);
        stage.show();
    }

    public BorderPane leftPanel(){
        TextField field = new TextField();
        field.setPromptText("Enter Stack Value");
        Button pushbutton = new Button("Push");
        Button popbutton = new Button("Pop");
        Button peekbutton = new Button("Peek");
        Button clearbutton = new Button("Clear");
        Button randombutton = new Button("Random");
        Button checkButton = new Button("Check");
        Button capacityButton = new Button("Capacity");
        Button undo = new Button("Undo");
        Label title = new Label("Stacks");
        title.getStyleClass().add("lp-title");

        // Button functions
        pushbutton.setOnAction(event -> {
            try{
                int fieldValue = Integer.parseInt(field.getText());
                if(!stack.isFull()){
                    statusMessage.setText("Status Message: Pushed " + fieldValue);
                    lastButtonPressed.setText(pushbutton.getText());
                    field.clear();
                    stack.push(fieldValue);
                    addData(pushbutton.getText(), fieldValue, stack);
                }
                refreshStackDisplay();
            } catch (NumberFormatException e){
                System.out.println("Enter a number");
            }
        });

        peekbutton.setOnAction(event ->{
            statusMessage.setText("Status Message: Pressed Peek ");
            addData(peekbutton.getText());
            lastButtonPressed.setText(peekbutton.getText());
            stack.peek();
            peekActive = true;
            refreshStackDisplay();
            peekActive = false;
        });
        
        checkButton.setOnAction(event ->{
            statusMessage.setText("Status Message: Pressed Check ");
            addData(checkButton.getText());
            isCheckPressed();
        });

        capacityButton.setOnAction(event -> {
            capacitySelector();
        });
        
        popbutton.setOnAction( event -> {
            checkStack();
            if(!stack.isEmpty()){
                lastButtonPressed.setText(popbutton.getText());
                int num = stack.pop();
                statusMessage.setText("Status Message: Popped " + num);
                addData(popbutton.getText(), num, stack);
            }
            refreshStackDisplay();
        });

        randombutton.setOnAction(event ->{
            isClearPressed();   // saves the snapshot, then empties the stack
            statusMessage.setText("Status Message: Pressed Random ");

            for (int i = 0; i < stack.getCapacity(); i++) {
                int randomNum = (int)(Math.random() * 100) + 1;   // 1 to 100
                stack.push(randomNum);
            }

            lastButtonPressed.setText(randombutton.getText());
            addData(randombutton.getText(), stack);
            refreshStackDisplay();
            });

            clearbutton.setOnAction(event ->{
            statusMessage.setText("Status Message: Pressed Clear ");
            isClearPressed();
            addData(clearbutton.getText(), stack);
            lastButtonPressed.setText(clearbutton.getText());
        });

        undo.setOnAction(event -> {
            if(lastButtonPressed.getText().isEmpty()){
                statusMessage.setText("Status Message: Nothing to undo.");
                return;
            }
            stack.undoOperation(lastButtonPressed, this);
            lastButtonPressed.setText("");
            refreshStackDisplay();
        });

        VBox buttonBox = new VBox(20,field, pushbutton, popbutton, peekbutton, clearbutton, randombutton, checkButton, capacityButton, undo);
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
        rpTitle.getStyleClass().add("rp-title");
        statusMessage = new Label("Status Message: Waiting for inputs...");
        statusMessage.getStyleClass().add("status-message");
        statusMessage.setWrapText(true);
        TableView<dataTable> stackTable = new TableView<>();
        stackTable.setStyle("-fx-translate-y: 7px");

        TableColumn<dataTable, String> operationColumn = new TableColumn<>("Operation");
        operationColumn.setCellValueFactory(new PropertyValueFactory<>("operation"));
        operationColumn.getStyleClass().add("operation-header");

        TableColumn<dataTable, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        valueColumn.getStyleClass().add("value-header");

        TableColumn<dataTable, String> stackColumn = new TableColumn<>("Stack");
        stackColumn.setCellValueFactory(new PropertyValueFactory<>("stack"));
        stackColumn.getStyleClass().add("stack-header");

        operationColumn.setPrefWidth(100);
        valueColumn.setPrefWidth(60);
        stackColumn.setPrefWidth(190);

        stackTable.getColumns().add(operationColumn);
        stackTable.getColumns().add(valueColumn);
        stackTable.getColumns().add(stackColumn);
        stackTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        stackTable.setItems(data);
        stackTable.setMaxWidth(350);
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
        stackBox.setAlignment(Pos.CENTER);

        centerPanel.setCenter(stackBox);
        return centerPanel;
    }

    public void refreshStackDisplay(){
        stackBox.getChildren().clear();

        for (int i = stack.getSize() - 1; i >= 0; i--) {
            StackPane box = new StackPane();
            Label value = new Label(String.valueOf(stack.getValue(i)));
            if (peekActive) {
                if (i == stack.getSize() - 1){
                    box.getStyleClass().add("stack-top");
                } else {
                    box.getStyleClass().add("stack-below");
                    value.getStyleClass().add("stack-peek-value");
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
            statusMessage.setText("Status Message: The stack is Empty!");
        }
        else{
            statusMessage.setText("Status Message: The stack is not Empty!");
        }
    }

    public void isClearPressed(){
        stack.saveSnapshot();            // save before destroying
        stackBox.getChildren().clear();
        stack.clear();                   // replaces the old pop() loop
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
                    stack.saveSnapshot();
                stack.setCapacity(newCapacity);
                lastButtonPressed.setText("Capacity");
                statusMessage.setText("Status Message: Capacity set to " + newCapacity);
                addData("Capacity", newCapacity, stack);
                refreshStackDisplay();

                } else {
                    statusMessage.setText("Status Message: Capacity must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        });
    }

    public void addData(String operation, int value, Stack stack){
        data.add(new dataTable(operation, value, stack));
    }
    public void addData(String operation){
        data.add(new dataTable(operation));
    }
    public void checkStack(){
        if(stack.isFull()){ statusMessage.setText("STACK OVERFLOW: Stack is full"); }
        if(stack.isEmpty()) {statusMessage.setText("STACK UNDERFLOW: Stack is empty"); }
    }
    public void addData(String operation, Stack stack){
    data.add(new dataTable(operation, stack));
    }
}
