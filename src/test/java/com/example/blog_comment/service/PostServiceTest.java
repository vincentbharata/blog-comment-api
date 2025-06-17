package com.example.blog_comment.service;

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

import com.example.blog_comment.dto.PostRequest;
import com.example.blog_comment.model.Post;
import com.example.blog_comment.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void testCreatePost() {
        
        PostRequest request = new PostRequest();
        request.setTitle("Spring Boot Test");
        request.setContent("Learn how to test with Mockito");
        request.setAuthor("Vincent");

        Post savedPost = new Post();
        savedPost.setId(1L);
        savedPost.setTitle("Spring Boot Test");
        savedPost.setContent("Learn how to test with Mockito");
        savedPost.setAuthor("Vincent");

        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

       
        Post result = postService.createPost(request);

       
        assertNotNull(result);
        assertEquals("Spring Boot Test", result.getTitle());
        assertEquals("Vincent", result.getAuthor());
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void testGetPostById_Found() {
        
        Post post = new Post();
        post.setId(1L);
        post.setTitle("JUnit");
        post.setAuthor("Tester");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

       
        Post result = postService.getPostById(1L);

        
        assertNotNull(result);
        assertEquals("JUnit", result.getTitle());
        verify(postRepository).findById(1L);
    }

    @Test
    void testGetPostById_NotFound() {
        
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

       
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            postService.getPostById(99L);
        });

        assertEquals("Post not found", exception.getMessage());
        verify(postRepository).findById(99L);
    }
}
