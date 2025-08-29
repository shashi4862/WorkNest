package com.example.controller;

import com.example.entity.Comment;
import com.example.entity.Task;
import com.example.entity.User;
import com.example.service.CommentService;
import com.example.service.TaskService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final TaskService taskService;
    private final UserService userService;

    public CommentController(CommentService commentService, TaskService taskService, UserService userService) {
        this.commentService = commentService;
        this.taskService = taskService;
        this.userService = userService;
    }

    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

    @PostMapping("/tasks/{taskId}/comments")
    public String addComment(@PathVariable Long taskId, @RequestParam String content, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);
        Task task = taskService.getTaskById(taskId);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setTask(task);
        comment.setUser(user);
        commentService.saveComment(comment);
        return "redirect:/tasks/" + taskId;
    }
}
