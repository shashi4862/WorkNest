package com.worknest.dao;

import java.util.List;

import com.worknest.entity.Task;

public interface TaskDAO {
	void save(Task task);
    Task findById(Long id);
    List<Task> findAll();
    void update(Task task);
    void delete(Long id);
    List<Task> findByUserId(Long userId);
}
