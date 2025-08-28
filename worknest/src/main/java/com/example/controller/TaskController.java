package com.example.controller;

import com.example.entity.Task;
import com.example.entity.TaskStatus;
import com.example.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String viewTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "user/mytasks";
    }

    @PostMapping("/tasks/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        Task task = taskService.getTaskById(id);
        task.setStatus(status);
        taskService.saveTask(task);
        return "redirect:/user/tasks";
    }
}
