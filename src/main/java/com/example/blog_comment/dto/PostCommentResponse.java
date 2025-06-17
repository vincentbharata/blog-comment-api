package com.example.blog_comment.dto;

import java.util.List;

import lombok.Data;

@Data
public class PostCommentResponse {

    private Long id;
    private String title;
    private String content;
    private String author;
    private List<CommentDTO> comments;

    @Data
    public static class CommentDTO {

        private Long id;
        private String name;
        private String message;
    }
}
