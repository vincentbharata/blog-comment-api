package com.example.blog_comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog_comment.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
