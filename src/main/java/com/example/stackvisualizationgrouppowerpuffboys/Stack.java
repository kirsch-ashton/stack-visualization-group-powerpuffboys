package com.example.stackvisualizationgrouppowerpuffboys;

/* Description: Handles stack logic, including Push, Pop, and many other functions.
    Programmed by: PowerPuff Boys BSIT 48079 Data Structures and Algorithms
    Last Modified: 10/24/2026
    [Acknowledgements:
    https://jenkov.com/tutorials/javafx/index.html
    https://dev.java/learn/javafx/core/layout/
    https://www.tutorialspoint.com/javafx/index.htm
    https://www.geeksforgeeks.org/java/javafx-tutorial/

        ChatGPT:
        "How can I make the Stack column in my TableView display the actual stack values instead of com.example...?"
        Answered:
        Suggested to use StringBuilder
    ]
 */

import javafx.scene.control.Button;
// Stack Data and Logic
public class Stack {
    private int capacity = 10;
    private int[] stack = new int[capacity];
    private int[] backupStack = stack;
    private int top = -1; //Default value kay empty ang stack/array
    private int previousPushValue;
    
//Add a top value in Stack
    public void push(int value){
        previousPushValue = value;
        if(isFull()){
            System.out.println("ERROR: Stack is Full");
            return;
        }
        top++;
        stack[top] = value;
    }

//Remove the top value of Stack
    public int pop() {
        if(isEmpty()){
            System.out.println("ERROR: Stack is Empty");
            return -1;
        }

        int poppedValue = stack[top];
        top--;
        return poppedValue;
    }

//Return the top value of Stack
    public int peek(){
    if(top == -1){
            System.out.println("ERROR: Stack is Empty");
            return -1;
        }
    int topValue = stack[top];
    return topValue;
    }

//Return size of Stack
    public int getSize() {
        int stackSize = top + 1;
        return stackSize;
    }

//Return stack index like stack[index]
    public int getValue(int index) {

    if(index < 0 || index > top){
        System.out.println("ERROR: Stack is EMPTY or Invalid Input");
        return -1;
    }
    int currentValue = stack[index];
    return currentValue;
    }

//Returns true if stack is FULL, otherwise false
    public boolean isFull(){
    boolean stackFull;
    if(top == capacity - 1){
            stackFull = true;
        }
    else{stackFull = false;}
    return stackFull;
    }

//Returns true if stack is EMPTY, otherwise false
    public boolean isEmpty(){
    boolean stackEmpty;
    if(top == -1){
        stackEmpty = true;
        }
    else{stackEmpty = false;}
    return stackEmpty;
    }
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    
    public void undoOperation(Button button, StackApplication SA){
        switch(button.getText()){
            case "Push":
                int num = pop();
                SA.addData(button.getText(), num);
                break;

            case "Pop":
                push(previousPushValue);
                SA.addData(button.getText(), previousPushValue);
                break;

            /* Needs to be updated or discussed with the rest
            case "Peek":
                peek();
                SA.addData(button.getText());
                break;

            case "Clear":
                SA.isClearPressed();
                SA.addData(button.getText());
                break;

            case "Random":
                SA.setRandom(true);
                SA.isClearPressed();
                SA.setRandom(false);
                SA.addData(button.getText());
                break;

            case "Check":
                SA.checkStack();
                SA.addData(button.getText());
                break;*/

            case "Capacity":
                SA.capacitySelector();
                SA.addData(button.getText());
                break;
            default:
                SA.setStatusMessage("Status Message: Undo for " + button.getText() + " is not supported.");
                break;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i <= top; i++) {
            result.append(stack[i]);

            if (i < top) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }
}