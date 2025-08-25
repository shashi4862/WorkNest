package com.worknest.dao;

import com.worknest.entity.Comment;
import java.util.List;

public interface CommentDAO {
    void save(Comment comment);
    List<Comment> findByTaskId(Long taskId);
}