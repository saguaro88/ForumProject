package com.javaee.projectFroum.projectForum.services.interfaces;

import com.javaee.projectFroum.projectForum.exceptions.WrongUsernameException;
import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;

import java.util.List;

public interface PostService {
    Post getPostById(long id);
    List<Post> getAllPosts();
    Post addPost(Post post);
    Boolean deletePostById(long postId, long topicId) throws WrongUsernameException;
    Post editPost(Post post, long topicId, long postId) throws WrongUsernameException;

}
