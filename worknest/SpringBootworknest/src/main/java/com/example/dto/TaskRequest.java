package com.example.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
}

