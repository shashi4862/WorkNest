package com.example.controller;

import com.example.entity.Task;
import com.example.entity.User;
import com.example.service.TaskService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final TaskService taskService;

    public AdminController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        model.addAttribute("tasks", taskService.getAllTasks());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String usersPage(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/tasks")
    public String tasksPage(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        model.addAttribute("tasks", taskService.getAllTasks());
        return "admin/tasks";
    }

    @PostMapping("/addTask")
    public String addTask(@ModelAttribute Task task, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        taskService.saveTask(task);
        return "redirect:/admin/tasks";
    }
}
