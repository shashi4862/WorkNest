package com.worknest.dao;

import java.util.List;

import com.worknest.entity.User;

public interface UserDAO {
	void save(User user);
    User findById(Long id);
    User findByEmail(String email);
    List<User> findAll();
    void update(User user);
    void delete(Long id);
}
