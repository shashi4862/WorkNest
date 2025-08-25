package com.worknest.dao;

import com.worknest.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(User user) {
        getSession().save(user);
    }

    @Override
    public User findById(Long id) {
        return getSession().get(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        Query<User> q = getSession().createQuery("FROM User u WHERE u.email=:email", User.class);
        q.setParameter("email", email);
        return q.uniqueResult();
    }

    @Override
    public List<User> findAll() {
        return getSession().createQuery("FROM User", User.class).list();
    }

    @Override
    public void update(User user) {
        getSession().update(user);
    }

    @Override
    public void delete(Long id) {
        User u = findById(id);
        if(u != null) getSession().delete(u);
    }
}
