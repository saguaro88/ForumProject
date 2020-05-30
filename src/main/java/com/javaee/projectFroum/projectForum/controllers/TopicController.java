package com.javaee.projectFroum.projectForum.controllers;

import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.services.TopicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
public class TopicController {
    @Autowired
    TopicServiceImpl topicService;

    @GetMapping
    public ResponseEntity<List<Topic>> getAllItems() {
        return new ResponseEntity<>(topicService.getAllTopics(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable("id") long id){
        Topic searchedTopic = topicService.getTopicById(id);
        if(searchedTopic != null)    return new ResponseEntity<>(searchedTopic, HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "add")
    public ResponseEntity<String> addTopic(@RequestBody @Valid Topic topic, BindingResult result) {
        if (!result.hasErrors()) {
            topicService.addTopic(topic);
            return new ResponseEntity<>("Topic added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad input", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity deleteTopic(@PathVariable("id") long id) {
        if (topicService.deleteTopicById(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
