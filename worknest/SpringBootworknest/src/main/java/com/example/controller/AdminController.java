package com.example.controller;

import com.example.dto.AssignTaskRequest;
import com.example.dto.TaskRequest;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.enums.TaskStatus;
import com.example.service.AssignmentService;
import com.example.service.CommentService;
import com.example.service.TaskService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final TaskService taskService;
    private final AssignmentService assignmentService;
    private final CommentService commentService;

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (!Role.ADMIN.equals(session.getAttribute("role"))) return "redirect:/login";

        // Task stats
        model.addAttribute("pendingCount", assignmentService.countByStatus(TaskStatus.PENDING));
        model.addAttribute("inProgressCount", assignmentService.countByStatus(TaskStatus.IN_PROGRESS));
        model.addAttribute("completedCount", assignmentService.countByStatus(TaskStatus.COMPLETED));
        model.addAttribute("delayedCount", assignmentService.countByStatus(TaskStatus.DELAYED));

        // Users (exclude admin)
        List<User> nonAdminUsers = userService.getAllUsers().stream()
                .filter(user -> !user.getRole().equals(Role.ADMIN))
                .collect(Collectors.toList());
        model.addAttribute("users", nonAdminUsers);

        // Assignable tasks
        model.addAttribute("tasks", taskService.getAllTasks());

        // Assignments & comments
        model.addAttribute("allAssignments", assignmentService.getAllAssignments());
        model.addAttribute("comments", commentService.getAllComments());

        return "admin-dashboard";
    }

    @PostMapping("/assign-task")
    public String assignTask(@ModelAttribute AssignTaskRequest request, HttpSession session, Model model) {
        if (!Role.ADMIN.equals(session.getAttribute("role"))) return "redirect:/login";

        Long adminId = (Long) session.getAttribute("userId");
        User admin = userService.getUserById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        assignmentService.assignTaskManually(request, admin);

        model.addAttribute("successMessage", "Task assigned successfully!");
        return "redirect:/admin/dashboard";
    }
}
