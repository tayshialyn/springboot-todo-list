package com.generation.todo_list.exceptions;

public class MessageNotReadableException extends RuntimeException {

    public MessageNotReadableException() {
        super("Data sent is not valid. Please Check again.");
    }
}
