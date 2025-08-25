package com.worknest.service;

import com.worknest.entity.Comment;
import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> getCommentsByTask(Long taskId);
}
