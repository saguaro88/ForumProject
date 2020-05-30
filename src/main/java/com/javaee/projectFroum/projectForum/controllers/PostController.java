package com.javaee.projectFroum.projectForum.controllers;

import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.services.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostServiceImpl postService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllItems() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") long id){
        Post searchedPost = postService.getPostById(id);
        if(searchedPost != null)    return new ResponseEntity<>(searchedPost, HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "add")
    public ResponseEntity<String> addPost(@RequestBody @Valid Post post, BindingResult result) {
        if (!result.hasErrors()) {
            postService.addPost(post);
            return new ResponseEntity<>("Post added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad input", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity deletePost(@PathVariable("id") long id) {
        if (postService.deletePostById(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
