package com.generation.todo_list.exceptions;

public class ResourceNotFoundException extends Throwable {

    public ResourceNotFoundException(String id) {
        super("Could not find the Task: " +  id);
    }
}
