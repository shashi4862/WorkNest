package com.worknest.service;

import java.util.List;

import com.worknest.entity.Task;

public interface TaskService {
	 void createTask(Task task);
	    Task getTaskById(Long id);
	    List<Task> getAllTasks();
	    List<Task> getTasksByUser(Long userId);
	    void updateTask(Task task);
	    void deleteTask(Long id);
}
