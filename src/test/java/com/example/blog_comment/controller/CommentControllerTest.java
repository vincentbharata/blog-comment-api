package com.example.blog_comment.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.blog_comment.dto.CommentRequest;
import com.example.blog_comment.dto.PostCommentResponse;
import com.example.blog_comment.model.Comment;
import com.example.blog_comment.model.Post;
import com.example.blog_comment.service.CommentService;
import com.example.blog_comment.service.PostService;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private PostService postService;

    @InjectMocks
    private CommentController commentController;

    @Test
    void testAddComment() {
        Long postId = 1L;
        CommentRequest request = new CommentRequest();
        request.setName("Alice");
        request.setMessage("Nice post");
        request.setPostId(postId);

        Post post = new Post();
        post.setId(postId);

        Comment comment = new Comment();
        comment.setId(10L);
        comment.setName("Alice");
        comment.setMessage("Nice post");
        comment.setPost(post);

        when(postService.getPostById(postId)).thenReturn(post);
        when(commentService.addComment(post, request)).thenReturn(comment);

        Comment result = commentController.addComment(postId, request);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals("Nice post", result.getMessage());
        verify(postService).getPostById(postId);
        verify(commentService).addComment(post, request);
    }

    @Test
    void testGetCommentById() {
        Long commentId = 2L;
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setName("Bob");
        comment.setMessage("Interesting");

        when(commentService.getCommentById(commentId)).thenReturn(comment);

        Comment result = commentController.getCommentById(commentId);

        assertNotNull(result);
        assertEquals("Bob", result.getName());
        assertEquals("Interesting", result.getMessage());
        verify(commentService).getCommentById(commentId);
    }

    @Test
    void testGetPostWithComments() {
        Long postId = 1L;

        Post post = new Post();
        post.setId(postId);
        post.setTitle("Judul");
        post.setContent("Isi konten");
        post.setAuthor("Vincent");

        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setName("User1");
        comment1.setMessage("Komentar 1");
        comment1.setPost(post);

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setName("User2");
        comment2.setMessage("Komentar 2");
        comment2.setPost(post);

        post.setComments(List.of(comment1, comment2));

        when(postService.getPostById(postId)).thenReturn(post);

        ResponseEntity<PostCommentResponse> response = commentController.getPostWithComments(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PostCommentResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Judul", responseBody.getTitle());
        assertEquals("Isi konten", responseBody.getContent());
        assertEquals("Vincent", responseBody.getAuthor());
        assertEquals(2, responseBody.getComments().size());

        verify(postService).getPostById(postId);
    }
}
