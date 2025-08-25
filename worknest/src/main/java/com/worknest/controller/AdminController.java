package com.worknest.controller;

import com.worknest.entity.Task;
import com.worknest.entity.User;
import com.worknest.service.TaskService;
import com.worknest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("users", userService.getAllUsers());
        return "admin-dashboard";
    }

    @GetMapping("/addUser")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        user.setRole("USER");
        userService.register(user);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/addTask")
    public String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("users", userService.getAllUsers());
        return "addTask";
    }

    @PostMapping("/addTask")
    public String addTask(@ModelAttribute Task task, @RequestParam Long userId) {
        User u = userService.getUserById(userId);
        task.setAssignedUser(u);
        task.setStatus("Pending");
        taskService.createTask(task);
        return "redirect:/admin/dashboard";
    }
}
