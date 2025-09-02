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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        // Assignments & comments
        model.addAttribute("allAssignments", assignmentService.getAllAssignments());
        model.addAttribute("comments", commentService.getAllComments());

        return "admin-dashboard";
    }

    @PostMapping("/assign-task")
    public String assignTask(@ModelAttribute AssignTaskRequest request, HttpSession session, RedirectAttributes redirectAttrs) {
        if (!Role.ADMIN.equals(session.getAttribute("role"))) return "redirect:/login";

        Long adminId = (Long) session.getAttribute("userId");
        User admin = userService.getUserById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        assignmentService.assignTaskManually(request, admin);
        
        redirectAttrs.addFlashAttribute("successMessage", "Task assigned successfully to selected users!");
        return "redirect:/admin/dashboard";
    }
    
    @PostMapping("/lock-task")
    public String lockTask(@RequestParam Long assignmentId, HttpSession session, RedirectAttributes redirectAttrs) {
        if (!Role.ADMIN.equals(session.getAttribute("role"))) {
            redirectAttrs.addFlashAttribute("errorMessage", "Only administrators can lock tasks.");
            return "redirect:/admin/dashboard";
        }
        
        try {
            assignmentService.lockAssignment(assignmentId);
            redirectAttrs.addFlashAttribute("successMessage", "Task locked successfully.");
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        return "redirect:/admin/dashboard";
    }
}
