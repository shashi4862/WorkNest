package com.example.service;

import com.example.dto.AssignTaskRequest;
import com.example.dto.UpdateStatusRequest;
import com.example.entity.Task;
import com.example.entity.TaskAssignment;
import com.example.entity.User;
import com.example.enums.TaskStatus;
import com.example.repository.TaskAssignmentRepository;
import com.example.repository.TaskRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskAssignmentRepository taskAssignmentRepository;

    // Assign a new task to a user
    public void assignTaskManually(AssignTaskRequest request, User admin) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create new Task
        Task task = Task.builder()
                .title(request.getTaskTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .dueDate(request.getDueDate())
                .createdBy(admin)
                .build();

        Task savedTask = taskRepository.save(task);

        // Create TaskAssignment
        TaskAssignment assignment = TaskAssignment.builder()
                .task(savedTask)
                .user(user)
                .status(TaskStatus.PENDING)
                .build();

        taskAssignmentRepository.save(assignment);
    }

    public List<TaskAssignment> getAssignmentsByUser(Long userId) {
        return taskAssignmentRepository.findByUserId(userId);
    }

    public List<TaskAssignment> getAllAssignments() {
        return taskAssignmentRepository.findAll();
    }

    public long countByStatus(TaskStatus status) {
        return taskAssignmentRepository.countByStatus(status);
    }
    
    public void updateStatus(UpdateStatusRequest request) {
        TaskAssignment assignment = taskAssignmentRepository.findById(request.getAssignmentId())
                .orElseThrow();
        assignment.setStatus(request.getStatus());
        taskAssignmentRepository.save(assignment);
    }
}
