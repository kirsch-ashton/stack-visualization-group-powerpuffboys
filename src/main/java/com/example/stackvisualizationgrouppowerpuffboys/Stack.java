package com.example.stackvisualizationgrouppowerpuffboys;

// Stack Data and Logic
public class Stack {
    private final int capacity = 10;
    private int[] stack = new int[capacity];
    private int top = -1; //Default value kay empty ang stack/array

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
}