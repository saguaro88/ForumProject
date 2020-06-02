package com.javaee.projectFroum.projectForum.dto;

import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.services.PostServiceImpl;
import com.javaee.projectFroum.projectForum.services.UserServiceImpl;
import com.javaee.projectFroum.projectForum.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PostMapper {
    private static PostServiceImpl postService;
    private static UserServiceImpl userService;

    @Autowired
    public PostMapper(PostServiceImpl postService, UserServiceImpl userService){
        PostMapper.postService = postService;
        PostMapper.userService = userService;
    }

    public static Post toPost(PostDto postDto){
        Post post = new Post();
        post.setUser(userService.findByUsername(postDto.getUsername()));
        post.setContent(postDto.getContent());
        return post;
    }

}
