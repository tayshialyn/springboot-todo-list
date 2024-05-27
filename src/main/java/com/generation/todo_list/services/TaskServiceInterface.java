package com.generation.todo_list.services;

import com.generation.todo_list.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskServiceInterface {

    // Method signatures to work on Tasks Data

    // Create tasks
    public Optional<Task> createNewTask(Task task);

    // Get all tasks
    public List<Task> getAllTasks();

    // Get all completed tasks
    public List<Task> findAllCompletedTask();

    // Get all incomplete tasks
    public List<Task> findAllInCompleteTask();

    // Get task by id
    public Optional<Task> findTaskById(Long id);

    // Update task
    public Optional<Task> updateTask(Long id, Task task);

    // Delete task
    public boolean deleteTask(Long id);

    // Get Task by id
    public Optional<Task> getTask(Long id);

}
