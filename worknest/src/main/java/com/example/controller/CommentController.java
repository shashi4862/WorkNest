package com.example.controller;

import com.example.entity.Comment;
import com.example.entity.Task;
import com.example.service.CommentService;
import com.example.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final TaskService taskService;

    public CommentController(CommentService commentService, TaskService taskService) {
        this.commentService = commentService;
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    public String viewComments(@PathVariable Long taskId, Model model) {
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("comments", commentService.getCommentsByTask(task));
        model.addAttribute("task", task);
        return "user/comments";
    }

    @PostMapping("/{taskId}/add")
    public String addComment(@PathVariable Long taskId, @ModelAttribute Comment comment) {
        Task task = taskService.getTaskById(taskId);
        comment.setTask(task);
        commentService.saveComment(comment);
        return "redirect:/comments/" + taskId;
    }
}
