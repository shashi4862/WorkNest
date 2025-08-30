package com.example.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AssignTaskRequest {
    private Long userId;         // Changed from String to Long
    private String taskTitle;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
}

