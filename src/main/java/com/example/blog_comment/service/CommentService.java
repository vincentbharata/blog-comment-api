package com.example.blog_comment.service;

import com.example.blog_comment.dto.CommentRequest;
import com.example.blog_comment.model.Comment;
import com.example.blog_comment.model.Post;
import com.example.blog_comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment addComment(Post post, CommentRequest request) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setName(request.getName());
        comment.setMessage(request.getMessage());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
}
