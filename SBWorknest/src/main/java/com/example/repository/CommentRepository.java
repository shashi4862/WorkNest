package com.example.repository;

import com.example.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	// The previous method is no longer needed since the entity relationship changed
	// List<Comment> findByAssignmentId(Long assignmentId);
	
	// New method to find all comments for a given task ID
	List<Comment> findByTaskId(Long taskId);
}