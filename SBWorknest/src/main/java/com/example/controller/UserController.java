package com.example.controller;

import com.example.dto.UpdateStatusRequest;
import com.example.entity.TaskAssignment;
import com.example.entity.User;
import com.example.service.AssignmentService;
import com.example.service.CommentService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AssignmentService assignmentService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        model.addAttribute("assignments", assignmentService.getAssignmentsByUser(userId));
        return "user-dashboard";
    }

    @PostMapping("/update-status")
    public String updateStatus(@ModelAttribute UpdateStatusRequest request,
                               HttpSession session,
                               RedirectAttributes redirectAttrs) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        assignmentService.updateStatus(request);
        redirectAttrs.addFlashAttribute("successMessage", "Task status updated successfully!");
        return "redirect:/user/dashboard";
    }

    @PostMapping("/comment")
    public String addComment(@RequestParam Long assignmentId,
                             @RequestParam String content,
                             HttpSession session,
                             RedirectAttributes redirectAttrs) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.getUserById(userId).orElseThrow();
        TaskAssignment assignment = assignmentService.getAssignmentsByUser(userId).stream()
                .filter(a -> a.getId().equals(assignmentId))
                .findFirst().orElseThrow();

        commentService.addComment(assignment, user, content);
        redirectAttrs.addFlashAttribute("successMessage", "Comment added successfully!");
        return "redirect:/user/dashboard";
    }
}
