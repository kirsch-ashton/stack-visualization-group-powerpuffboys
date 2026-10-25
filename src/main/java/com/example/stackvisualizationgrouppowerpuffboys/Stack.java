package com.example.stackvisualizationgrouppowerpuffboys;

import java.util.Arrays;

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
    private int top = -1; //Default value kay empty ang stack/array

    // For Undo
    private int lastPoppedValue;   // for undoing Pop
    private int[] savedStack;      // snapshot, for undoing Clear / Random / Capacity
    private int savedTop;
    private int savedCapacity;

    /* Not Needed Fields
    private int[] backupStack = stack;
    private int previousPushValue;
    */

    //Add a top value in Stack

    public void push(int value){
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
        lastPoppedValue = stack[top];
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

    public void setCapacity(int newCapacity){
        stack = Arrays.copyOf(stack, newCapacity);
        if(top >= newCapacity){
            top = newCapacity - 1;
        }
        capacity = newCapacity;
    }
    // Methods For Undo

    public void saveSnapshot(){
        savedStack = Arrays.copyOf(stack, stack.length);
        savedTop = top;
        savedCapacity = capacity;
    }

    public void restoreSnapshot(){
        stack = Arrays.copyOf(savedStack, savedStack.length);
        top = savedTop;
        capacity = savedCapacity;
    }

    public void clear(){
    top = -1;
    }

    public int getCapacity(){
    return capacity;
}
   
    
    public void undoOperation(Button button, StackApplication SA){
        switch(button.getText()){

            case "Push":
                if(isEmpty()){
                    SA.setStatusMessage("Status Message: Nothing to undo. Stack is Empty.");
                    break;
                }
                int removedValue = stack[top];
                top--;
                SA.setStatusMessage("Status Message: Undid Push (removed " + removedValue + ")");
                SA.addData("Undo Push", removedValue, this);
                break;

            case "Pop": 
                if(isFull()){
                SA.setStatusMessage("Status Message: Cannot undo Pop, stack is full.");
                break;
            }
            top++;
            stack[top] = lastPoppedValue;
            SA.setStatusMessage("Status Message: Undid Pop (restored " + lastPoppedValue + ")");
            SA.addData("Undo Pop", lastPoppedValue, this);
            break;

            // These three cases have a similar result/purpose, so im making them run the same thing
            case "Clear":
            case "Random":
            case "Capacity":
            if(savedStack == null){
                SA.setStatusMessage("Status Message: Nothing to undo.");
                break;
            }
            restoreSnapshot();
            SA.setStatusMessage("Status Message: Undid " + button.getText());
            SA.addData("Undo " + button.getText(), this);
            break;

            case "Peek":
            SA.setStatusMessage("Status Message: Undid Peek");
            SA.addData("Undo Peek");
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