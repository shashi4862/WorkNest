package com.worknest.service;

import com.worknest.dao.CommentDAO;
import com.worknest.entity.Comment;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;

import java.util.List;


@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public void addComment(Comment comment) {
        commentDAO.save(comment);
    }

    @Override
    public List<Comment> getCommentsByTask(Long taskId) {
        return commentDAO.findByTaskId(taskId);
    }
}
