package com.worknest.service;

import com.worknest.dao.TaskDAO;
import com.worknest.entity.Task;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;

import java.util.List;


@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDAO taskDAO;

    @Override
    public void createTask(Task task) {
        taskDAO.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskDAO.findById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }

    @Override
    public List<Task> getTasksByUser(Long userId) {
        return taskDAO.findByUserId(userId);
    }

    @Override
    public void updateTask(Task task) {
        taskDAO.update(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskDAO.delete(id);
    }
}
