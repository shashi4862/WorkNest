package com.example.controller;

import com.example.dto.AssignTaskRequest;
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
    public String adminDashboard(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Object role = session.getAttribute("role");
        if (userId == null) return "redirect:/login";
        if (role == null || role != Role.ADMIN) return "redirect:/user/dashboard";

        User currentUser = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        long pendingCount = assignmentService.countByStatus(TaskStatus.PENDING);
        long inProgressCount = assignmentService.countByStatus(TaskStatus.IN_PROGRESS);
        long completedCount = assignmentService.countByStatus(TaskStatus.COMPLETED);
        long delayedCount = assignmentService.countByStatus(TaskStatus.DELAYED);

        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("inProgressCount", inProgressCount);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("delayedCount", delayedCount);

        List<User> nonAdminUsers = userService.getAllUsers().stream()
                .filter(user -> user.getRole() != Role.ADMIN)
                .collect(Collectors.toList());
        model.addAttribute("users", nonAdminUsers);
        model.addAttribute("allTasks", taskService.getAllTasks());
        model.addAttribute("comments", commentService.getAllComments());

        return "admin-dashboard";
    }

    @PostMapping("/assign-task")
    public String assignTask(@ModelAttribute AssignTaskRequest request,
                             HttpSession session,
                             RedirectAttributes redirectAttrs) {
        Long userId = (Long) session.getAttribute("userId");
        Object role = session.getAttribute("role");
        if (userId == null) return "redirect:/login";
        if (role == null || role != Role.ADMIN) return "redirect:/user/dashboard";

        try {
            if (request.getUserIds() == null || request.getUserIds().isEmpty()) {
                redirectAttrs.addFlashAttribute("errorMessage", "Please select at least one user.");
                return "redirect:/admin/dashboard";
            }
            if (request.getLeaderId() == null) {
                redirectAttrs.addFlashAttribute("errorMessage", "Please select a leader.");
                return "redirect:/admin/dashboard";
            }

            User admin = userService.getUserById(userId)
                    .orElseThrow(() -> new RuntimeException("Admin not found"));

            assignmentService.assignTaskManually(request, admin);
            redirectAttrs.addFlashAttribute("successMessage", "Task assigned successfully to selected users!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("errorMessage", "Failed to assign task: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/lock-task")
    public String lockTask(@RequestParam Long taskId,
                           HttpSession session,
                           RedirectAttributes redirectAttrs) {
        Long userId = (Long) session.getAttribute("userId");
        Object role = session.getAttribute("role");
        if (userId == null) return "redirect:/login";
        if (role == null || role != Role.ADMIN) {
            redirectAttrs.addFlashAttribute("errorMessage", "Only administrators can lock tasks.");
            return "redirect:/user/dashboard";
        }

        try {
            assignmentService.lockTask(taskId);
            redirectAttrs.addFlashAttribute("successMessage", "Task locked successfully.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}
