package com.worknest.dao;

import com.worknest.entity.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Repository
public class CommentDAOImpl implements CommentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Comment comment) {
        getSession().save(comment);
    }

    @Override
    public List<Comment> findByTaskId(Long taskId) {
        Query<Comment> q = getSession().createQuery("FROM Comment c WHERE c.task.id=:tid", Comment.class);
        q.setParameter("tid", taskId);
        return q.list();
    }
}
