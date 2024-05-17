package com.ulanzhasssanov.CrudAppWithJUnit.controller;

import com.ulanzhasssanov.CrudAppWithJUnit.model.Post;
import com.ulanzhasssanov.CrudAppWithJUnit.repository.JdbcPostRepositoryImpl;

import java.util.List;

public class PostController {
    JdbcPostRepositoryImpl jdbcPostRepository = new JdbcPostRepositoryImpl();

    public Post savePost(Post post){
        Post savedPost = jdbcPostRepository.save(post);
        return savedPost;
    }


    public List<Post> getAllPosts(){
        List<Post> posts = jdbcPostRepository.getAll();
        return posts;
    }

    public Post getPostById(Integer id){
        Post post = jdbcPostRepository.getById(id);
        return post;
    }

    public Post updatePost(Post post){
        Post updatedPost = jdbcPostRepository.update(post);
        return updatedPost;
    }

    public void deletePost(Integer id){
        jdbcPostRepository.deleteById(id);
    }

}
