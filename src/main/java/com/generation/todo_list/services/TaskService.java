package com.generation.todo_list.services;

import com.generation.todo_list.models.Task;
import com.generation.todo_list.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService implements TaskServiceInterface {

    // Inject the repository
    @Autowired
    TaskRepository taskRepository;

    public void TaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Create a new task
    @Override
    public Optional<Task> createNewTask(Task task) {
        try {
            Task newTask = taskRepository.save(task);
            return Optional.of(newTask);
        }catch (Exception e) {
            // Otherwise return empty object
            return Optional.empty();
        }
    }

    // Get all tasks
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get all completed tasks
    @Override
    public List<Task> findAllCompletedTask() {
        return taskRepository.findAll().stream()
                .filter(task -> task.isCompleted())
                .collect(Collectors.toList());
    }

    // Get all incomplete tasks
    @Override
    public List<Task> findAllInCompleteTask() {
        return taskRepository.findAll().stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }

    // Get task by id
    @Override
    public Optional<Task> findTaskById(Long id) {
        return Optional.empty();
    }

    // Update task
    @Override
    public Optional<Task> updateTask(Long id, Task task) {
        try {
            // Find the task by id
            Optional<Task> toUpdate = taskRepository.findById(id);
            if(toUpdate.isEmpty()) {
                return Optional.empty();
            }
            Task updateTask = toUpdate.get();

            // Update the Task
            updateTask.setTask(task.getTask());
            updateTask.setCompleted(task.isCompleted());

            // Try to update the Task
            Task updatedTask = taskRepository.save(updateTask);
            return Optional.of(updatedTask);

        } catch (Exception e) {
            // Otherwise return empty object
            return Optional.empty();
        }
    }

    // Delete task
    @Override
    public boolean deleteTask(Long id) {
        try {
            // Find the Task to delete by id
            Optional<Task> task = taskRepository.findById(id);
            if (task.isEmpty()) {
                return false;
            }
            taskRepository.delete(task.get());
            return true;

        }catch (Exception e) {
            return false;
        }
    }

    // Get task by id
    @Override
    public Optional<Task> getTask(Long id) {
        return taskRepository.findById(id);
    }


}
