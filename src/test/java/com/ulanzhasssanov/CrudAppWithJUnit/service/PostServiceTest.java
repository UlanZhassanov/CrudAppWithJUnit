package com.ulanzhasssanov.CrudAppWithJUnit.service;

import com.ulanzhasssanov.CrudAppWithJUnit.enums.PostStatus;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Label;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Post;
import com.ulanzhasssanov.CrudAppWithJUnit.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private List<Label> labels;
    private Timestamp currentTimestamp;
    private Post mockPost;

    @BeforeEach
    void setUp() {
        labels = new ArrayList<>();
        currentTimestamp = Timestamp.from(Instant.now());
        mockPost = new Post(1, "Test Content", currentTimestamp.toString(), currentTimestamp.toString(), labels, 1, PostStatus.ACTIVE);
    }

    @Test
    void testGetPostById() {
        when(postRepository.getById(anyInt())).thenReturn(mockPost);

        Post post = postService.getPostById(1);

        assertPostEquals(mockPost, post);
        verify(postRepository, times(1)).getById(1);
    }

    @Test
    void testGetAllPosts() {
        Post post1 = new Post(1, "Test Content", currentTimestamp.toString(), currentTimestamp.toString(), labels, 1, PostStatus.ACTIVE);
        Post post2 = new Post(1, "Test Content", currentTimestamp.toString(), currentTimestamp.toString(), labels, 1, PostStatus.ACTIVE);
        List<Post> mockPosts = Arrays.asList(post1, post2);

        when(postRepository.getAll()).thenReturn(mockPosts);

        List<Post> posts = postService.getAllPosts();

        assertEquals(2, posts.size());
        assertPostEquals(post1, mockPosts.get(0));
        assertPostEquals(post1, mockPosts.get(1));
        verify(postRepository, times(1)).getAll();
    }

    @Test
    void testSavePost() {
        when(postRepository.save(any(Post.class))).thenReturn(mockPost);

        Post result = postService.savePost(mockPost);

        assertPostEquals(mockPost, result);
        verify(postRepository, times(1)).save(mockPost);
    }

    @Test
    void testUpdatePost() {
        when(postRepository.update(any(Post.class))).thenReturn(mockPost);

        Post result = postService.updatePost(mockPost);

        assertPostEquals(mockPost, result);
        verify(postRepository, times(1)).update(mockPost);
    }

    @Test
    void testDeletePostById() {
        int postId = 1;

        postService.deletePostById(postId);

        verify(postRepository, times(1)).deleteById(postId);
    }

    private void assertPostEquals(Post expected, Post actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getContent(), actual.getContent());
        assertEquals(expected.getCreated(), actual.getCreated());
        assertEquals(expected.getUpdated(), actual.getUpdated());
        assertEquals(expected.getLabels(), actual.getLabels());
        assertEquals(expected.getWriterId(), actual.getWriterId());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

}
