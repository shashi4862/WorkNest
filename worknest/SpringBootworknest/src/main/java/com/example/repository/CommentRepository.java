package com.example.repository;

import com.example.entity.Comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	 List<Comment> findByAssignmentId(Long assignmentId);
}

