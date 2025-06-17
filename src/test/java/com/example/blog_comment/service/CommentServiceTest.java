package com.example.blog_comment.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.blog_comment.dto.CommentRequest;
import com.example.blog_comment.model.Comment;
import com.example.blog_comment.model.Post;
import com.example.blog_comment.repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void testAddComment() {
        // Arrange
        Post post = new Post();
        post.setId(1L);

        CommentRequest request = new CommentRequest();
        request.setName("Cent");
        request.setMessage("Nice post!");

        Comment savedComment = new Comment();
        savedComment.setId(1L);
        savedComment.setPost(post);
        savedComment.setName("Cent");
        savedComment.setMessage("Nice post!");

        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        // Act
        Comment result = commentService.addComment(post, request);

        // Assert
        assertNotNull(result);
        assertEquals("Cent", result.getName());
        assertEquals("Nice post!", result.getMessage());
        assertEquals(1L, result.getPost().getId());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void testGetCommentsByPost() {
        // Arrange
        Post post = new Post();
        post.setId(1L);

        Comment c1 = new Comment(1L, "Cent", "Nice post!", post);
        Comment c2 = new Comment(2L, "Bob", "I agree", post);
        List<Comment> comments = Arrays.asList(c1, c2);

        when(commentRepository.findByPost(post)).thenReturn(comments);

        // Act
        List<Comment> result = commentService.getCommentsByPost(post);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Cent", result.get(0).getName());
        verify(commentRepository).findByPost(post);
    }

    @Test
    void testGetCommentById_Found() {
        // Arrange
        Post post = new Post();
        post.setId(1L);

        Comment comment = new Comment(1L, "Charlie", "Great read!", post);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        // Act
        Comment result = commentService.getCommentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Charlie", result.getName());
        verify(commentRepository).findById(1L);
    }

    @Test
    void testGetCommentById_NotFound() {
        // Arrange
        when(commentRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            commentService.getCommentById(99L);
        });

        assertEquals("Comment not found", exception.getMessage());
        verify(commentRepository).findById(99L);
    }
}
