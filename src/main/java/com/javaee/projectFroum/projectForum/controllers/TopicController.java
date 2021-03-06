package com.javaee.projectFroum.projectForum.controllers;

import com.javaee.projectFroum.projectForum.dto.PostDto;
import com.javaee.projectFroum.projectForum.dto.PostMapper;
import com.javaee.projectFroum.projectForum.dto.TopicDto;
import com.javaee.projectFroum.projectForum.dto.TopicMapper;
import com.javaee.projectFroum.projectForum.exceptions.WrongUsernameException;
import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.models.User;
import com.javaee.projectFroum.projectForum.services.TopicServiceImpl;
import com.javaee.projectFroum.projectForum.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/topic")
@Slf4j
public class TopicController {
    @Autowired
    TopicServiceImpl topicService;
    @Autowired
    UserServiceImpl userService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllTopics(ModelMap model) {
        model.addAttribute("topicsList", topicService.getAllTopics());
        Set<Topic> followedTopics = userService.getCurrentLoggedUser().getFollowedTopics();
        model.addAttribute("topicfollowed", followedTopics);
        log.info("Getting all topics page.");
        return "topics";
    }
    @RequestMapping(path ="{id}", method = RequestMethod.GET)
    public String getAllPostsFromTopic(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("postsList", topicService.getAllPostsFromTopic(id));
        log.info("Getting all posts from topic page.");
        return "posts";
    }

    @GetMapping(path = "add")
    public String addTopic(Model model) {
        model.addAttribute("topicForm", new TopicDto());
        log.info("Adding new topic page returned.");
        return "newtopic";
    }
    @GetMapping(path = "update/{id}")
    public String editTopic(Model model, @PathVariable("id") long id) {
        model.addAttribute("topicForm", topicService.getTopicById(id));
        log.info("Edit topic page returned.");
        return "edittopic";
    }
    @PostMapping(path = "update/{id}")
    public String editTopic(@ModelAttribute("topicForm") Topic topic, @PathVariable("id") long id) throws ParseException, WrongUsernameException {
        topicService.editTopic(topic, id);
        log.info("Topic edited.");
        return "redirect:/topic";
    }
    @PostMapping(path = "add")
    public String addTopic(@ModelAttribute("topicForm") TopicDto topicDto) throws ParseException {
        topicService.addTopic(TopicMapper.toTopic(topicDto));
        log.info("Topic added.");
            return "redirect:/topic";
    }
    @GetMapping(path = "{id}/add")
    public String addPostToTopic(Model model) {
        model.addAttribute("postForm", new PostDto());
        log.info("New post page returned.");
        return "newpost";
    }
    @PostMapping(path = "{id}/add")
    public String addPostToTopic(@ModelAttribute("postForm") PostDto postDto, @PathVariable("id") long id){
            topicService.addPostToTopic(id, PostMapper.toPost(postDto));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        log.info("Post added to topic.");
        return "redirect:/topic/{id}";
    }
    @GetMapping(path = "delete/{id}")
    public String deleteTopic(@PathVariable("id") long id) throws WrongUsernameException {
            topicService.deleteTopicById(id);
            log.info("Topic deleted.");
        return "redirect:/topic";
    }
}
