package com.example.service;

import com.example.entity.Task;
import com.example.entity.TaskStatus;

import java.util.List;

public interface TaskService {
    Task saveTask(Task task);
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    void deleteTask(Long id);
    List<Task> getTasksByStatus(TaskStatus status);
}
