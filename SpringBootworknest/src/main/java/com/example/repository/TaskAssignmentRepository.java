package com.example.repository;

import com.example.entity.TaskAssignment;
import com.example.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {
    List<TaskAssignment> findByUserId(Long userId);
    long countByStatus(TaskStatus status);
    List<TaskAssignment> findTop5ByOrderByIdDesc();
}
