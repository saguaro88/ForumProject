package com.javaee.projectFroum.projectForum.dto;

import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.services.PostServiceImpl;
import com.javaee.projectFroum.projectForum.services.UserServiceImpl;
import com.javaee.projectFroum.projectForum.services.interfaces.PostService;
import com.javaee.projectFroum.projectForum.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class TopicMapper {

    private static PostServiceImpl postService;
    private static UserServiceImpl userService;

    @Autowired
    public TopicMapper(PostServiceImpl postService, UserServiceImpl userService){
        TopicMapper.postService = postService;
        TopicMapper.userService = userService;
    }
    public static Topic toTopic(TopicDto topicDto) throws ParseException {
        Topic topic = new Topic();
        topic.setTitle(topicDto.getTitle());
        topic.setUser(userService.findByUsername(topicDto.getUsername()));
        Post post = new Post();
        post.setContent(topicDto.getFirstPostContent());
        post.setUser(userService.findByUsername(topicDto.getUsername()));
        postService.addPost(post);
        Set<Post> posts = new HashSet<>();
        posts.add(post);
        topic.setPosts(posts);
        return topic;
    }
}

