package com.example.service;

import com.example.entity.Comment;
import com.example.entity.TaskAssignment;
import com.example.entity.User;
import com.example.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment addComment(TaskAssignment assignment, User author, String content) {
        Comment comment = Comment.builder()
                .assignment(assignment)
                .author(author)
                .content(content)
                .build();
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByAssignment(TaskAssignment assignment) {
        return assignment.getComments();
    }
    
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
    
    public List<Comment> getCommentsByAssignmentId(Long assignmentId) {
        return commentRepository.findByAssignmentId(assignmentId);
    }
}
