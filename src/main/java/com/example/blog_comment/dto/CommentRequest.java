package com.example.blog_comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank(message = "Cant be blank")
    private String name;

    @NotBlank(message = "Cant be blank")
    private String message;

    @JsonProperty("post_id")
    private Long postId;
}
