package com.javaee.projectFroum.projectForum.controllers;

import com.javaee.projectFroum.projectForum.dto.PostDto;
import com.javaee.projectFroum.projectForum.dto.PostMapper;
import com.javaee.projectFroum.projectForum.dto.TopicDto;
import com.javaee.projectFroum.projectForum.dto.TopicMapper;
import com.javaee.projectFroum.projectForum.exceptions.WrongUsernameException;
import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.services.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostServiceImpl postService;

    @GetMapping(path = "add")
    public String addPost(Model model) {
        model.addAttribute("postForm", new PostDto());
        return "newpost";
    }
    @RequestMapping(path ="{id}", method = RequestMethod.GET)
    public String getPostById(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("post", postService.getPostById(id));
        return "post";
    }
    @PostMapping(path = "add")
    public String addPost(@ModelAttribute("postForm") PostDto postDto, BindingResult bindingResult) throws ParseException {
        postService.addPost(PostMapper.toPost(postDto));
        return "redirect:/topic";
    }
    @GetMapping(path = "update/{topicId}/{postId}")
    public String editPost(Model model, @PathVariable("topicId") long topicId, @PathVariable("postId") long postId) {
        model.addAttribute("postForm", postService.getPostById(postId));
        return "editpost";
    }

    @PostMapping(path = "update/{topicId}/{postId}")
    public String editPost(@ModelAttribute("topicForm") Post post,@PathVariable("topicId") long topicId, @PathVariable("postId") long postId) throws WrongUsernameException {
        postService.editPost(post, topicId, postId);
        return "redirect:/topic/{topicId}";
    }

    @GetMapping(path = "delete/{topicId}/{postId}")
    public String deletePost(@PathVariable("topicId") long topicId, @PathVariable("postId") long postId) throws WrongUsernameException {
        postService.deletePostById(postId, topicId);
        return "redirect:/topic/{topicId}";
    }
}
