package com.worknest.controller;

import com.worknest.entity.Task;
import com.worknest.entity.Comment;
import com.worknest.entity.User;
import com.worknest.service.TaskService;
import com.worknest.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User u = (User) session.getAttribute("user");
        model.addAttribute("tasks", taskService.getTasksByUser(u.getId()));
        return "user-dashboard";
    }

    @GetMapping("/taskDetails")
    public String taskDetails(@RequestParam Long id, Model model) {
        Task t = taskService.getTaskById(id);
        model.addAttribute("task", t);
        return "task-details";
    }

    @PostMapping("/updateTaskStatus")
    public String updateTaskStatus(@RequestParam Long id, @RequestParam String status) {
        Task t = taskService.getTaskById(id);
        t.setStatus(status);
        taskService.updateTask(t);
        return "redirect:/user/taskDetails?id=" + id;
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam Long taskId, @RequestParam String content, HttpSession session) {
        User u = (User) session.getAttribute("user");
        Task t = taskService.getTaskById(taskId);
        Comment c = new Comment();
        c.setContent(content);
        c.setTask(t);
        c.setUser(u);
        commentService.addComment(c);
        return "redirect:/user/taskDetails?id=" + taskId;
    }
}
