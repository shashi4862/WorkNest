package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private TaskAssignment assignment;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(length = 1000)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
}

