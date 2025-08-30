package com.example.repository;

import com.example.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
	// The countByStatus method has been removed as it was invalid
}
