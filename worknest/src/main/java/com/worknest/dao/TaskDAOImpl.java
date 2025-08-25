package com.worknest.dao;

import com.worknest.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAOImpl implements TaskDAO {

	@Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Task task) {
        getSession().save(task);
    }

    @Override
    public Task findById(Long id) {
        return getSession().get(Task.class, id);
    }

    @Override
    public List<Task> findAll() {
        return getSession().createQuery("FROM Task", Task.class).list();
    }

    @Override
    public void update(Task task) {
        getSession().update(task);
    }

    @Override
    public void delete(Long id) {
        Task t = findById(id);
        if(t != null) getSession().delete(t);
    }

    @Override
    public List<Task> findByUserId(Long userId) {
        Query<Task> q = getSession().createQuery("FROM Task t WHERE t.assignedUser.id=:uid", Task.class);
        q.setParameter("uid", userId);
        return q.list();
    }
}