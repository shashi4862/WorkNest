package com.example.controller;

import com.example.entity.Task;
import com.example.entity.User;
import com.example.service.TaskService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

    @GetMapping
    public String listTasks(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "user/mytasks";
    }

    @GetMapping("/{id}")
    public String viewTask(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        model.addAttribute("assignedUsers", task.getAssignedUsers());
        return "user/task-detail";
    }
}
