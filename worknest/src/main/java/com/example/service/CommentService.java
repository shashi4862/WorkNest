package com.example.service;

import com.example.entity.Comment;
import com.example.entity.Task;

import java.util.List;

public interface CommentService {
    Comment saveComment(Comment comment);
    List<Comment> getCommentsByTask(Task task);
}
