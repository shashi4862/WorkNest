package com.worknest.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String password;
  private String role; // "ADMIN" or "USER"

  @OneToMany(mappedBy="assignedUser", cascade=CascadeType.ALL)
  private List<Task> tasks;

  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public String getEmail() {
	return email;
  }

  public void setEmail(String email) {
	this.email = email;
  }

  public String getPassword() {
	return password;
  }

  public void setPassword(String password) {
	this.password = password;
  }

  public String getRole() {
	return role;
  }

  public void setRole(String role) {
	this.role = role;
  }

  public List<Task> getTasks() {
	return tasks;
  }

  public void setTasks(List<Task> tasks) {
	this.tasks = tasks;
  }
  
}