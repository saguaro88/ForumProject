package com.javaee.projectFroum.projectForum.controllers;

import com.javaee.projectFroum.projectForum.dto.PostDto;
import com.javaee.projectFroum.projectForum.dto.PostMapper;
import com.javaee.projectFroum.projectForum.dto.TopicDto;
import com.javaee.projectFroum.projectForum.dto.TopicMapper;
import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.models.User;
import com.javaee.projectFroum.projectForum.services.TopicServiceImpl;
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

@Controller
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    TopicServiceImpl topicService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllTopics(ModelMap model) {
        model.addAttribute("topicsList", topicService.getAllTopics());
        return "topics";
    }
    @RequestMapping(path ="{id}", method = RequestMethod.GET)
    public String getAllPostsFromTopic(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("postsList", topicService.getAllPostsFromTopic(id));
        return "posts";
    }

    @GetMapping(path = "add")
    public String addTopic(Model model) {
        model.addAttribute("topicForm", new TopicDto());
        return "newtopic";
    }
    @GetMapping(path = "update/{id}")
    public String editTopic(Model model, @PathVariable("id") long id) {
        model.addAttribute("topicForm", topicService.getTopicById(id));
        return "edittopic";
    }
    @PostMapping(path = "update/{id}")
    public String editTopic(@ModelAttribute("topicForm") Topic topic, @PathVariable("id") long id) throws ParseException {
        topicService.editTopic(topic, id);
        return "redirect:/topic";
    }
    @PostMapping(path = "add")
    public String addTopic(@ModelAttribute("topicForm") TopicDto topicDto) throws ParseException {
        topicService.addTopic(TopicMapper.toTopic(topicDto));
            return "redirect:/topic";
    }
    @GetMapping(path = "{id}/add")
    public String addPostToTopic(Model model) {
        model.addAttribute("postForm", new PostDto());
        return "newpost";
    }
    @PostMapping(path = "{id}/add")
    public String addPostToTopic(@ModelAttribute("postForm") PostDto postDto, @PathVariable("id") long id){
            topicService.addPostToTopic(id, PostMapper.toPost(postDto));
        return "redirect:/topic/{id}";
    }
    @GetMapping(path = "delete/{id}")
    public String deleteTopic(@PathVariable("id") long id) {
            topicService.deleteTopicById(id);
        return "redirect:/topic";
    }
}
