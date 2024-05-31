package com.ulanzhasssanov.CrudAppWithJUnit.controller;

import com.ulanzhasssanov.CrudAppWithJUnit.model.Post;
import com.ulanzhasssanov.CrudAppWithJUnit.repository.JdbcPostRepositoryImpl;
import com.ulanzhasssanov.CrudAppWithJUnit.service.PostService;

import java.util.List;

public class PostController {
    private PostService postService = new PostService(new JdbcPostRepositoryImpl());

    public Post savePost(Post post){
        return postService.savePost(post);
    }


    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    public Post getPostById(Integer id){
        return postService.getPostById(id);
    }

    public Post updatePost(Post post){
        return postService.updatePost(post);
    }

    public void deletePost(Integer id){
        postService.deletePostById(id);
    }

}
