// CommentService.java
package com.example.service;

import com.example.entity.Comment;
import com.example.entity.Task;
import com.example.entity.User;
import com.example.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void addComment(Task task, User author, String content) {
        Comment comment = Comment.builder()
                .task(task)
                .author(author)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}