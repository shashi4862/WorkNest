package com.worknest.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="tasks")
public class Task {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;
  private String status; // Pending, In Progress, Completed, Delayed
  @Temporal(TemporalType.DATE)
  private Date startDate;
  @Temporal(TemporalType.DATE)
  private Date dueDate;

  @ManyToOne @JoinColumn(name="user_id")
  private User assignedUser;

  @OneToMany(mappedBy="task", cascade=CascadeType.ALL, orphanRemoval=true)
  private List<Comment> comments;

  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public String getTitle() {
	return title;
  }

  public void setTitle(String title) {
	this.title = title;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }

  public String getStatus() {
	return status;
  }

  public void setStatus(String status) {
	this.status = status;
  }

  public Date getStartDate() {
	return startDate;
  }

  public void setStartDate(Date startDate) {
	this.startDate = startDate;
  }

  public Date getDueDate() {
	return dueDate;
  }

  public void setDueDate(Date dueDate) {
	this.dueDate = dueDate;
  }

  public User getAssignedUser() {
	return assignedUser;
  }

  public void setAssignedUser(User assignedUser) {
	this.assignedUser = assignedUser;
  }

  public List<Comment> getComments() {
	return comments;
  }

  public void setComments(List<Comment> comments) {
	this.comments = comments;
  }

  
  
}