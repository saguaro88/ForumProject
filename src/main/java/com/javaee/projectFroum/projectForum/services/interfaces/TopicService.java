package com.javaee.projectFroum.projectForum.services.interfaces;

import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;

import java.util.List;

public interface TopicService {
    Topic getTopicById(long id);
    List<Topic> getAllTopics();
    Topic addTopic(Topic topic);
    Boolean deleteTopicById(long id);
}
