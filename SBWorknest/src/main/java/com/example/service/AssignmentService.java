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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskAssignmentRepository taskAssignmentRepository;
    private final TaskService taskService;

    public void assignTaskManually(AssignTaskRequest request, User admin) {
        Task task = Task.builder()
                .title(request.getTaskTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .dueDate(request.getDueDate())
                .createdBy(admin)
                .status(TaskStatus.PENDING)
                .build();
        Task savedTask = taskRepository.save(task);

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

    public Optional<TaskAssignment> getAssignmentById(Long assignmentId) {
        return taskAssignmentRepository.findById(assignmentId);
    }

    public long countByStatus(TaskStatus status) {
        return taskAssignmentRepository.countByStatus(status);
    }

    public void updateStatus(UpdateStatusRequest request) {
        TaskAssignment assignment = taskAssignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Task assignment not found"));

        if (assignment.isLocked()) {
            throw new RuntimeException("Task is locked by admin and cannot be updated.");
        }
        if (assignment.isLeader() && assignment.getStatus() == TaskStatus.COMPLETED) {
            throw new RuntimeException("Leader cannot change the status of a completed task.");
        }

        // Update status of the individual assignment
        assignment.setStatus(request.getStatus());
        assignment.setUpdatedAt(LocalDateTime.now());
        taskAssignmentRepository.save(assignment);

        // If leader, update the task and all assignments for this task
        if (assignment.isLeader()) {
            Task task = assignment.getTask();
            task.setStatus(request.getStatus());
            taskRepository.save(task);

            List<TaskAssignment> allAssignments = taskAssignmentRepository.findByTaskId(task.getId());
            for (TaskAssignment a : allAssignments) {
                if (!a.isLeader()) { // Skip leader since already updated
                    a.setStatus(request.getStatus());
                    a.setUpdatedAt(LocalDateTime.now());
                    taskAssignmentRepository.save(a);
                }
            }
        }
    }

    public void lockTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.getStatus() != TaskStatus.COMPLETED) {
            throw new RuntimeException("Only completed tasks can be locked.");
        }

        task.setLocked(true);
        taskRepository.save(task);

        List<TaskAssignment> assignments = taskAssignmentRepository.findByTaskId(taskId);
        for (TaskAssignment assignment : assignments) {
            assignment.setLocked(true);
            taskAssignmentRepository.save(assignment);
        }
    }
}
