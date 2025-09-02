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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskAssignmentRepository taskAssignmentRepository;

    public void assignTaskManually(AssignTaskRequest request, User admin) {
        // Create new Task
        Task task = Task.builder()
                .title(request.getTaskTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .dueDate(request.getDueDate())
                .createdBy(admin)
                .status(TaskStatus.PENDING)
                .build();

        Task savedTask = taskRepository.save(task);

        // Create TaskAssignments for all selected users
        for (Long userId : request.getUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            TaskAssignment assignment = TaskAssignment.builder()
                    .task(savedTask)
                    .user(user)
                    .status(TaskStatus.PENDING)
                    .isLeader(userId.equals(request.getLeaderId()))
                    .assignedAt(LocalDateTime.now())
                    .build();

            taskAssignmentRepository.save(assignment);
        }
    }

    public List<TaskAssignment> getAssignmentsByUser(Long userId) {
        return taskAssignmentRepository.findByUserId(userId);
    }

    public List<TaskAssignment> getAllAssignments() {
        return taskAssignmentRepository.findAll();
    }
    
    public List<TaskAssignment> getAssignmentsByTaskId(Long taskId) {
        return taskAssignmentRepository.findByTaskId(taskId);
    }

    public long countByStatus(TaskStatus status) {
        return taskAssignmentRepository.countByStatus(status);
    }
    
    public void updateStatus(UpdateStatusRequest request) {
        TaskAssignment assignment = taskAssignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Task assignment not found"));
        
        // Prevent update if task is locked
        if (assignment.isLocked()) {
            throw new RuntimeException("Task is locked by admin and cannot be updated.");
        }
        
        // Prevent leader from updating status after it's completed
        if (assignment.isLeader() && assignment.getStatus() == TaskStatus.COMPLETED) {
            throw new RuntimeException("Leader cannot change the status of a completed task.");
        }
        
        assignment.setStatus(request.getStatus());
        assignment.setUpdatedAt(LocalDateTime.now());
        taskAssignmentRepository.save(assignment);
    }
    
    public void lockAssignment(Long assignmentId) {
        TaskAssignment assignment = taskAssignmentRepository.findById(assignmentId)
            .orElseThrow(() -> new RuntimeException("Task assignment not found"));
        
        if (assignment.getStatus() != TaskStatus.COMPLETED) {
            throw new RuntimeException("Only completed tasks can be locked.");
        }
        
        assignment.setLocked(true);
        assignment.setUpdatedAt(LocalDateTime.now());
        taskAssignmentRepository.save(assignment);
    }
    
}
