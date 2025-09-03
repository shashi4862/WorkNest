package com.example.controller;

import com.example.dto.UpdateStatusRequest;
import com.example.entity.User;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final AssignmentService assignmentService;
    private final CommentService commentService;
    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session, Model model, RedirectAttributes redirectAttrs) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        try {
            User user = userService.getUserById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found in DB with ID: " + userId));
            model.addAttribute("assignments", assignmentService.getAssignmentsByUser(user.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("errorMessage", "Failed to load user dashboard: " + e.getMessage());
            return "redirect:/";
        }
        return "user-dashboard";
    }

    @PostMapping("/update-status")
    public String updateStatus(@ModelAttribute UpdateStatusRequest request,
                               HttpSession session,
                               RedirectAttributes redirectAttrs) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        try {
            assignmentService.updateStatus(request);
            redirectAttrs.addFlashAttribute("successMessage", "Task status updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("errorMessage", "Failed to update status: " + e.getMessage());
        }
        return "redirect:/user/dashboard";
    }

    @PostMapping("/comment")
    public String addComment(@RequestParam Long assignmentId,
                             @RequestParam String content,
                             HttpSession session,
                             RedirectAttributes redirectAttrs) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        try {
            User user = userService.getUserById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found in DB with ID: " + userId));
            var assignment = assignmentService.getAssignmentById(assignmentId)
                    .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));
            commentService.addComment(assignment.getTask(), user, content);
            redirectAttrs.addFlashAttribute("successMessage", "Comment added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("errorMessage", "Failed to add comment: " + e.getMessage());
        }
        return "redirect:/user/dashboard";
    }
}
