package com.worknest.service;

import java.util.List;

import com.worknest.entity.User;

public interface UserService {
	void register(User user);
    User login(String email, String password);
    List<User> getAllUsers();
    User getUserById(Long id);
    void updateUser(User user);
    void deleteUser(Long id);
}
