package com.worknest.service;

import com.worknest.dao.UserDAO;
import com.worknest.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public void register(User user) {
        userDAO.save(user);
    }

    @Override
    public User login(String email, String password) {
        User u = userDAO.findByEmail(email);
        if(u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public void updateUser(User user) {
        userDAO.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.delete(id);
    }
}