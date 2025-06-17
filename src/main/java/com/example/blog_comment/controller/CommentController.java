package com.example.blog_comment.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog_comment.dto.CommentRequest;
import com.example.blog_comment.dto.PostCommentResponse;
import com.example.blog_comment.dto.PostCommentResponse.CommentDTO;
import com.example.blog_comment.model.Comment;
import com.example.blog_comment.model.Post;
import com.example.blog_comment.service.CommentService;
import com.example.blog_comment.service.PostService;

import jakarta.validation.Valid;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @PostMapping("/posts/{id}/comments")
    public Comment addComment(@PathVariable Long id, @Valid @RequestBody CommentRequest request) {
        Post post = postService.getPostById(id);
        return commentService.addComment(post, request);
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<PostCommentResponse> getPostWithComments(@PathVariable Long id) {
        Post post = postService.getPostById(id);

        PostCommentResponse response = new PostCommentResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setAuthor(post.getAuthor());

        List<CommentDTO> commentDTOs = post.getComments().stream().map(comment -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(comment.getId());
            dto.setName(comment.getName());
            dto.setMessage(comment.getMessage());
            return dto;
        }).collect(Collectors.toList());

        response.setComments(commentDTOs);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/comments/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }
}
