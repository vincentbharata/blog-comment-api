package com.example.blog_comment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.blog_comment.dto.PostRequest;
import com.example.blog_comment.model.Post;
import com.example.blog_comment.service.PostService;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    void testCreatePost() {
        // Arrange
        PostRequest request = new PostRequest();
        request.setTitle("Aku Nyerah");
        request.setContent("Helppppppppppp");
        request.setAuthor("Vincent");

        Post post = new Post();
        post.setId(1L);
        post.setTitle("Aku Nyerah");
        post.setContent("Helppppppppppp");
        post.setAuthor("Vincent");

        when(postService.createPost(request)).thenReturn(post);

        // Act
        Post result = postController.createPost(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Aku Nyerah", result.getTitle());
        assertEquals("Helppppppppppp", result.getContent());
        assertEquals("Vincent", result.getAuthor());
        verify(postService).createPost(request);
    }
}
