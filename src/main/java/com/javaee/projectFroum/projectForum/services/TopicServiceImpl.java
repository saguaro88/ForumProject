package com.javaee.projectFroum.projectForum.services;

import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.repositories.TopicRepository;
import com.javaee.projectFroum.projectForum.services.interfaces.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicRepository topicRepository;
    @Override
    public Topic getTopicById(long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        return optionalTopic.orElse(null);
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Topic addTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Boolean deleteTopicById(long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
            if(optionalTopic.isPresent()){
                topicRepository.deleteById(id);
                return true;
            }
            return false;
    }
}
