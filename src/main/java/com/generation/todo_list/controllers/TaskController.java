package com.generation.todo_list.controllers;

import com.generation.todo_list.exceptions.ResourceNotFoundException;
import com.generation.todo_list.models.Task;
import com.generation.todo_list.services.TaskServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    TaskServiceInterface taskServiceInterface;

    // Constructor
    public TaskController(TaskServiceInterface taskServiceInterface) {
        this.taskServiceInterface = taskServiceInterface;
    }

    // TODO : Implemented - Custom Exception Handling
    //  Create a new task
    @PostMapping
    public ResponseEntity<Object> createTask(@Valid @RequestBody Task task) throws Exception {
        try {
            Optional<Task> createdTask = taskServiceInterface.createNewTask(task);
            if (createdTask.isEmpty()) {
                throw new Exception("Unable to add Task.");
            }
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // TODO : Implemented - Custom Exception Handling
    //  Get all tasks
    @GetMapping
    public ResponseEntity<Object> getAllTasks() throws Exception {
        try {
            List<Task> tasks = taskServiceInterface.getAllTasks();
            if (tasks.isEmpty()) {
                throw new ResourceNotFoundException("No task(s) found.");
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // TODO : Implemented - Exception Handling
    //  Update Task by Id
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable("id") Long id, @Valid @RequestBody Task task) {
        try {
            Optional<Task> result = taskServiceInterface.updateTask(id, task);
            if (result.isEmpty()) {
                throw new ResourceNotFoundException("No task found.");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    // TODO : Implemented - Custom Exception Handling
    //  Delete a task by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable("id") Long id) throws Exception {
        try {
            boolean removed = taskServiceInterface.deleteTask(id);
            if (!removed) {
                throw new Exception("Unable to find Task.");
            }
            return new ResponseEntity<>("Task deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    // TODO : Implemented - Custom Exception Handling
    //  Get all completed tasks
    @GetMapping("/completed")
    public ResponseEntity<Object> getAllCompletedTasks() throws Exception {
        try {
            List<Task> tasks = taskServiceInterface.findAllCompletedTask();
            if (tasks.isEmpty()) {
                throw new ResourceNotFoundException("No completed task(s) found.");
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO : Implemented - Custom Exception Handling
    //  Get all incomplete tasks
    @GetMapping("/incomplete")
    public ResponseEntity<Object> getAllInCompleteTasks() throws Exception {
        try {
            List<Task> tasks = taskServiceInterface.findAllInCompleteTask();
            if (tasks.isEmpty()) {
                throw new ResourceNotFoundException("No incomplete task(s) found.");
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO : Implemented - Custom Exception Handling
    //  Get task by id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getTaskById(@PathVariable("id") Long id) throws Exception {
        try {
            Optional<Task> task = taskServiceInterface.getTask(id);
            if (task.isEmpty()) {
                throw new ResourceNotFoundException(id.toString() + " not found.");
            }
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
